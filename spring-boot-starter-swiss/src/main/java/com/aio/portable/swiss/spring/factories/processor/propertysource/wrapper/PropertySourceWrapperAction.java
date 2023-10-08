package com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper;

import com.aio.portable.swiss.spring.factories.processor.propertysource.convert.PropertyNameValueConvert;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import org.springframework.core.env.PropertySource;

public interface PropertySourceWrapperAction {
    PropertySource<?> getPropertySource();
    PropertyNameValueConvert getConvert();

    default Object getProperty(String name) {
        Object value = this.getPropertySource().getProperty(name);
        Object property = this.getConvert().apply(this.getPropertySource(), new KeyValuePair<>(name, value));
        return property;
    }
}
