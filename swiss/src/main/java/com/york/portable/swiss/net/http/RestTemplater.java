package com.york.portable.swiss.net.http;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
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
    public static RestTemplate buildProxyRestTemplate(String host, int port, String username, String password) {
        HttpComponentsClientHttpRequestFactory factory = buildProxyHttpComponentsClientHttpRequestFactory(host, port, username, password);

        return new RestTemplate(factory);
    }

    public static RestTemplate buildProxyRestTemplate(String host, int port) {
        SimpleClientHttpRequestFactory factory = buildProxySimpleClientHttpRequestFactory(host, port);

        return new RestTemplate(factory);
    }

    public static Map.Entry buildAuthorizationHead(String username, int password) {
        final String plainCreds = MessageFormat.format("{0}:{1}", username, password);

        final byte[] plainCredsBytes = plainCreds.getBytes();
        final byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        final String base64Creds = new String(base64CredsBytes);

        Map.Entry entry = new HashMap<String, String>() {
            {
                put(HttpHeaders.AUTHORIZATION, "Basic " + base64Creds);
            }
        }.entrySet().stream().findFirst().get();
        return entry;
    }

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

    public static <Res> ResponseEntity<Res> exchange(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, Class<Res> responseType) {
        HttpEntity httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Res> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType);
        return responseEntity;
    }

    public static <Res> ResponseEntity<Res> exchange(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, ParameterizedTypeReference parameterizedTypeReference) {
        HttpEntity httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Res> responseEntity = restTemplate.exchange(url, method, httpEntity, parameterizedTypeReference);
        return responseEntity;
    }

    public static <Res> ResponseEntity<Res> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, Class<Res> responseType) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<Res> responseEntity = exchange(restTemplate, url, method, headers, responseType);
        return responseEntity;
    }

    public static <Res> ResponseEntity<Res> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, ParameterizedTypeReference parameterizedTypeReference) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<Res> responseEntity = exchange(restTemplate, url, method, headers, parameterizedTypeReference);
        return responseEntity;
    }

    public static <Res> ResponseEntity<Res> get(RestTemplate restTemplate, String url, HttpHeaders headers, Class<Res> responseType) {
        HttpEntity httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Res> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return responseEntity;
    }

    public static <Res> ResponseEntity<Res> get(RestTemplate restTemplate, String url, HttpHeaders headers, ParameterizedTypeReference parameterizedTypeReference) {
        HttpEntity httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Res> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, parameterizedTypeReference);
        return responseEntity;
    }

    public static <Res> ResponseEntity<Res> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, Class<Res> responseType) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, responseType);
    }

    public static <Res> ResponseEntity<Res> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, ParameterizedTypeReference parameterizedTypeReference) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, parameterizedTypeReference);
    }

    public static <Res, Req> ResponseEntity<Res> post(RestTemplate restTemplate, String url, Req body, HttpHeaders headers, Class<Res> responseType) {
        HttpEntity httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Res> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType);
        return responseEntity;
    }

    public static <Res, Req> ResponseEntity<Res> post(RestTemplate restTemplate, String url, Req body, HttpHeaders headers, ParameterizedTypeReference parameterizedTypeReference) {
        HttpEntity httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<Res> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, parameterizedTypeReference);
        return responseEntity;
    }
    public static <Res, Req> ResponseEntity<Res> postJsonUTF8(RestTemplate restTemplate, String url, Req body, HttpHeaders headers, Class<Res> responseType) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, body, headers, responseType);
    }

    public static <Res, Req> ResponseEntity<Res> postJsonUTF8(RestTemplate restTemplate, String url, Req body, HttpHeaders headers, ParameterizedTypeReference parameterizedTypeReference) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, body, headers, parameterizedTypeReference);
    }
}
