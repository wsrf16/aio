package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.spring.factories.autoconfigure.properties.RestTemplateProperties;
import com.aio.portable.swiss.suite.net.tcp.http.HttpRequestFactory;
import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@ConditionalOnClass({RestTemplate.class, RestTemplateBuilder.class})
//@EnableConfigurationProperties(RestTemplateProperties.class)
@ConditionalOnMissingBean({RestTemplate.class})
@AutoConfigureAfter(org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration.class)
//@ConditionalOnProperty(value = "spring.rest.agent.enabled", havingValue = "true", matchIfMissing = true)
public class RestTemplateAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RestTemplateProperties.class)
    @ConfigurationProperties("spring.rest")
    @ConditionalOnProperty("spring.rest.proxy.port")
    public RestTemplateProperties restTemplateProperties() {
        return new RestTemplateProperties();
    }

    @Bean("restTemplate")
//    @Primary
    @ConditionalOnBean({RestTemplateBuilder.class, RestTemplateProperties.class})
    public RestTemplate proxyRestTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateProperties restTemplateProperties) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setErrorHandler(SILENCE_RESPONSE_ERROR_HANDLER);

        RestTemplateProperties.Proxy proxy = restTemplateProperties.getProxy();
        boolean enabled = proxy.isEnabled();
        String proxyHost = proxy.getHost();
        int proxyPort = proxy.getPort();
        HttpComponentsClientHttpRequestFactory requestFactory = enabled ?
                HttpRequestFactory.buildHttpComponentsClientHttpRequestFactory(false, proxyHost, proxyPort, null, null)
                : HttpRequestFactory.buildHttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);

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
//    @ConditionalOnClass({org.springframework.retry.backoff.BackOffPolicy.class})
//    @ConditionalOnClass(name = {"org.springframework.retry.backoff.BackOffPolicy"})
    public RestTemplate proxySkipSSLRestTemplate(RestTemplateBuilder restTemplateBuilder, RestTemplateProperties restTemplateProperties) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setErrorHandler(SILENCE_RESPONSE_ERROR_HANDLER);

        RestTemplateProperties.Proxy proxy = restTemplateProperties.getProxy();
        boolean enabled = proxy.isEnabled();
        String proxyHost = proxy.getHost();
        int proxyPort = proxy.getPort();
        HttpComponentsClientHttpRequestFactory requestFactory = enabled ?
                HttpRequestFactory.buildHttpComponentsClientHttpRequestFactory(true, proxyHost, proxyPort, null, null) : HttpRequestFactory.buildSkipSSLHttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);

        return restTemplate;
    }

    @Bean("skipSSLRestTemplate")
    @ConditionalOnBean({RestTemplateBuilder.class})
    @ConditionalOnMissingBean(RestTemplateProperties.class)
//    @ConditionalOnClass({org.springframework.retry.backoff.BackOffPolicy.class})
//    @ConditionalOnClass(name = {"org.springframework.retry.backoff.BackOffPolicy"})
    public RestTemplate skipSSLRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.setErrorHandler(SILENCE_RESPONSE_ERROR_HANDLER);

        HttpComponentsClientHttpRequestFactory requestFactory = HttpRequestFactory.buildSkipSSLHttpComponentsClientHttpRequestFactory();
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }


    private static final ResponseErrorHandler SILENCE_RESPONSE_ERROR_HANDLER = new DefaultResponseErrorHandler() {
        @Override
        public boolean hasError(ClientHttpResponse clientHttpResponse) {
            return false;
        }

        @Override
        public void handleError(ClientHttpResponse clientHttpResponse) {
        }

        @Override
        protected boolean hasError(HttpStatus statusCode) {
            return super.hasError(statusCode);
        }
    };
}
