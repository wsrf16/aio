package com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper;

import com.aio.portable.swiss.spring.factories.processor.propertysource.convert.PropertyNameValueConvert;
import org.springframework.core.env.MapPropertySource;

public class MapPropertySourceWrapper extends MapPropertySource implements PropertySourceWrapperAction {
    private final MapPropertySource propertySource;
    private final PropertyNameValueConvert convert;

    public MapPropertySource getPropertySource() {
        return this.propertySource;
    }

    public PropertyNameValueConvert getConvert() {
        return this.convert;
    }

    public MapPropertySourceWrapper(MapPropertySource propertySource, PropertyNameValueConvert convert) {
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
