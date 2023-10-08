package com.aio.portable.park.postprocessor;

import com.aio.portable.swiss.spring.factories.processor.propertysource.PropertySourceBeanDefinitionRegistryPostProcessor;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.PropertySource;

import java.util.Objects;

@Configuration
public class PropertySourceConvert extends PropertySourceBeanDefinitionRegistryPostProcessor {
    public Object propertyNameValueConvert(PropertySource<?> propertySource, KeyValuePair<String, Object> nameValuePair) {
        String key = nameValuePair.getKey();
        Object value = nameValuePair.getValue();
        if (value != null) {
            if (Objects.equals(value, "hello world"))
                return (value + "222222");
            if (Objects.equals(key, "swagger.api-info.title"))
                return (value + "222222");
            if (Objects.equals(value, "对外接口在线文档"))
                return (value + "222222");
        }
        return value;
    }

}
