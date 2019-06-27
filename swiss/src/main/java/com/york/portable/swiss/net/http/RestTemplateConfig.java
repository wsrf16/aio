package com.york.portable.swiss.net.http;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    private boolean httpDebug = false;
    private String debugHost = "127.0.0.1";
    private int debugPort = 8888;

    public boolean isHttpDebug() {
        return httpDebug;
    }

    public void setHttpDebug(boolean httpDebug) {
        this.httpDebug = httpDebug;
    }

    public String getDebugHost() {
        return debugHost;
    }

    public void setDebugHost(String debugHost) {
        this.debugHost = debugHost;
    }

    public int getDebugPort() {
        return debugPort;
    }

    public void setDebugPort(int debugPort) {
        this.debugPort = debugPort;
    }

    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate restTemplate() {
        RestTemplate restTemplate;
        if (httpDebug)
            restTemplate = RestTemplater.buildProxyRestTemplate(debugHost, debugPort);
        else
            restTemplate = new RestTemplate();
        return restTemplate;
    }

    public final static HttpHeaders jsonHttpHead() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return headers;
    }

    public final static HttpHeaders formHttpHead() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return headers;
    }
}
