package com.aio.portable.park.config.root;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "config")
//@PropertySource(value = "classpath:application.yml", factory = MixedPropertySourceFactory.class)
public class ApplicationConfig {
    private String cron;
    private String text;

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

