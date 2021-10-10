package com.aio.portable.swiss.spring.factories.processor.propertysource;

import org.springframework.core.env.PropertySource;

import java.util.function.BiFunction;

class PropertySourceWrapper extends PropertySource<Object> {
    private PropertySource<?> propertySource;
    private BiFunction<String, Object, Object> convert;

    public PropertySourceWrapper(PropertySource<?> propertySource) {
        super(propertySource.getName(), propertySource.getSource());
        this.propertySource = propertySource;
        this.convert = (key, value) -> value;
    }


    public PropertySourceWrapper(PropertySource<?> propertySource, BiFunction<String, Object, Object> convert) {
        super(propertySource.getName(), propertySource.getSource());
        this.propertySource = propertySource;
        this.convert = convert;
    }


    @Override
    public Object getProperty(String s) {
        Object property = this.propertySource.getProperty(s);
        return this.convert.apply(s, property);
    }
}
