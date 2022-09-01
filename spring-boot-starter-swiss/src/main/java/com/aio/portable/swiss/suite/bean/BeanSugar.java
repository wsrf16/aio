package com.aio.portable.swiss.suite.bean;

import com.aio.portable.swiss.sugar.resource.function.LambdaBiConsumer;
import com.aio.portable.swiss.sugar.resource.function.LambdaConsumer;
import com.aio.portable.swiss.sugar.resource.function.LambdaFunction;
import com.aio.portable.swiss.sugar.resource.function.LambdaSupplier;
import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.DynamicProxySugar;
import com.aio.portable.swiss.sugar.resource.ClassSugar;
import org.apache.ibatis.reflection.ReflectionException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.cglib.core.Converter;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public abstract class BeanSugar {

    public static final <T> Boolean match(T match, T bean) {
        if (match == null)
            return true;
        else {
            if (bean == null)
                return false;
            else {
                Map<String, Object> nameValueMatch = BeanSugar.PropertyDescriptors.toNameValueMap(match);
                Map<String, Object> nameValueBean = BeanSugar.PropertyDescriptors.toNameValueMap(bean);

                boolean equal = matchNameValueMap(nameValueMatch, nameValueBean);
                return equal;
            }
        }
    }

    /**
     * matchCollection : like Objects.deepEquals()
     *
     * @param matchList
     * @param beanList
     * @param <T>
     * @return
     */
    public static final <T> Boolean matchList(List<T> matchList, List<T> beanList) {
        Map<String, Object> nameValueMatch = BeanSugar.PropertyDescriptors.toNameValueMap(matchList.get(0));
        boolean equal = beanList.stream().anyMatch(bean -> {
            Map<String, Object> nameValueBean = BeanSugar.PropertyDescriptors.toNameValueMap(bean);

            boolean itemEqual = matchNameValueMap(nameValueMatch, nameValueBean);
            return itemEqual;
        });
        return equal;
    }

    private static final <SUB> boolean matchNameValueMap(Map<String, Object> nameValueMatch, Map<String, Object> nameValueBean) {
        Set<String> nameList = nameValueMatch.keySet();
        return nameList.stream().allMatch(key -> {
            Boolean b;
            SUB subMatch = (SUB) nameValueMatch.get(key);
            SUB subBean = (SUB) nameValueBean.get(key);
            if (subMatch == null)
                b = true;
            else if (subBean == null)
                b = false;
            else {
                Class<SUB> subClazz = (Class<SUB>) subMatch.getClass();
                if (ClassSugar.isSimpleValueType(subClazz))
                    b = Objects.equals(subMatch, subBean);
                else if ((List.class).isAssignableFrom(subClazz))
                    b = matchList((List) subMatch, (List) subBean);
                else
                    b = match(subMatch, subBean);
            }
            return b;
        });
    }



    public abstract static class Cloneable {
        private static final ConcurrentMap<Class<?>, BeanCopier> beanCopierCache = new ConcurrentHashMap<Class<?>, BeanCopier>();
        public static final <S> S deepCloneByProperties(S source) {
            try {
                S target = (S)source.getClass().getConstructor().newInstance();
                deepCloneByProperties(source, target);
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final <S, T> void deepCloneByProperties(S source, T target) {
            BeanUtils.copyProperties(source, target);
        }

        public static final <S, T> T deepCloneByProperties(S source, Class<T> targetClazz) {
            try {
                T target = targetClazz.getConstructor().newInstance();
                deepCloneByProperties(source, target);
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final <S> S deepCloneByCglib(S source) {
            try {
                S target = (S)source.getClass().getConstructor().newInstance();
                deepCloneByCglib(source, target);
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public static final <S, T> void deepCloneByCglib(S source, T target) {
            BeanCopier copier = createCopier(source.getClass(), target.getClass());
            copier.copy(source, target, new BeanConverter());
        }

        public static final <S, T> T deepCloneByCglib(S source, Class<T> targetClazz) {
            try {
                T target = targetClazz.getConstructor().newInstance();
                BeanCopier copier = createCopier(source.getClass(), targetClazz);
                copier.copy(source, target, new BeanConverter());
                return target;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private static BeanCopier createCopier(Class<?> clazz) {
            if (beanCopierCache.containsKey(clazz))
                return beanCopierCache.get(clazz);
            beanCopierCache.putIfAbsent(clazz, BeanCopier.create(clazz, clazz, true));
            return beanCopierCache.get(clazz);
        }

        private static BeanCopier createCopier(Class<?> source, Class<?> target) {
            return BeanCopier.create(source, target, true);
        }

        private static class BeanConverter implements Converter {
            @Override
            public Object convert(Object bean, Class fieldType, Object fieldName) {
                return _clone(bean);
            }

            private static Object _clone(Object bean) {
                if (bean == null) {
                    return null;
                } else {
                    if (bean.getClass().isArray() && !bean.getClass().getComponentType().equals(byte.class)) {
                        int length = Array.getLength(bean);
                        Object clone = Array.newInstance(bean.getClass().getComponentType(), length);
                        for (int i = 0; i < length; i++) {
                            Array.set(clone, i, _clone(Array.get(bean, i)));
                        }
                        return clone;
                    } else {
                        return bean;
                    }
                }
            }
        }
    }

    public abstract static class PropertyDescriptors {
        public static final String[] getNullPropertyNames(Object bean) {
            //1.获取Bean
            BeanWrapper srcBean = new BeanWrapperImpl(bean);
            //2.获取Bean的属性描述
            PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
            //3.获取Bean的空属性
            Set<String> properties = new HashSet<>();
            for (PropertyDescriptor propertyDescriptor : pds) {
                String propertyName = propertyDescriptor.getName();
                Object propertyValue = srcBean.getPropertyValue(propertyName);
                if (propertyValue == null) {
                    srcBean.setPropertyValue(propertyName, null);
                    properties.add(propertyName);
                }
            }
            return properties.toArray(new String[0]);
        }

        public static final String[] getAllPropertyNames(Object bean) {
            Map<String, Class<?>> stringClassMap = toNameClassMap(bean.getClass());
            return stringClassMap.keySet().toArray(new String[0]);
        }

        public static final Object getPropertyValue(Object bean, String propertyName) {
            BeanWrapper srcBean = new BeanWrapperImpl(bean);
            return srcBean.getPropertyValue(propertyName);
        }

        public static final void copyNotNullProperties(Object source, Object target) {
            BeanUtils.copyProperties(source, target, PropertyDescriptors.getNullPropertyNames(source));
        }

        public static final void copyAllProperties(Object source, Object target) {
            BeanUtils.copyProperties(source, target);
        }

        public static final void copyPropertiesOnly(Object source, Object target, List<String> onlyPropertyList) {
            List<String> allPropertyNameList = Arrays.asList(PropertyDescriptors.getAllPropertyNames(source));
            List<String> excludeList = CollectionSugar.except(allPropertyNameList, onlyPropertyList);
            String[] excludeProperties = CollectionSugar.toArrayNullable(excludeList);
            BeanUtils.copyProperties(source, target, excludeProperties);
        }

        public static final void copyPropertiesOnly(Object source, Object target, String... onlyProperties) {
            List<String> allPropertyList = Arrays.asList(PropertyDescriptors.getAllPropertyNames(source));
            List<String> onlyPropertyList = Arrays.asList(onlyProperties);
            List<String> excludeList = CollectionSugar.except(allPropertyList, onlyPropertyList);
            String[] excludeProperties = CollectionSugar.toArrayNullable(excludeList);
            BeanUtils.copyProperties(source, target, excludeProperties);
        }

        public static final <S, T> void copyPropertiesOnly(S s, T t, LambdaFunction<S, ?>... properties) {
            List<String> propertyList = Arrays.stream(properties).map(c -> BeanSugar.PropertyDescriptors.getPropertyName(c)).collect(Collectors.toList());
            copyPropertiesOnly(s, t, propertyList);
        }

        public static final void copyAllPropertiesExclude(Object source, Object target, List<String> excludePropertyList) {
            String[] excludeProperties = CollectionSugar.toArrayNullable(excludePropertyList);
            BeanUtils.copyProperties(source, target, excludeProperties);
        }

        public static final void copyAllPropertiesExclude(Object source, Object target, String... excludeProperties) {
            BeanUtils.copyProperties(source, target, excludeProperties);
        }

        public static final <S, T> void copyAllPropertiesExclude(S s, T t, LambdaFunction<S, ?>... properties) {
            List<String> propertyList = Arrays.stream(properties).map(c -> BeanSugar.PropertyDescriptors.getPropertyName(c)).collect(Collectors.toList());
            copyAllPropertiesExclude(s, t, propertyList);
        }




        public static final PropertyDescriptor getDeclaredPropertyDescriptorIncludeParents(Class<?> clazz, String name) throws NoSuchFieldException {
            List<String> propertyDescriptorNameList = new ArrayList<>(Arrays.asList(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))).stream().map(PropertyDescriptor::getName).collect(Collectors.toList());
            PropertyDescriptor propertyDescriptor;
            if (propertyDescriptorNameList.contains(name)) {
                propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(clazz, name);
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                if (parentClazz == null) {
                    throw new NoSuchFieldException(name);
                } else {
                    propertyDescriptor = getDeclaredPropertyDescriptorIncludeParents(parentClazz, name);
                }
            }
            return propertyDescriptor;
        }

        public static final List<PropertyDescriptor> getDeclaredPropertyDescriptorIncludeParents(Class<?> clazz) {
            List<PropertyDescriptor> propertyDescriptorList = new ArrayList<>(Arrays.asList(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz)));

            Class<?> parentClazz = clazz.getSuperclass();
            if (parentClazz != null) {
                List<PropertyDescriptor> parentPropertyDescriptorList = getDeclaredPropertyDescriptorIncludeParents(parentClazz);
                if (!CollectionSugar.isEmpty(parentPropertyDescriptorList))
                    propertyDescriptorList.addAll(parentPropertyDescriptorList);
            }
            return propertyDescriptorList;
        }

        private static final boolean isBuiltIn(PropertyDescriptor propertyDescriptor, Object bean) {
            return propertyDescriptor.getName().equals("class")
                    || (propertyDescriptor.getName().equals("beanFactory") && DynamicProxySugar.isCglibProxy(bean));
        }

        public static final Map<String, Class<?>> toNameClassMap(Class<?> clazz) {
            Map<String, Class<?>> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))
                    .filter(c -> !c.getName().equals("class"))
                    .collect(Collectors.toMap(c -> c.getName(), c -> c.getPropertyType()));
            return map;
        }


        public static final Map<String, Object> toNameValueMap(Object bean) {
            Map<String, Object> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean))
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property)), HashMap::putAll);
            return map;
        }

        public static final Map<String, Object> toNameValueMapExceptNull(Object bean) {
            Map<String, Object> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean) && getValue(bean, c) != null)
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property)), HashMap::putAll);
            return map;
        }


        public static final Map<String, PropertyDescriptor> toNamePropertyMap(Object bean) {
            Map<String, PropertyDescriptor> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean))
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), _property), HashMap::putAll);
            return map;
        }

        public static final Map<String, PropertyDescriptor> toNamePropertyMapExceptNull(Object bean) {
            Map<String, PropertyDescriptor> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean) && getValue(bean, c) != null)
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), _property), HashMap::putAll);
            return map;
        }


        public static final Map<String, String> toNameStringMap(Object bean) {
            Map<String, String> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean))
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property).toString()), HashMap::putAll);
            return map;
        }

        public static final Map<String, String> toNameStringMapExceptNull(Object bean) {
            Map<String, String> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !isBuiltIn(c, bean))
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property).toString()), HashMap::putAll);
            return map;
        }

        public static final PropertyDescriptor getPropertyDescriptor(Object entity, String propertyName) {
            return BeanUtils.getPropertyDescriptor(entity.getClass(), propertyName);
        }

        public static final Object getValue(Object bean, PropertyDescriptor propertyDescriptor) {
            try {
                return propertyDescriptor.getReadMethod().invoke(bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static final Object getValue(Object bean, String propertyName) {
            try {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
                return propertyDescriptor.getReadMethod().invoke(bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static Object setValue(Object bean, String propertyName, Object value) {
            try {
                PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
                return propertyDescriptor.getWriteMethod().invoke(bean, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static Object setValueIfPresent(Object bean, String propertyName, Object value) {
            try {
                if (exist(bean, propertyName)) {
                    PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
                    return propertyDescriptor.getWriteMethod().invoke(bean, value);
                } else
                    return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static boolean exist(Object bean, String propertyName) {
            PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(bean.getClass(), propertyName);
            return propertyDescriptor != null;
        }

        public static <T extends Annotation> T getAnnotationIncludeParents(Class<?> clazz, PropertyDescriptor propertyDescriptor, Class<T> annotationClass) {
            try {
                String fieldName = propertyDescriptor.getName();
                return BeanSugar.Fields.getDeclaredFieldIncludeParents(clazz, fieldName).getAnnotation(annotationClass);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static Annotation[] getAnnotationsIncludeParents(Class<?> clazz, PropertyDescriptor propertyDescriptor) {
            try {
                String fieldName = propertyDescriptor.getName();
                return BeanSugar.Fields.getDeclaredFieldIncludeParents(clazz, fieldName).getAnnotations();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static <T extends Annotation> Boolean isAnnotationPresentIncludeParents(Class<?> clazz, PropertyDescriptor propertyDescriptor, Class<T> annotationClass) {
            try {
                String fieldName = propertyDescriptor.getName();
                return BeanSugar.Fields.getDeclaredFieldIncludeParents(clazz, fieldName).isAnnotationPresent(annotationClass);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public static <T> String getPropertyName(LambdaSupplier<T> fn) {
            String methodName = getMethodName(fn);
            String fieldName = convertMethodNameToPropertyName(methodName);
            return fieldName;
        }

        public static <T> String getPropertyName(LambdaConsumer<T> fn) {
            String methodName = getMethodName(fn);
            String fieldName = convertMethodNameToPropertyName(methodName);
            return fieldName;
        }

        public static <T> String getPropertyName(LambdaFunction<T, ?> fn) {
            String methodName = getMethodName(fn);
            String fieldName = convertMethodNameToPropertyName(methodName);
            return fieldName;
        }

        public static <T> String getPropertyName(LambdaBiConsumer<T, ?> fn) {
            String methodName = getMethodName(fn);
            String fieldName = convertMethodNameToPropertyName(methodName);
            return fieldName;
        }

        private static <T> String getMethodName(LambdaSupplier<T> fn) {
            SerializedLambda serializedLambda = ClassSugar.invoke(fn, "writeReplace");
            return serializedLambda.getImplMethodName();
        }

        private static <T> String getMethodName(LambdaConsumer<T> fn) {
            SerializedLambda serializedLambda = ClassSugar.invoke(fn, "writeReplace");
            return serializedLambda.getImplMethodName();
        }

        private static <T> String getMethodName(LambdaFunction<T, ?> fn) {
            SerializedLambda serializedLambda = ClassSugar.invoke(fn, "writeReplace");
            return serializedLambda.getImplMethodName();
        }

        private static <T> String getMethodName(LambdaBiConsumer<T, ?> fn) {
            SerializedLambda serializedLambda = ClassSugar.invoke(fn, "writeReplace");
            return serializedLambda.getImplMethodName();
        }

        private static String convertMethodNameToPropertyName(String name) {
            if (name.startsWith("is")) {
                name = name.substring(2);
            } else if (name.startsWith("get") || name.startsWith("set")) {
                name = name.substring(3);
            } else {
                throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }

            if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
                name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
            }

            return name;
        }
    }


    public abstract static class Fields {
        public static final Field getDeclaredFieldIncludeParents(Class<?> clazz, String name) throws NoSuchFieldException {
            List<String> fieldNameList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().map(Field::getName).collect(Collectors.toList());
            Field field;
            if (fieldNameList.contains(name)) {
                field = clazz.getDeclaredField(name);
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                if (parentClazz == null) {
                    throw new NoSuchFieldException(name);
                } else {
                    field = getDeclaredFieldIncludeParents(parentClazz, name);
                }
            }
            return field;
        }

        public static final List<Field> getDeclaredFieldIncludeParents(Class<?> clazz) {
            List<Field> fieldList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().collect(Collectors.toList());
            Class<?> parentClazz = clazz.getSuperclass();
            if (parentClazz != null) {
                List<Field> parentFieldList = getDeclaredFieldIncludeParents(parentClazz);
                if (!CollectionSugar.isEmpty(parentFieldList))
                    fieldList.addAll(parentFieldList);
            }
            return fieldList;
        }

        public static final Field getFieldIncludeParents(Class<?> clazz, String name) throws NoSuchFieldException {
            List<String> fieldNameList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().map(Field::getName).collect(Collectors.toList());
            Field field;
            if (fieldNameList.contains(name)) {
                field = clazz.getField(name);
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                if (parentClazz == null) {
                    throw new NoSuchFieldException(name);
                } else {
                    field = getDeclaredFieldIncludeParents(parentClazz, name);
                }
            }
            return field;
        }

        public static final List<Field> getFieldIncludeParents(Class<?> clazz) {
            List<Field> fieldList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().collect(Collectors.toList());
            Class<?> parentClazz = clazz.getSuperclass();
            if (parentClazz != null) {
                List<Field> parentFieldList = getFieldIncludeParents(parentClazz);
                if (!CollectionSugar.isEmpty(parentFieldList))
                    fieldList.addAll(parentFieldList);
            }
            return fieldList;
        }
    }


    public abstract static class Methods {
        public static final Method getDeclaredMethodIncludeParents(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
            List<String> methodNameList = new ArrayList<>(Arrays.asList(clazz.getDeclaredMethods())).stream().map(Method::getName).collect(Collectors.toList());
            Method method;
            if (methodNameList.contains(name)) {
                method = clazz.getDeclaredMethod(name, parameterTypes);
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                if (parentClazz == null) {
                    throw new NoSuchMethodException(name);
                } else {
                    method = getDeclaredMethodIncludeParents(parentClazz, name, parameterTypes);
                }
            }
            return method;
        }

        public static final Method getMethodIncludeParents(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
            List<String> methodNameList = new ArrayList<>(Arrays.asList(clazz.getMethods())).stream().map(Method::getName).collect(Collectors.toList());
            Method method;
            if (methodNameList.contains(name)) {
                method = clazz.getMethod(name, parameterTypes);
            } else {
                Class<?> parentClazz = clazz.getSuperclass();
                if (parentClazz == null) {
                    throw new NoSuchMethodException(name);
                } else {
                    method = getMethodIncludeParents(parentClazz, name, parameterTypes);
                }
            }
            return method;
        }
    }


}
