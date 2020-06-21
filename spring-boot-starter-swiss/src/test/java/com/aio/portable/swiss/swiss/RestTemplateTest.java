package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.suite.net.protocol.http.RestTemplater;
import com.aio.portable.swiss.suite.resource.PackageSugar;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@TestComponent
public class RestTemplateTest {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate skipSSLRestTemplate;

    @Test
    public void restTemplate() {
        ResponseEntity<String> stringResponseEntity1 = RestTemplater.getJsonUTF8(restTemplate, "https://10.124.154.8/a/login", RestTemplater.Http.jsonHttpHead(), String.class);
        ResponseEntity<String> stringResponseEntity2 = RestTemplater.getJsonUTF8(skipSSLRestTemplate, "https://10.124.154.8/a/login", RestTemplater.Http.jsonHttpHead(), String.class);
        ResponseEntity<String> stringResponseEntity3 = RestTemplater.getJsonUTF8(RestTemplater.Build.buildSkipSSLRestTemplate(restTemplateBuilder), "https://10.124.154.8/a/login", RestTemplater.Http.jsonHttpHead(), String.class);

    }

}
