package com.york.portable.park.config.mybatis;

import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MybatisPropertiesConfiguration {
    @Bean("masterMybatisProperties")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public MybatisProperties masterMybatisProperties() {
        return new MybatisProperties();
    }

    @Bean("slaveMybatisProperties")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public MybatisProperties slaveMybatisProperties() {
        return new MybatisProperties();
    }
}
