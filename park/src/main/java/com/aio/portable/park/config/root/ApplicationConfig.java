package com.aio.portable.park.config.root;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "config")
//@PropertySource(value = "classpath:application.yml", factory = MixedPropertySourceFactory.class)
@Data
public class ApplicationConfig {
    private String abc;
}

