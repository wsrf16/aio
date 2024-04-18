package com.aio.portable.swiss.spring.factories.processor.propertysource.convert;

import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.stream.StreamSupport;

public class PropertySourcesConverter {
    private static final LocalLog log = LocalLog.getLog(PropertySourcesConverter.class);

    PropertySourceFilter filter;
    PropertySourceConvert propertySourceConvert;
    PropertyNameValueConvert propertyNameValueConvert;

    public void setFilter(PropertySourceFilter filter) {
        this.filter = filter;
    }

    public void setPropertySourceConvert(PropertySourceConvert convert) {
        this.propertySourceConvert = convert;
    }

    public void setPropertyNameValueConvert(PropertyNameValueConvert convert) {
        this.propertyNameValueConvert = convert;
    }

    public boolean filter(PropertySource<?> propertySource) {
        return filter.test(propertySource);
    }



    public PropertySource<?> convert(PropertySource<?> propertySource) {
        return this.propertySourceConvert.apply(propertySource);
    }

    public void replace(MutablePropertySources propertySources) {
        StreamSupport.stream(propertySources.spliterator(), false)
                .filter(c -> this.filter(c))
                .map(this::convert)
                .forEach(c -> {
                    propertySources.replace(c.getName(), c);
                });
    }

}
