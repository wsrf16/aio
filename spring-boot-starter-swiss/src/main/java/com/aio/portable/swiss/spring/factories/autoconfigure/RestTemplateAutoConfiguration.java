package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.spring.factories.autoconfigure.properties.RestTemplateProperties;
import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@ConditionalOnClass({RestTemplate.class, RestTemplateBuilder.class})
//@EnableConfigurationProperties(RestTemplateProperties.class)
@ConditionalOnMissingBean({RestTemplate.class})
@AutoConfigureAfter(org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration.class)
//@ConditionalOnProperty(value = "spring.rest.agent.enabled", havingValue = "true", matchIfMissing = true)
public class RestTemplateAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RestTemplateProperties.class)
    @ConfigurationProperties("spring.rest")
    @ConditionalOnProperty("spring.rest.agent.port")
    public RestTemplateProperties restTemplateProperties() {
        return new RestTemplateProperties();
    }

    @Bean("restTemplate")
//    @Primary
    @ConditionalOnBean({RestTemplateBuilder.class, RestTemplateProperties.class})
    public RestTemplate proxyRestTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateProperties restTemplateProperties) {
        RestTemplateProperties.Agent agent = restTemplateProperties.getAgent();
        boolean enabled = agent.isEnabled();
        String agentHost = agent.getHost();
        int agentPort = agent.getPort();
        RestTemplate restTemplate = restTemplateBuilder.build();
        if (enabled)
            RestTemplater.setProxyRequestFactory(restTemplate, agentHost, agentPort);
        restTemplate.setErrorHandler(SILENCE_RESPONSE_ERROR_HANDLER);
        return restTemplate;
    }

    @Bean("restTemplate")
//    @Primary
    @ConditionalOnBean({RestTemplateBuilder.class})
    @ConditionalOnMissingBean(RestTemplateProperties.class)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setErrorHandler(SILENCE_RESPONSE_ERROR_HANDLER);
        return restTemplate;
    }


    @Bean("skipSSLRestTemplate")
    @ConditionalOnBean({RestTemplateBuilder.class, RestTemplateProperties.class})
    public RestTemplate proxySkipSSLRestTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateProperties restTemplateProperties) {
        RestTemplateProperties.Agent agent = restTemplateProperties.getAgent();
        boolean enabled = agent.isEnabled();
        String agentHost = agent.getHost();
        int agentPort = agent.getPort();
        RestTemplate restTemplate = restTemplateBuilder.build();
        if (enabled)
            RestTemplater.setSkipSSLRequestFactory(restTemplate, agentHost, agentPort);
        else
            RestTemplater.setSkipSSLRequestFactory(restTemplate);
        restTemplate.setErrorHandler(SILENCE_RESPONSE_ERROR_HANDLER);
        return restTemplate;
    }

    @Bean("skipSSLRestTemplate")
    @ConditionalOnBean({RestTemplateBuilder.class})
    @ConditionalOnMissingBean(RestTemplateProperties.class)
    public RestTemplate skipSSLRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RestTemplater.setSkipSSLRequestFactory(restTemplate);
        restTemplate.setErrorHandler(SILENCE_RESPONSE_ERROR_HANDLER);
        return restTemplate;
    }


    private static final ResponseErrorHandler SILENCE_RESPONSE_ERROR_HANDLER = new DefaultResponseErrorHandler() {
        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        }

        @Override
        protected boolean hasError(HttpStatus statusCode) {
            return super.hasError(statusCode);
        }
    };
}
