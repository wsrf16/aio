package com.aio.portable.swiss.spring.factories.processor.propertysource;

import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.stream.StreamSupport;

public class PropertySourcesConverter {

    PropertySourceFilter filter;
    PropertySourceConvert propertySourceConvert;
    PropertyValueConvert propertyValueConvert;

    public void setFilter(PropertySourceFilter filter) {
        this.filter = filter;
    }

    public void setPropertySourceConvert(PropertySourceConvert convert) {
        this.propertySourceConvert = convert;
    }

    public void setPropertyValueConvert(PropertyValueConvert convert) {
        this.propertyValueConvert = convert;
    }

    public boolean filter(PropertySource<?> propertySource) {
        return filter.test(propertySource);
    }



    public PropertySource<?> convert(PropertySource<?> propertySource) {
        return this.propertySourceConvert.apply(propertySource);
    }

    public void replace(MutablePropertySources propertySources) {
//        propertySources.stream()
        StreamSupport.stream(propertySources.spliterator(), false)
                .filter(c -> this.filter(c))
                .map(this::convert)
                .forEach(c -> {
                    propertySources.replace(c.getName(), c);
                });
    }

}
