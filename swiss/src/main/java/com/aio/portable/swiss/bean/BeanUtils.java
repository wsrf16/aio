package com.aio.portable.swiss.bean;

import com.aio.portable.swiss.resource.ClassUtils;
import com.aio.portable.swiss.sugar.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BeanUtils {

    public static <T, NEXT> Boolean match(T match, T bean, Class<T> clazz) {
        Map<String, Object> nameBeanCondition = BeanUtils.PropertyDescriptors.getNameValue(match);
        Map<String, Object> nameValueBean = BeanUtils.PropertyDescriptors.getNameValue(bean);

        Set<String> nameList = nameBeanCondition.keySet();
        boolean equal = nameList.stream().allMatch(key -> {


            Boolean b;
            NEXT nextMatch = (NEXT)nameBeanCondition.get(key);
            NEXT nextBean = (NEXT)nameValueBean.get(key);
            if (nextMatch == null)
                b = true;
            else if (nextBean == null)
                b = false;
            else {
                Class<NEXT> nextClazz = (Class<NEXT>) nextMatch.getClass();
                if (ClassUtils.isSimpleValueType(nextClazz))
                    b = Objects.equals(nextMatch, nextBean);
                else
                    b = match(nextMatch, nextBean, nextClazz);
            }
            return b;
        });
        return equal;
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
                if (!CollectionUtils.isEmpty(parentPropertyDescriptorList))
                    propertyDescriptorList.addAll(parentPropertyDescriptorList);
            }
            return propertyDescriptorList;
        }


        public final static Map<String, Class> getNameClass(Class clazz) {
            Map<String, Class> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))
                    .filter(c -> !c.getName().equals("class"))
                    .collect(Collectors.toMap(c -> c.getName(), c -> c.getPropertyType()));
            return map;
        }


        public final static Map<String, Object> getNameValue(Object bean) {
            Map<String, Object> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(bean.getClass()))
                    .filter(c -> !c.getName().equals("class"))
                    //                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                    .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property)), HashMap::putAll);
            return map;
        }


        public static Object getValue(Object bean, PropertyDescriptor c) {
            try {
                return c.getReadMethod().invoke(bean);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
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
                if (!CollectionUtils.isEmpty(parentFieldList))
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
                if (!CollectionUtils.isEmpty(parentFieldList))
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
