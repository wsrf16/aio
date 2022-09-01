package com.aio.portable.park.postprocessor;

import com.aio.portable.swiss.spring.factories.processor.propertysource.PropertySourceBeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

//@Configuration
public class PropertySourceConvert extends PropertySourceBeanDefinitionRegistryPostProcessor {
    public Object propertyValueConvert(String key, Object value) {
        if (Objects.equals(value, "abc"))
            return ("v" + "222222");
        if (Objects.equals(key, "swagger.api-info.title"))
            return (value + "222222");
        if (Objects.equals(value, "对外接口在线文档"))
            return (value + "222222");
        return value;
    }

}
