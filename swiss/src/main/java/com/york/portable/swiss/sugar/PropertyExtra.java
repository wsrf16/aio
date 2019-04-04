package com.york.portable.swiss.sugar;

//import com.sun.javafx.collections.MappingChange;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class PropertyExtra {
    public static Map<String, Object> getPropertyNameValues(Object bean) {
//        Stream<PropertyDescriptor> propertyDescriptorStream
        Map<String, Object> map = Arrays.stream(PropertyUtils.getPropertyDescriptors(bean.getClass()))
                .filter(c -> !c.getName().equals("class"))
                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
        return map;
    }

    public static Map<String, Class> getPropertyNameTypesByBean(Object bean) {
//        Stream<PropertyDescriptor> propertyDescriptorStream
        Map<String, Class> map = Arrays.stream(PropertyUtils.getPropertyDescriptors(bean.getClass()))
                .filter(c -> !c.getName().equals("class"))
                .collect(Collectors.toMap(c -> c.getName(), c -> c.getPropertyType()));
        return map;
    }

    public static Map<String, Object> getPropertyNameValues(Class clazz) throws IllegalAccessException, InstantiationException {
//        Stream<PropertyDescriptor> propertyDescriptorStream
//        new PropertyDescriptor("id", Person.class);
        Object bean = clazz.newInstance();
        Map<String, Object> map = Arrays.stream(PropertyUtils.getPropertyDescriptors(clazz))
                .filter(c -> !c.getName().equals("class"))
                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
        return map;
    }

    public static Map<String, Class> getPropertyNameTypesByClass(Class clazz) throws IllegalAccessException, InstantiationException {
//        Stream<PropertyDescriptor> propertyDescriptorStream
//        new PropertyDescriptor("id", Person.class);
        Object bean = clazz.newInstance();
        Map<String, Class> map = Arrays.stream(PropertyUtils.getPropertyDescriptors(clazz))
                .filter(c -> !c.getName().equals("class"))
                .collect(Collectors.toMap(c -> c.getName(), c -> c.getPropertyType()));
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
