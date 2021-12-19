package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@TestComponent
public class RestTemplateTest {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate skipSSLRestTemplate;

    @Test
    public void foobar() {
        ResponseEntity<String> stringResponseEntity1 = RestTemplater.get(restTemplate, "https://10.124.154.8/a/login", RestTemplater.Headers.newContentTypeApplicationJson(), String.class);
        ResponseEntity<String> stringResponseEntity2 = RestTemplater.get(skipSSLRestTemplate, "https://10.124.154.8/a/login", RestTemplater.Headers.newContentTypeApplicationJson(), String.class);
    }


    public void todo() {

    }
}
