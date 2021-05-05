package com.aio.portable.park.config;

import com.aio.portable.swiss.config.MixedPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config")
//@PropertySource(value = "classpath:application.yml", factory = MixedPropertySourceFactory.class)
public class ApplicationConfig {
    private String abc;

    public String getAbc() {
        return abc;
    }

    public void setAbc(String abc) {
        this.abc = abc;
    }
}
