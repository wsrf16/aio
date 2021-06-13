package com.aio.portable.park.config;

import com.aio.portable.swiss.hamlet.bean.HamletBeanConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfig extends HamletBeanConfig {
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return super.threadPoolTaskScheduler();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return super.objectMapper();
    }

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
}
