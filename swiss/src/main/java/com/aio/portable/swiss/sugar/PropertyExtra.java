package com.aio.portable.swiss.sugar;

import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class PropertyExtra {

    public final static Map<String, Object> getNameValue(Object bean) {
//        Stream<PropertyDescriptor> propertyDescriptorStream
        Map<String, Object> map = Arrays.stream(BeanUtils.getPropertyDescriptors(bean.getClass()))
                .filter(c -> !c.getName().equals("class"))
//                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                .collect(HashMap::new, (_map, _property) -> _map.put(_property.getName(), getValue(bean, _property)), HashMap::putAll);
        return map;
    }

    public final static Map<String, Class> getNameClass(Class clazz) {
//        Stream<PropertyDescriptor> propertyDescriptorStream
        Map<String, Class> map = Arrays.stream(BeanUtils.getPropertyDescriptors(clazz))
                .filter(c -> !c.getName().equals("class"))
                .collect(Collectors.toMap(c -> c.getName(), c -> c.getPropertyType()));
        return map;
    }



//    public final static Map<String, Object> getNameValue(Class clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
////        Stream<PropertyDescriptor> propertyDescriptorStream
////        new PropertyDescriptor("id", Person.class);
//        Object bean = clazz.getDeclaredConstructor().newInstance();
//        Map<String, Object> map = getNameValue(bean);
//        return map;
//    }

    private static Object getValue(Object bean, PropertyDescriptor c) {
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
