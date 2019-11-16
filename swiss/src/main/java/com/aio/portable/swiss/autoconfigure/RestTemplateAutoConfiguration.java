package com.aio.portable.swiss.autoconfigure;

import com.aio.portable.swiss.autoconfigure.properties.resttemplate.RestTemplateProperties;
import com.aio.portable.swiss.structure.net.protocol.http.RestTemplater;
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
@ConditionalOnClass({RestTemplate.class, RestTemplateBuilder.class})
//@EnableConfigurationProperties(RestTemplateProperties.class)
@AutoConfigureAfter(org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration.class)
public class RestTemplateAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RestTemplateProperties.class)
    @ConfigurationProperties("spring.rest")
    @ConditionalOnProperty("spring.rest.agent.port")
    public RestTemplateProperties restTemplateConfig() {
        return new RestTemplateProperties();
    }

    @Bean
    @ConditionalOnBean({RestTemplateProperties.class, RestTemplateBuilder.class})
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateProperties restTemplateProperties) {
        boolean agent = restTemplateProperties.getAgent().isEnable();
        String agentHost = restTemplateProperties.getAgent().getHost();
        int agentPort = restTemplateProperties.getAgent().getPort();
        RestTemplate restTemplate;
        if (agent)
            restTemplate = RestTemplater.Build.buildProxyRestTemplate(restTemplateBuilder, agentHost, agentPort);
        else
            restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }

    @Bean
    @ConditionalOnBean({RestTemplateBuilder.class})
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate rawRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        return restTemplate;
    }


}
