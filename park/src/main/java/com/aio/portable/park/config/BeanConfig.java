package com.aio.portable.park.config;

import com.aio.portable.swiss.hamlet.bean.HamletBeanConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class BeanConfig extends HamletBeanConfig {
    @Bean
    public static ObjectMapper objectMapper() {
        return HamletBeanConfig.objectMapper();
    }

    @Bean
    public ThreadPoolTaskScheduler migrateThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}
