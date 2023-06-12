package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
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


        {
            // https://www.yxgapp.com/
            // https://www.facebook.com/
            String url = "http://www.yxgapp.com/";
            ResponseEntity objectResponseEntity = RestTemplater.get(skipSSLRestTemplate, url, RestTemplater.Headers.newContentTypeApplicationJson(), String.class);
        }

//        HttpClient httpClient = HttpClients.custom()
//                .setRetryHandler((exception, executionCount, context) -> {
//                    if (executionCount > 3) {
//                        log.warn("Maximum retries {} reached", 3);
//                        return false;
//                    }
//                    return false;
//                })
//                .build();
//
//        ((HttpComponentsClientHttpRequestFactory) restTemplate.getRequestFactory()).getHttpClient()
    }


    public void todo() {

    }
}
