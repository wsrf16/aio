package com.aio.portable.swiss.suite.eventbus.subscriber.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class HttpAttempt {
    private HttpMethod httpMethod;
    private String url;
    private HttpHeaders httpHeaders;

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

}
