package com.aio.portable.swiss.suite.net.tcp.http;

import com.aio.portable.swiss.sugar.location.UrlSugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import com.aio.portable.swiss.suite.log.facade.LogBase;
import com.aio.portable.swiss.suite.log.facade.LogHub;
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
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class RestTemplater<T, R> {
    private RestTemplate restTemplate;
    private HttpHeaders headers = new HttpHeaders();
    private String url;
    private Map<String, Object> params = new HashMap<>();
    private T body;
    private Class<R> responseClazz;
    private ParameterizedTypeReference<R> responseTypeReference;
    private LogBase log;

    public static final RestTemplater client(RestTemplate restTemplate) {
        RestTemplater restTemplater = new RestTemplater();
        restTemplater.restTemplate = restTemplate;
        return restTemplater;
    }

    public static final RestTemplater client(RestTemplate restTemplate, LogBase log) {
        RestTemplater restTemplater = new RestTemplater();
        restTemplater.restTemplate = restTemplate;
        restTemplater.log = log;
        return restTemplater;
    }

    public RestTemplater headerContentTypeApplicationJson() {
        this.headers = Headers.newContentTypeApplicationJson();
        return this;
    }

    public RestTemplater headerContentTypeApplicationForm() {
        this.headers = Headers.newContentTypeApplicationForm();
        return this;
    }

    public RestTemplater headerContentTypeApplicationXml() {
        this.headers = Headers.newContentTypeApplicationXml();
        return this;
    }

    public RestTemplater header(HttpHeaders headers) {
        this.headers = headers;
        return this;
    }

    public RestTemplater addHeader(String name, String value) {
        this.headers.add(name, value);
        return this;
    }

    public RestTemplater addHeaders(HttpHeaders headers) {
        this.headers.addAll(headers);
        return this;
    }

    public RestTemplater url(String url) {
        this.url = url;
        return this;
    }
    public RestTemplater body(T body) {
        this.body = body;
        return this;
    }
//    public RestTemplater responseFor(Class<R> responseClazz) {
//        this.responseClazz = responseClazz;
//        return this;
//    }
    public RestTemplater responseFor(ParameterizedTypeReference<R> responseTypeReference) {
        this.responseTypeReference = responseTypeReference;
        return this;
    }
    public RestTemplater params(Map<String, Object> params) {
        this.params = params;
        return this;
    }
    public RestTemplater addParam(String key, Object value) {
        this.params.put(key, value);
        return this;
    }

    public RestTemplater addParams(Map<String, Object> params) {
        this.params.putAll(params);
        return this;
    }

    public ResponseEntity<R> exchangeFor(HttpMethod method, Class<R> responseClazz) {
        if (log != null) {
            HashMap<String, Object> req = new HashMap(8);
            req.put("url", this.url);
            req.put("headers", this.headers);
            req.put("method", method.name());
            req.put("body", this.body);
            log.i("", req);
        }

        ResponseEntity<R> responseEntity;
        try {
            responseEntity = RestTemplater.exchangeFor(this.restTemplate,
                    responseClazz,
                    this.url,
                    method,
                    this.headers,
                    this.body,
                    this.params);
            return responseEntity;
        } catch (Exception e) {
            HttpHeaders httpHeaders = Headers.newHttpHeaders();
            httpHeaders.add("Reason", e.getMessage());
            responseEntity = new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            log.e(e);
        }
        return responseEntity;
    }

    public ResponseEntity<R> exchangeFor(HttpMethod method, ParameterizedTypeReference<R> responseTypeReference) {
        if (log != null) {
            HashMap<String, Object> req = new HashMap(8);
            req.put("url", this.url);
            req.put("headers", this.headers);
            req.put("method", method.name());
            req.put("body", this.body);
            log.i("", req);
        }

        ResponseEntity<R> responseEntity;
        try {
            responseEntity = RestTemplater.exchangeFor(this.restTemplate,
                    responseTypeReference,
                    this.url,
                    method,
                    this.headers,
                    this.body,
                    this.params);
        } catch (Exception e) {
            HttpHeaders httpHeaders = Headers.newHttpHeaders();
            httpHeaders.add("Reason", e.getMessage());
            responseEntity = new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            log.e(e);
        }
        return responseEntity;
    }

    public ResponseEntity<R> getFor(Class<R> responseClazz) {
        return exchangeFor(HttpMethod.GET, responseClazz);
    }

    public ResponseEntity<R> postFor(Class<R> responseClazz) {
        return exchangeFor(HttpMethod.POST, responseClazz);
    }

    public ResponseEntity<R> deleteFor(Class<R> responseClazz) {
        return exchangeFor(HttpMethod.DELETE, responseClazz);
    }

    public ResponseEntity<R> headFor(Class<R> responseClazz) {
        return exchangeFor(HttpMethod.HEAD, responseClazz);
    }

    public ResponseEntity<R> optionsFor(Class<R> responseClazz) {
        return exchangeFor(HttpMethod.OPTIONS, responseClazz);
    }

    public ResponseEntity<R> patchFor(Class<R> responseClazz) {
        return exchangeFor(HttpMethod.PATCH, responseClazz);
    }

    public ResponseEntity<R> putFor(Class<R> responseClazz) {
        return exchangeFor(HttpMethod.PUT, responseClazz);
    }

    public ResponseEntity<R> traceFor(Class<R> responseClazz) {
        return exchangeFor(HttpMethod.TRACE, responseClazz);
    }

    public ResponseEntity<R> getFor(ParameterizedTypeReference<R> responseClazz) {
        return exchangeFor(HttpMethod.GET, responseClazz);
    }

    public ResponseEntity<R> postFor(ParameterizedTypeReference<R> responseClazz) {
        return exchangeFor(HttpMethod.POST, responseClazz);
    }

    public ResponseEntity<R> deleteFor(ParameterizedTypeReference<R> responseClazz) {
        return exchangeFor(HttpMethod.DELETE, responseClazz);
    }

    public ResponseEntity<R> headFor(ParameterizedTypeReference<R> responseClazz) {
        return exchangeFor(HttpMethod.HEAD, responseClazz);
    }

    public ResponseEntity<R> optionsFor(ParameterizedTypeReference<R> responseClazz) {
        return exchangeFor(HttpMethod.OPTIONS, responseClazz);
    }

    public ResponseEntity<R> patchFor(ParameterizedTypeReference<R> responseClazz) {
        return exchangeFor(HttpMethod.PATCH, responseClazz);
    }

    public ResponseEntity<R> putFor(ParameterizedTypeReference<R> responseClazz) {
        return exchangeFor(HttpMethod.PUT, responseClazz);
    }

    public ResponseEntity<R> traceFor(ParameterizedTypeReference<R> responseClazz) {
        return exchangeFor(HttpMethod.TRACE, responseClazz);
    }

    public static final class Headers {
        public static HttpHeaders newHttpHeaders() {
            HttpHeaders headers = new HttpHeaders();
            return headers;
        }

        public static HttpHeaders newContentTypeApplicationJson() {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            return headers;
        }

        public static HttpHeaders newContentTypeApplicationJsonUtf8() {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
            return headers;
        }

        public static HttpHeaders newContentTypeApplicationForm() {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            return headers;
        }

        public static HttpHeaders newContentTypeApplicationXml() {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
            return headers;
        }

        public static void addContentTypeApplicationJson(HttpHeaders headers) {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        }

        public static void addContentTypeApplicationJsonUtf8(HttpHeaders headers) {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        }

        public static void addContentTypeForm(HttpHeaders headers) {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        }

        public static void addAuthorization(HttpHeaders headers, String username, String password) {
            headers.add(HttpHeaders.AUTHORIZATION,
                    MessageFormat.format("Basic {0}",
                            JDKBase64Convert.encodeToString(MessageFormat.format("{0}:{1}", username, password))));
        }

        public static KeyValuePair<String, String> buildBasicAuthorizationHeaderPair(String username, String password) {
            String plainCreds = MessageFormat.format("{0}:{1}", username, password);
            String token = JDKBase64Convert.encodeToString(plainCreds);
            KeyValuePair<String, String> entry = new KeyValuePair<>(HttpHeaders.AUTHORIZATION, "Basic " + token);
            return entry;
        }

        public static KeyValuePair<String, String> buildBearerAuthorizationHeaderPair(String token) {
            KeyValuePair<String, String> entry = new KeyValuePair<>(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            return entry;
        }
    }

    public static final void setProxyRequestFactory(RestTemplate restTemplate, String host, int port) {
        SimpleClientHttpRequestFactory factory = HttpRequestFactory.buildProxySimpleClientHttpRequestFactory(host, port);
        restTemplate.setRequestFactory(factory);
    }

    public static final void setProxyRequestFactory(RestTemplate restTemplate, String host, int port, String username, String password) {
        HttpComponentsClientHttpRequestFactory factory = HttpRequestFactory.buildProxyHttpComponentsClientHttpRequestFactory(host, port, username, password);
        restTemplate.setRequestFactory(factory);
    }

    public static final void setSkipSSLRequestFactory(RestTemplate restTemplate) {
        SkipSSLSimpleClientHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLSimpleClientHttpRequestFactory();
        restTemplate.setRequestFactory(factory);
    }

    public static final void setSkipSSLRequestFactory(RestTemplate restTemplate, String host, int port) {
        SkipSSLSimpleClientHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLSimpleClientHttpRequestFactory(host, port);
        restTemplate.setRequestFactory(factory);
    }

    public static final class HttpRequestFactory {
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

    public static <RESP> ResponseEntity<RESP> exchangeFor(RestTemplate restTemplate, Class<RESP> responseClazz, String url, HttpMethod method, HttpHeaders headers, Object body, Map<String, ?> params, Object... uriVariables) {
        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, method, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

    public static <RESP, REQ> ResponseEntity<RESP> exchangeFor(RestTemplate restTemplate, ParameterizedTypeReference<RESP> responseClazz, String url, HttpMethod method, HttpHeaders headers, REQ body, Map<String, ?> params, Object... uriVariables) {
        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, method, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

//        restTemplate.getForObject("http://example.com/hotels/{hotel}/bookings/{booking}", String.class, new HashMap<>());
    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, Class<RESP> responseClazz, String url, HttpHeaders headers, Map<String, Object> params, Object... uriVariables) {
        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, HttpMethod.GET, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, ParameterizedTypeReference<RESP> responseClazz, String url, HttpHeaders headers, Map<String, Object> params, Object... uriVariables) {
        String u = UrlSugar.appendQueries(url, params);

        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, HttpMethod.GET, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> post(RestTemplate restTemplate, Class<RESP> responseClazz, String url, HttpHeaders headers, Object body, Map<String, Object> params, Object... uriVariables) {
        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, HttpMethod.POST, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> post(RestTemplate restTemplate, ParameterizedTypeReference<RESP> responseClazz, String url, HttpHeaders headers, Object body, Map<String, Object> params, Object... uriVariables) {
        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, HttpMethod.POST, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }



    public final static RetryTemplate newRetryTemplate(int maxAttempts) {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(new ExponentialBackOffPolicy());

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);
        retryTemplate.setRetryPolicy(retryPolicy);

        return retryTemplate;
    }
























//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, REQ body, Class<RESP> responseClazz, Object... uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseClazz, uriVariables);
//        return responseEntity;
//    }
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, REQ body, ParameterizedTypeReference<RESP> responseClazz, Object... uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseClazz, uriVariables);
//        return responseEntity;
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, Class<RESP> responseClazz, Object... uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return get(restTemplate, url, headers, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, ParameterizedTypeReference<RESP> responseClazz, Object... uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return get(restTemplate, url, headers, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, REQ body , Class<RESP> responseClazz, Object... uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return post(restTemplate, url, headers, body, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, REQ body, ParameterizedTypeReference<RESP> responseClazz, Object... uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return post(restTemplate, url, headers, body, responseClazz, uriVariables);
//    }
//
//
//
//
//
//
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, REQ body, Class<RESP> responseClazz, Object... uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseClazz, uriVariables);
//        return responseEntity;
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, ParameterizedTypeReference<RESP> responseClazz, Object... uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, responseClazz, uriVariables);
//        return responseEntity;
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, Class<RESP> responseClazz, Object... uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return get(restTemplate, url, headers, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, ParameterizedTypeReference<RESP> responseClazz, Object... uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return get(restTemplate, url, headers, responseClazz, uriVariables);
//    }
//
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, Class<RESP> responseClazz, Object... uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return post(restTemplate, url, headers, body, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, ParameterizedTypeReference<RESP> responseClazz, Object... uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return post(restTemplate, url, headers, body, responseClazz, uriVariables);
//    }
}
