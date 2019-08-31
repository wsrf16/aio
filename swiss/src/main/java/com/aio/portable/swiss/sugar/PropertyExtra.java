package com.aio.portable.swiss.sugar;

//import com.sun.javafx.collections.MappingChange;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class PropertyExtra {
    public static Map<String, Object> getPropertyNameValue(Object bean) {
//        Stream<PropertyDescriptor> propertyDescriptorStream
        Map<String, Object> map = Arrays.stream(BeanUtils.getPropertyDescriptors(bean.getClass()))
                .filter(c -> !c.getName().equals("class"))
                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
        return map;
    }

    public static Map<String, Class> getPropertyNameClass(Object bean) {
//        Stream<PropertyDescriptor> propertyDescriptorStream
        Map<String, Class> map = Arrays.stream(BeanUtils.getPropertyDescriptors(bean.getClass()))
                .filter(c -> !c.getName().equals("class"))
                .collect(Collectors.toMap(c -> c.getName(), c -> c.getPropertyType()));
        return map;
    }

    public static Map<String, Object> getPropertyNameValue(Class clazz) throws IllegalAccessException, InstantiationException {
//        Stream<PropertyDescriptor> propertyDescriptorStream
//        new PropertyDescriptor("id", Person.class);
        Object bean = clazz.newInstance();
        Map<String, Object> map = getPropertyNameValue(bean);
        return map;
    }

    public static Map<String, Class> getPropertyNameClass(Class clazz) throws IllegalAccessException, InstantiationException {
//        Stream<PropertyDescriptor> propertyDescriptorStream
//        new PropertyDescriptor("id", Person.class);
        Object bean = clazz.newInstance();
        Map<String, Class> map = getPropertyNameClass(bean);
        return map;
    }


    private static Object getKeyValue(Object bean, PropertyDescriptor c) {
        try {
            return c.getReadMethod().invoke(bean);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
