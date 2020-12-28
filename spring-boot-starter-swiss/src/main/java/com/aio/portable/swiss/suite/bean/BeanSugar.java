package com.aio.portable.swiss.suite.bean;

import com.aio.portable.swiss.suite.resource.ClassSugar;
import com.aio.portable.swiss.sugar.CollectionSugar;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BeanSugar {

    public final static <T> Boolean match(T match, T bean) {
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
     * @param matchList
     * @param beanList
     * @param <T>
     * @return
     */
    public final static <T> Boolean matchList(List<T> matchList, List<T> beanList) {
        Map<String, Object> nameValueMatch = BeanSugar.PropertyDescriptors.toNameValueMap(matchList.get(0));
        boolean equal = beanList.stream().anyMatch(bean -> {
            Map<String, Object> nameValueBean = BeanSugar.PropertyDescriptors.toNameValueMap(bean);

            boolean itemEqual = matchNameValueMap(nameValueMatch, nameValueBean);
            return itemEqual;
        });
        return equal;
    }

    private static <SUB> boolean matchNameValueMap(Map<String, Object> nameValueMatch, Map<String, Object> nameValueBean) {
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

    public final static String[] getNullProperties(Object src) {
        //1.获取Bean
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        //2.获取Bean的属性描述
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        //3.获取Bean的空属性
        Set<String> properties = new HashSet<>();
        for (PropertyDescriptor propertyDescriptor : pds) {
            String propertyName = propertyDescriptor.getName();
            Object propertyValue = srcBean.getPropertyValue(propertyName);
            if (StringUtils.isEmpty(propertyValue)) {
                srcBean.setPropertyValue(propertyName, null);
                properties.add(propertyName);
            }
        }
        return properties.toArray(new String[0]);
    }

    public final static void copyNotNullProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, BeanSugar.getNullProperties(source));
    }



    public abstract static class PropertyDescriptors {
        public final static PropertyDescriptor getDeclaredPropertyDescriptorIncludeParents(Class<?> clazz, String name) throws NoSuchFieldException {
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

        public final static List<PropertyDescriptor> getDeclaredPropertyDescriptorIncludeParents(Class<?> clazz) {
            List<PropertyDescriptor> propertyDescriptorList = new ArrayList<>(Arrays.asList(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz)));

            Class<?> parentClazz = clazz.getSuperclass();
            if (parentClazz != null) {
                List<PropertyDescriptor> parentPropertyDescriptorList = getDeclaredPropertyDescriptorIncludeParents(parentClazz);
                if (!CollectionSugar.isEmpty(parentPropertyDescriptorList))
                    propertyDescriptorList.addAll(parentPropertyDescriptorList);
            }
            return propertyDescriptorList;
        }


        public final static Map<String, Class> toNameClassMap(Class clazz) {
            Map<String, Class> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))
                    .filter(c -> !c.getName().equals("class"))
                    .collect(Collectors.toMap(c -> c.getName(), c -> c.getPropertyType()));
            return map;
        }


        public final static Map<String, Object> toNameValueMap(Object bean) {
            Map<String, Object> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !c.getName().equals("class"))
                    //                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property)), HashMap::putAll);
            return map;
        }

        public final static Map<String, Object> toNameValueMapExceptNull(Object bean) {
            Map<String, Object> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(pro -> !pro.getName().equals("class") && getValue(bean, pro) != null)
                    //                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property)), HashMap::putAll);
            return map;
        }


        public final static Map<String, PropertyDescriptor> toNamePropertyMap(Object bean) {
            Map<String, PropertyDescriptor> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !c.getName().equals("class"))
                    //                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), _property), HashMap::putAll);
            return map;
        }

        public final static Map<String, PropertyDescriptor> toNamePropertyMapExceptNull(Object bean) {
            Map<String, PropertyDescriptor> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(pro -> !pro.getName().equals("class") && getValue(bean, pro) != null)
                    //                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), _property), HashMap::putAll);
            return map;
        }


        public final static Map<String, String> toNameStringMap(Object bean) {
            Map<String, String> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !c.getName().equals("class"))
                    //                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property).toString()), HashMap::putAll);
            return map;
        }

        public final static Map<String, String> toNameStringMapExceptNull(Object bean) {
            Map<String, String> map = bean instanceof Map ? (Map) bean : Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !c.getName().equals("class"))
                    //                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property).toString()), HashMap::putAll);
            return map;
        }

        public static Object getValue(Object bean, PropertyDescriptor propertyDescriptor) {
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

        public static Object getValue(Object bean, String propertyName) {
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
    }


    public abstract static class Fields {
        public final static Field getDeclaredFieldIncludeParents(Class<?> clazz, String name) throws NoSuchFieldException {
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

        public final static List<Field> getDeclaredFieldIncludeParents(Class<?> clazz) {
            List<Field> fieldList = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())).stream().collect(Collectors.toList());
            Class<?> parentClazz = clazz.getSuperclass();
            if (parentClazz != null) {
                List<Field> parentFieldList = getDeclaredFieldIncludeParents(parentClazz);
                if (!CollectionSugar.isEmpty(parentFieldList))
                    fieldList.addAll(parentFieldList);
            }
            return fieldList;
        }

        public final static Field getFieldIncludeParents(Class<?> clazz, String name) throws NoSuchFieldException {
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

        public final static List<Field> getFieldIncludeParents(Class<?> clazz) {
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
        public final static Method getDeclaredMethodIncludeParents(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
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

        public final static Method getMethodIncludeParents(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
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
