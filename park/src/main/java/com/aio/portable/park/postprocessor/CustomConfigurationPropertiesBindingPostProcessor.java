package com.aio.portable.park.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomConfigurationPropertiesBindingPostProcessor extends ConfigurationPropertiesBindingPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return super.postProcessBeforeInitialization(bean, beanName);
    }
}
