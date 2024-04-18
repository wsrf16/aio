package com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper;

import com.aio.portable.swiss.spring.factories.processor.propertysource.convert.PropertyNameValueConvert;
import org.springframework.core.env.SystemEnvironmentPropertySource;

public class SystemEnvironmentPropertySourceWrapper extends SystemEnvironmentPropertySource implements PropertySourceWrapperAction {
    private final SystemEnvironmentPropertySource propertySource;
    private final PropertyNameValueConvert convert;

    public SystemEnvironmentPropertySource getPropertySource() {
        return this.propertySource;
    }

    public PropertyNameValueConvert getConvert() {
        return this.convert;
    }

    public SystemEnvironmentPropertySourceWrapper(SystemEnvironmentPropertySource propertySource, PropertyNameValueConvert convert) {
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
