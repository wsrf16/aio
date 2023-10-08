package com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper;

import com.aio.portable.swiss.spring.factories.processor.propertysource.convert.PropertyNameValueConvert;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

public class PropertySourceWrapper extends PropertySource<Object> implements PropertySourceWrapperAction {
    private final PropertySource<?> propertySource;
    private final PropertyNameValueConvert convert;

    public PropertySource<?> getPropertySource() {
        return this.propertySource;
    }

    public PropertyNameValueConvert getConvert() {
        return this.convert;
    }

    public PropertySourceWrapper(PropertySource<?> propertySource) {
        super(propertySource.getName(), propertySource.getSource());
        this.propertySource = propertySource;
        this.convert = PropertyNameValueConvert.NO_OP;
    }


    public PropertySourceWrapper(PropertySource<?> propertySource, PropertyNameValueConvert convert) {
        super(propertySource.getName(), propertySource.getSource());
        this.propertySource = propertySource;
        this.convert = convert;
    }


    @Override
    public Object getProperty(String name) {
        Object property = PropertySourceWrapperAction.super.getProperty(name);
        return property;
    }
}
