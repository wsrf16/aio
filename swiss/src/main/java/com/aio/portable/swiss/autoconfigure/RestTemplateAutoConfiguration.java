package com.aio.portable.swiss.autoconfigure;

import com.aio.portable.swiss.autoconfigure.properties.RestTemplateProperties;
import com.aio.portable.swiss.net.http.RestTemplater;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@Configuration
@ConditionalOnClass({RestTemplate.class, RestTemplateBuilder.class})
//@EnableConfigurationProperties(RestTemplateProperties.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration.class)
public class RestTemplateAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RestTemplateProperties.class)
    @ConfigurationProperties("spring.rest")
    public RestTemplateProperties restTemplateConfig() {
        return new RestTemplateProperties();
    }

    @Bean
    @ConditionalOnBean({RestTemplateProperties.class, RestTemplateBuilder.class})
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateProperties restTemplateProperties) {
        boolean httpDebug = restTemplateProperties.isHttpDebug();
        String debugHost = restTemplateProperties.getDebugHost();
        int debugPort = restTemplateProperties.getDebugPort();
        RestTemplate restTemplate;
        if (httpDebug)
            restTemplate = RestTemplater.buildProxyRestTemplate(restTemplateBuilder, debugHost, debugPort);
        else
            restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }
}
