package com.aio.portable.swiss.suite.eventbus.subscriber.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class HttpAttempt {
    private HttpMethod method;
    private String url;
    private HttpHeaders headers;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

}
