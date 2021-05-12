package com.aio.portable.swiss.factories.autoconfigure;

import com.aio.portable.swiss.factories.autoconfigure.properties.RestTemplateProperties;
import com.aio.portable.swiss.suite.net.protocol.http.RestTemplater;
import org.apache.http.client.HttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@Configuration
@ConditionalOnClass({RestTemplate.class, RestTemplateBuilder.class, HttpClient.class})
//@EnableConfigurationProperties(RestTemplateProperties.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration.class)
public class RestTemplateAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RestTemplateProperties.class)
    @ConfigurationProperties("spring.rest")
    @ConditionalOnProperty("spring.rest.agent.port")
    public RestTemplateProperties restTemplateProperties() {
        return new RestTemplateProperties();
    }

    @Bean
    @ConditionalOnBean({RestTemplateBuilder.class, RestTemplateProperties.class, })
    public RestTemplate proxyRestTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateProperties restTemplateProperties) {
        boolean agent = restTemplateProperties.getAgent().isEnabled();
        String agentHost = restTemplateProperties.getAgent().getHost();
        int agentPort = restTemplateProperties.getAgent().getPort();
        RestTemplate restTemplate;
        if (agent)
            restTemplate = RestTemplater.Build.setProxyRestTemplate(restTemplateBuilder.build(), agentHost, agentPort);
        else
            restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }

    @Bean
    @ConditionalOnBean({RestTemplateBuilder.class})
    @ConditionalOnMissingBean(RestTemplateProperties.class)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }

    @Bean
    @ConditionalOnBean({RestTemplateBuilder.class})
    public RestTemplate skipSSLRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = RestTemplater.Build.setSkipSSLRestTemplate(restTemplateBuilder.build());
        return restTemplate;
    }





}
