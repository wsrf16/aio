package com.york.portable.swiss.autoconfigure;

import com.york.portable.swiss.net.http.RestTemplateConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnClass({RestTemplate.class})
public class RestTemplateAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RestTemplateConfig.class)
    @ConfigurationProperties("spring.rest")
    public RestTemplateConfig restTemplateConfig() {
        return new RestTemplateConfig();
    }

    @Bean
    @ConditionalOnBean(RestTemplateConfig.class)
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(RestTemplateConfig restTemplateConfig) {
        return restTemplateConfig.restTemplate();
    }
}
