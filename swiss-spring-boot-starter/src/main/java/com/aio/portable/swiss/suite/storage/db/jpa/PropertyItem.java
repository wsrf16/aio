package com.aio.portable.swiss.suite.storage.db.jpa;

import com.aio.portable.swiss.suite.bean.BeanSugar;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class PropertyItem {
    private String name;

    private Object value;

    private PropertyDescriptor propertyDescriptor;

    private Field field;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public PropertyDescriptor getPropertyDescriptor() {
        return propertyDescriptor;
    }

    public void setPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }







    public static final Map<String, PropertyItem> getNamePropertyItemMap(Object bean) {
        Class<?> clazz = bean.getClass();
        Map<String, PropertyItem> map = Arrays.stream(org.springframework.beans.BeanUtils.getPropertyDescriptors(clazz))
                .filter(c -> !c.getName().equals("class"))
//                .collect(Collectors.toMap(c -> c.getName(), c -> getKeyValue(bean, c)));
                .collect(HashMap::new, (_map, _property) -> {
                    String name = _property.getName();
                    Object value = BeanSugar.PropertyDescriptors.getValue(bean, _property);
                    Field field = null;
                    try {
                        field = BeanSugar.Fields.getDeclaredFieldIncludeParents(clazz, name);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    PropertyItem propertyItem = new PropertyItem();
                    propertyItem.setName(name);
                    propertyItem.setValue(value);
                    propertyItem.setField(field);
                    propertyItem.setPropertyDescriptor(_property);
                    _map.put(name, propertyItem);
                }, HashMap::putAll);
        return map;
    }
}
