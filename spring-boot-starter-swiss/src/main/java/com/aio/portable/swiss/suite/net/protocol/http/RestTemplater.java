package com.aio.portable.swiss.suite.net.protocol.http;

import com.aio.portable.swiss.sugar.StringSugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.bean.BeanSugar;
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
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RestTemplater {
    public final static class Http {
        public final static HttpHeaders jsonHttpHeader() {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return headers;
        }

        public final static HttpHeaders jsonUTF8HttpHeader() {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            return headers;
        }

        public final static HttpHeaders formHttpHeader() {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            return headers;
        }

        public final static String addQueries(String uri, Object bean) {
            Map<String, Object> map = BeanSugar.PropertyDescriptors.toNameValueMap(bean);
            String queryParams = map.entrySet().stream().map(c -> MessageFormat.format("{0}={1}", c.getKey(), c.getValue() == null ? "" : c.getValue().toString())).collect(Collectors.joining("&"));

            while (StringUtils.endsWithIgnoreCase(uri, "/")) {
                uri = StringSugar.removeEnd(uri, "/");
            }
            return MessageFormat.format("{0}?{1}", uri, queryParams);
        }

        public static String buildBasicAuthorization(String username, String password) {
            final String plainCreds = MessageFormat.format("{0}:{1}", username, password);
            return MessageFormat.format("Basic {0}", JDKBase64Convert.encodeToString(plainCreds.getBytes()));
        }

//        public static Map.Entry<String, String> buildBearerAuthorizationHeader(String token) {
//            final String base64Creds = token;
//
//            Map.Entry<String, String> entry = new HashMap<String, String>() {
//                {
//                    put(HttpHeaders.AUTHORIZATION, "Bearer " + base64Creds);
//                }
//            }.entrySet().stream().findFirst().get();
//            return entry;
//        }





        @Deprecated
        public final static HttpHeaders jsonHttpHead() {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return headers;
        }
    }

    public final static class Build {
        public final static RestTemplate buildProxyRestTemplate(RestTemplate restTemplate, String host, int port, String username, String password) {
            HttpComponentsClientHttpRequestFactory factory = HttpRequestFactory.buildProxyHttpComponentsClientHttpRequestFactory(host, port, username, password);

            restTemplate.setRequestFactory(factory);
            return restTemplate;
        }

        public final static RestTemplate buildProxyRestTemplate(RestTemplate restTemplate, String host, int port) {
            SimpleClientHttpRequestFactory factory = HttpRequestFactory.buildProxySimpleClientHttpRequestFactory(host, port);
            restTemplate.setRequestFactory(factory);
            return restTemplate;
        }

        public final static RestTemplate buildSkipSSLRestTemplate(RestTemplate restTemplate) {
            SkipSSLSimpleClientHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLSimpleClientHttpRequestFactory();
            restTemplate.setRequestFactory(factory);
            return restTemplate;
        }

        public final static RestTemplate buildSkipSSLRestTemplate(RestTemplate restTemplate, String host, int port) {
            SkipSSLSimpleClientHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLSimpleClientHttpRequestFactory(host, port);
            restTemplate.setRequestFactory(factory);
            return restTemplate;
        }




        @Deprecated
        public final static RestTemplate setSkipSSLRestTemplate(RestTemplate restTemplate) {
            SkipSSLSimpleClientHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLSimpleClientHttpRequestFactory();
            restTemplate.setRequestFactory(factory);
            return restTemplate;
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
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));

            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setProxy(proxy);

            return factory;
        }

        public static SkipSSLSimpleClientHttpRequestFactory buildSkipSSLSimpleClientHttpRequestFactory() {
            SkipSSLSimpleClientHttpRequestFactory factory = new SkipSSLSimpleClientHttpRequestFactory();
            factory.setReadTimeout(5000);
            factory.setConnectTimeout(15000);
            return factory;
        }

        public static SkipSSLSimpleClientHttpRequestFactory buildSkipSSLSimpleClientHttpRequestFactory(String host, int port) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));

            SkipSSLSimpleClientHttpRequestFactory factory = new SkipSSLSimpleClientHttpRequestFactory();
            factory.setProxy(proxy);
            factory.setReadTimeout(5000);
            factory.setConnectTimeout(15000);

            return factory;
        }

    }

    private static <T> HttpEntity<T> httpEntity(T body, HttpHeaders headers) {
        HttpEntity<T> httpEntity;
        if (body == null && headers == null)
            httpEntity = new HttpEntity<>(null, null);
        else if (body != null && headers == null)
            httpEntity = new HttpEntity<>(body, null);
        else if (body == null && headers != null)
            httpEntity = new HttpEntity<>(null, headers);
        else
            httpEntity = new HttpEntity<>(body, headers);
        return httpEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, Object body, Class<RESP> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP, REQ> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, REQ body, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    //        restTemplate.getForObject("http://example.com/hotels/{hotel}/bookings/{booking}", String.class, new HashMap<>());
    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, String url, HttpHeaders headers, Class<RESP> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, String url, HttpHeaders headers, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> post(RestTemplate restTemplate, String url, HttpHeaders headers, Object body, Class<RESP> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> post(RestTemplate restTemplate, String url, HttpHeaders headers, Object body, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, Object body, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, Object body, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> exchange(RestTemplate restTemplate, String url, HttpMethod method, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, method, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, String url, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, String url, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> post(RestTemplate restTemplate, String url, Object body, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> post(RestTemplate restTemplate, String url, Object body, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, responseType, uriVariables);
        return responseEntity;
    }





























    @Deprecated
    public static <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, REQ body, Class<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseType, uriVariables);
        return responseEntity;
    }

    @Deprecated
    public static <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, REQ body, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseType, uriVariables);
        return responseEntity;
    }

    @Deprecated
    public static <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, Class<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, responseType, uriVariables);
    }

    @Deprecated
    public static <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, responseType, uriVariables);
    }

    @Deprecated
    public static <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, REQ body , Class<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, headers, body, responseType, uriVariables);
    }

    @Deprecated
    public static <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, REQ body, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, headers, body, responseType, uriVariables);
    }







    @Deprecated
    public static <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, REQ body, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseType, uriVariables);
        return responseEntity;
    }

    @Deprecated
    public static <RESP> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, responseType, uriVariables);
        return responseEntity;
    }

    @Deprecated
    public static <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, responseType, uriVariables);
    }

    @Deprecated
    public static <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return get(restTemplate, url, headers, responseType, uriVariables);
    }


    @Deprecated
    public static <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, Class<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, headers, body, responseType, uriVariables);
    }

    @Deprecated
    public static <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, ParameterizedTypeReference<RESP> responseType, Object... uriVariables) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return post(restTemplate, url, headers, body, responseType, uriVariables);
    }
}
