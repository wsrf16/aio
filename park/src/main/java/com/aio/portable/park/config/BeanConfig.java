package com.aio.portable.park.config;

import com.aio.portable.swiss.hamlet.HamletBeanConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig extends HamletBeanConfig {
    @Bean
    public static ObjectMapper objectMapper() {
        return HamletBeanConfig.objectMapper();
    }
}
