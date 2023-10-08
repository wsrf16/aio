package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@TestComponent
public class RestTemplaterTest {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate skipSSLRestTemplate;

    @Test
    public void foobar() {
        ResponseEntity<String> stringResponseEntity1 = RestTemplater.get(restTemplate, String.class, "https://10.124.154.8/a/login", RestTemplater.Headers.newContentTypeApplicationJson(), null);
        ResponseEntity<String> stringResponseEntity2 = RestTemplater.get(skipSSLRestTemplate, String.class, "https://10.124.154.8/a/login", RestTemplater.Headers.newContentTypeApplicationJson(), null);


        {
            // https://www.yxgapp.com/
            // https://www.facebook.com/
            String url = "http://www.yxgapp.com/";
            ResponseEntity objectResponseEntity = RestTemplater.get(skipSSLRestTemplate, String.class, url, RestTemplater.Headers.newContentTypeApplicationJson(), null);
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


    @Test
    public void todo() {
        ResponseEntity<Map<String, Object>> response = RestTemplater.client(restTemplate)
                .url("http://localhost:8888/abc")
                .addParam("k", 8)
                .body("{k:0}")
                .putFor(new ParameterizedTypeReference<HashMap<String, Object>>() {});
        Map<String, Object> body = response.getBody();

    }
}
