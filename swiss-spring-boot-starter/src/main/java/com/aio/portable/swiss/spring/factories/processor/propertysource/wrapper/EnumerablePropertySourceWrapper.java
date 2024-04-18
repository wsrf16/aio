package com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper;

import com.aio.portable.swiss.spring.factories.processor.propertysource.convert.PropertyNameValueConvert;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;

public class EnumerablePropertySourceWrapper<T> extends EnumerablePropertySource<T> implements PropertySourceWrapperAction {
    private final EnumerablePropertySource<T> propertySource;
    private final PropertyNameValueConvert convert;

    public EnumerablePropertySource<T> getPropertySource() {
        return this.propertySource;
    }

    public PropertyNameValueConvert getConvert() {
        return this.convert;
    }

    public EnumerablePropertySourceWrapper(EnumerablePropertySource<T> propertySource, PropertyNameValueConvert convert) {
        super(propertySource.getName(), propertySource.getSource());
        this.propertySource = propertySource;
        this.convert = convert;
    }

    @Override
    public Object getProperty(String name) {
        Object property = PropertySourceWrapperAction.super.getProperty(name);
        return property;
    }

    @Override
    public String[] getPropertyNames() {
        return this.propertySource.getPropertyNames();
    }
}
