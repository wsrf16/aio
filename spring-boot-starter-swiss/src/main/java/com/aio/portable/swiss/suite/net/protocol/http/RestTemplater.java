package com.aio.portable.swiss.suite.net.protocol.http;

import com.aio.portable.swiss.suite.net.protocol.http.resttemplate.SkipSSLSimpleClientHttpRequestFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class RestTemplater {
    public final static class Http {
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

    public final static class Build {
        public final static RestTemplate buildProxyRestTemplate(RestTemplateBuilder restTemplateBuilder, String host, int port, String username, String password) {
            HttpComponentsClientHttpRequestFactory factory = HttpRequestFactory.buildProxyHttpComponentsClientHttpRequestFactory(host, port, username, password);

            RestTemplate restTemplate = restTemplateBuilder.build();
            restTemplate.setRequestFactory(factory);
            return restTemplate;
        }

        public final static RestTemplate buildProxyRestTemplate(RestTemplateBuilder restTemplateBuilder, String host, int port) {
            SimpleClientHttpRequestFactory factory = HttpRequestFactory.buildProxySimpleClientHttpRequestFactory(host, port);

            RestTemplate restTemplate = restTemplateBuilder.build();
            restTemplate.setRequestFactory(factory);
            return restTemplate;
        }

//        public final static RestTemplate buildProxyRestTemplate(String host, int port) {
//            SimpleClientHttpRequestFactory factory = HttpRequestFactory.buildProxySimpleClientHttpRequestFactory(host, port);
//
//            return new RestTemplate(factory);
//        }

        public final static RestTemplate buildSkipSSLRestTemplate(RestTemplateBuilder restTemplateBuilder, ClientHttpRequestFactory factory){
            RestTemplate restTemplate = restTemplateBuilder.build();
            restTemplate.setRequestFactory(factory);
            return restTemplate;
        }

        public final static RestTemplate buildSkipSSLRestTemplate(RestTemplateBuilder restTemplateBuilder){
            SkipSSLSimpleClientHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLSimpleClientHttpRequestFactory();
            return buildSkipSSLRestTemplate(restTemplateBuilder, factory);
        }
    }

    public static class HttpRequestFactory {
        // http://www.it1352.com/215149.html
        public static HttpComponentsClientHttpRequestFactory buildProxyHttpComponentsClientHttpRequestFactory(String host, int port, String username, String password) {
            HttpHost httpHost = new HttpHost(host, port);

            CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(
                    new AuthScope(host, port),
                    new UsernamePasswordCredentials(username, password));

            HttpClient httpClient = HttpClientBuilder.create()
                    .setProxy(httpHost)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .disableCookieManagement()
                    .build();

            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
            factory.setHttpClient(httpClient);

            return factory;
        }

        public static SimpleClientHttpRequestFactory buildProxySimpleClientHttpRequestFactory(String host, int port) {
            SocketAddress address = new InetSocketAddress(host, port);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, address);

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setProxy(proxy);
//        factory.setReadTimeout(readTimeout);
//        factory.setConnectTimeout(connectionTimeout);

            return factory;
        }

        public static SkipSSLSimpleClientHttpRequestFactory buildSkipSSLSimpleClientHttpRequestFactory() {
            SkipSSLSimpleClientHttpRequestFactory factory = new SkipSSLSimpleClientHttpRequestFactory();
            factory.setReadTimeout(5000);
            factory.setConnectTimeout(15000);
            return factory;
        }

    }


    public static Map.Entry buildAuthorizationHead(String username, int password) {
        final String plainCreds = MessageFormat.format("{0}:{1}", username, password);

        final byte[] plainCredsBytes = plainCreds.getBytes();
        final byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        final String base64Creds = new String(base64CredsBytes);

        Map.Entry<String, String> entry = new HashMap<String, String>() {
            {
                put(HttpHeaders.AUTHORIZATION, "Basic " + base64Creds);
            }
        }.entrySet().stream().findFirst().get();
        return entry;
    }

    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, Class<RESP> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, parameterizedTypeReference, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, Class<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, parameterizedTypeReference, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, String url, HttpHeaders headers, Class<RESP> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, String url, HttpHeaders headers, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, parameterizedTypeReference, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, Class<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, responseType, uriVariables);
    }

    public static <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, parameterizedTypeReference, uriVariables);
    }

    public static <RESP, REQ> ResponseEntity<RESP> post(RestTemplate restTemplate, String url, REQ body, HttpHeaders headers, Class<RESP> responseType, Object... uriVariables) {
        HttpEntity<REQ> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP, REQ> ResponseEntity<RESP> post(RestTemplate restTemplate, String url, REQ body, HttpHeaders headers, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpEntity<REQ> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, parameterizedTypeReference, uriVariables);
        return responseEntity;
    }
    public static <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, HttpHeaders headers, Class<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, body, headers, responseType, uriVariables);
    }

    public static <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, HttpHeaders headers, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, body, headers, parameterizedTypeReference, uriVariables);
    }


























    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, parameterizedTypeReference, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, parameterizedTypeReference, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, String url, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, String url, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, parameterizedTypeReference, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, responseType, uriVariables);
    }

    public static <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, parameterizedTypeReference, uriVariables);
    }

    public static <RESP, REQ> ResponseEntity<RESP> post(RestTemplate restTemplate, String url, REQ body, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<REQ> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP, REQ> ResponseEntity<RESP> post(RestTemplate restTemplate, String url, REQ body, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<REQ> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, parameterizedTypeReference, uriVariables);
        return responseEntity;
    }
    public static <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, body, headers, responseType, uriVariables);
    }

    public static <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, ParameterizedTypeReference<RESP> parameterizedTypeReference, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, body, headers, parameterizedTypeReference, uriVariables);
    }
}
