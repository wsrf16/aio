package com.aio.portable.swiss.suite.net.tcp.http;

import com.aio.portable.swiss.sugar.location.UrlSugar;
import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import com.aio.portable.swiss.suite.log.facade.LogBase;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RestTemplater<T, R> {
    private RestTemplate restTemplate;
    private HttpMethod method;
    private HttpHeaders headers = new HttpHeaders();
    private String uri;
    private Map<String, Object> queryParams = new HashMap<>();
    private Map<String, Object> uriParams = new HashMap<>();
    private T body;
    private LogBase log;

    public static final RestTemplater create(RestTemplate restTemplate) {
        RestTemplater restTemplater = new RestTemplater();
        restTemplater.restTemplate = restTemplate;
        return restTemplater;
    }

    public static final RestTemplater create(RestTemplate restTemplate, LogBase log) {
        RestTemplater restTemplater = new RestTemplater();
        restTemplater.restTemplate = restTemplate;
        restTemplater.log = log;
        return restTemplater;
    }

    public RestTemplater method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public RestTemplater clearHeaders() {
        this.headers = new HttpHeaders();
        return this;
    }

    public RestTemplater header(String name, String value) {
        if (this.headers == null)
            this.headers = new HttpHeaders();
        this.headers.set(name, value);
        return this;
    }

    public RestTemplater headers(HttpHeaders headers) {
        if (this.headers == null)
            this.headers = new HttpHeaders();
        this.headers.setAll(headers.toSingleValueMap());
        return this;
    }

    public RestTemplater header(Map<String, Object> headers) {
        if (this.headers == null)
            this.headers = new HttpHeaders();
        headers.entrySet().forEach(c -> {
            String key = c.getKey();
            String value = c.getValue() == null ? null : (String) c.getValue();
            this.headers.set(key, value);
        });
        return this;
    }


    public RestTemplater contentTypeApplicationJson() {
        header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return this;
    }

    public RestTemplater contentTypeApplicationForm() {
        header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return this;
    }

    public RestTemplater contentTypeApplicationXml() {
        header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);
        return this;
    }

    public RestTemplater uri(String uri) {
        this.uri = uri;
        return this;
    }

    public RestTemplater body(T body) {
        this.body = body;
        return this;
    }

    public RestTemplater clearQueryParams() {
        this.queryParams.clear();
        return this;
    }

    public RestTemplater queryParam(String key, Object value) {
        this.queryParams.put(key, value);
        return this;
    }

    public RestTemplater queryParams(Map<String, Object> params) {
        this.queryParams.putAll(params);
        return this;
    }


    public RestTemplater clearURIParams() {
        this.uriParams.clear();
        return this;
    }

    public RestTemplater uriParam(String key, Object value) {
        this.uriParams.put(key, value);
        return this;
    }

    public RestTemplater uriParams(Map<String, Object> params) {
        this.uriParams.putAll(params);
        return this;
    }


    public ResponseEntity<R> retrieve(Class<R> responseClazz) {
        String uuid = IDS.uuid();
        if (log != null) {
            HashMap<String, Object> req = new HashMap(8);
            req.put("url", this.uri);
            req.put("uriParams", this.uriParams);
            req.put("queryParams", this.queryParams);
            req.put("headers", this.headers);
            req.put("method", this.method);
            req.put("body", this.body);
            log.i(MessageFormat.format("{0}({1})", "api-call-request", uuid), req);
        }

        ResponseEntity<R> responseEntity;
        try {
            responseEntity = RestTemplater.exchangeFor(this.restTemplate,
                    responseClazz,
                    this.uri,
                    this.method,
                    this.headers,
                    this.body == null ? new HashMap<String, Object>() : body,
                    this.queryParams,
                    this.uriParams);
            if (log != null) {
                log.i(MessageFormat.format("{0}({1})", "api-call-response", uuid), responseEntity);
            }
            return responseEntity;
        } catch (Exception e) {
            HttpHeaders httpHeaders = Headers.newHttpHeaders();
            httpHeaders.add("reason", e.getMessage());
            responseEntity = new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            if (log != null)
                log.e(MessageFormat.format("{0}({1})", "api-call-error", uuid), e);
        }
        return responseEntity;
    }

    public ResponseEntity<R> retrieve(ParameterizedTypeReference<R> responseTypeReference) {
        String uuid = IDS.uuid();
        if (log != null) {
            HashMap<String, Object> req = new HashMap(8);
            req.put("url", this.uri);
            req.put("uriParams", this.uriParams);
            req.put("queryParams", this.queryParams);
            req.put("headers", this.headers);
            req.put("method", this.method);
            req.put("body", this.body);
            log.i(MessageFormat.format("{0}({1})", "api-call-request", uuid), req);
        }

        ResponseEntity<R> responseEntity;
        try {
            responseEntity = RestTemplater.exchangeFor(this.restTemplate,
                    responseTypeReference,
                    this.uri,
                    this.method,
                    this.headers,
                    this.body == null ? new HashMap<String, Object>() : body,
                    this.queryParams,
                    this.uriParams);
            if (log != null) {
                log.i(MessageFormat.format("{0}({1})", "api-call-response", uuid), responseEntity);
            }
        } catch (Exception e) {
            HttpHeaders httpHeaders = Headers.newHttpHeaders();
            httpHeaders.add("reason", e.getMessage());
            responseEntity = new ResponseEntity<>(httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            if (log != null)
                log.e(MessageFormat.format("{0}({1})", "api-call-error", uuid), e);
        }
        return responseEntity;
    }



    public <T> ResponseEntity<T> forward(HttpServletRequest request, String shimPath, Class<T> clazz) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        HttpMethod httpMethod = RestTemplater.getHttpMethodFrom(request);
        HttpHeaders httpHeaders = RestTemplater.getRequestHeadersFrom(request);
        String queryString = StringUtils.isEmpty(request.getQueryString()) ? "" : "?" + request.getQueryString();
        shimPath = getString(shimPath);
        String requestURI = StringSugar.trimStart(request.getServletPath(), shimPath);

        String body = RestTemplater.getRequestBody(request);
        httpHeaders.remove("host");

        if (!httpHeaders.containsKey("Scheme-Server-Port")) {
            throw new RuntimeException("Please special header: Scheme-Server-Port.");
        }
        String schemeServerPort = httpHeaders.getFirst("Scheme-Server-Port");
        String url = UrlSugar.concat(schemeServerPort, requestURI + queryString);

        ResponseEntity<T> responseEntity = RestTemplater.create(restTemplate)
                .uri(url)
                .headers(httpHeaders)
                .method(httpMethod)
                .body(body)
                .retrieve(clazz);
        return responseEntity;
    }

    public <T> ResponseEntity<T> forward(HttpServletRequest request, Class<T> clazz) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String className = stackTrace[2].getClassName();
        String methodName = stackTrace[2].getMethodName();

        Class<Object> apiClazz = ClassLoaderSugar.forName(className);
        Method method = Arrays.stream(apiClazz.getMethods()).filter(c -> c.getName().contains(methodName)).findFirst().get();

        RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
        String methodValue = methodAnnotation.value()[0];
        RequestMapping clazzAnnotation = apiClazz.getAnnotation(RequestMapping.class);
        String clazzValue = clazzAnnotation.value()[0];
        String shimContextPath = StringSugar.trimEnd(UrlSugar.concat(clazzValue, methodValue), "/**");
        return this.forward(request, shimContextPath, clazz);
    }

    private static final String getString(String slimPath) {
        if (StringUtils.isEmpty(slimPath))
            return "";
        slimPath = "/" + StringSugar.trim(slimPath, "/") + "/";
        return slimPath;
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

//    public static final void setProxyRequestFactory(RestTemplate restTemplate, String host, int port) {
//        SimpleClientHttpRequestFactory factory = HttpRequestFactory.buildProxySimpleClientHttpRequestFactory(host, port);
//        restTemplate.setRequestFactory(factory);
//    }
//
//    public static final void setProxyRequestFactory(RestTemplate restTemplate, String host, int port, String username, String password) {
//        HttpComponentsClientHttpRequestFactory factory = HttpRequestFactory.buildProxyHttpComponentsClientHttpRequestFactory(host, port, username, password);
//        restTemplate.setRequestFactory(factory);
//    }

    public static final boolean setHttpProxy(RestTemplate restTemplate, Proxy proxy) {
        if (restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setProxy(proxy);
            return true;
        }
        return false;
    }

    public static final boolean setHttpProxy(RestTemplate restTemplate, String host, int port) {
        if (restTemplate.getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory()).setProxy(proxy);
            return true;
        }
        return false;
    }

//    public static final void setSkipSSLRequestFactory(RestTemplate restTemplate) {
//        SkipSSLHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLHttpRequestFactory();
//        restTemplate.setRequestFactory(factory);
//    }
//
//    public static final void setSkipSSLRequestFactory(RestTemplate restTemplate, String host, int port) {
//        SkipSSLHttpRequestFactory factory = HttpRequestFactory.buildSkipSSLHttpRequestFactory(host, port);
//        restTemplate.setRequestFactory(factory);
//    }


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

    public static <RESP> ResponseEntity<RESP> exchangeFor(RestTemplate restTemplate, Class<RESP> responseClazz, String url, HttpMethod method, HttpHeaders headers, Object body, Map<String, ?> params, Map<String, ?> uriVariables) {
        uriVariables = uriVariables == null ? new HashMap<>() : uriVariables;

        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, method, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

    public static <RESP, REQ> ResponseEntity<RESP> exchangeFor(RestTemplate restTemplate, ParameterizedTypeReference<RESP> responseClazz, String url, HttpMethod method, HttpHeaders headers, REQ body, Map<String, ?> params, Map<String, ?> uriVariables) {
        uriVariables = uriVariables == null ? new HashMap<>() : uriVariables;

        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, method, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

//        restTemplate.getForObject("http://example.com/hotels/{hotel}/bookings/{booking}", String.class, new HashMap<>());
    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, Class<RESP> responseClazz, String url, HttpHeaders headers, Map<String, Object> params, Map<String, ?> uriVariables) {
        uriVariables = uriVariables == null ? new HashMap<>() : uriVariables;

        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, HttpMethod.GET, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> get(RestTemplate restTemplate, ParameterizedTypeReference<RESP> responseClazz, String url, HttpHeaders headers, Map<String, Object> params, Map<String, ?> uriVariables) {
        uriVariables = uriVariables == null ? new HashMap<>() : uriVariables;

        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(null, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, HttpMethod.GET, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> post(RestTemplate restTemplate, Class<RESP> responseClazz, String url, HttpHeaders headers, Object body, Map<String, Object> params, Map<String, ?> uriVariables) {
        uriVariables = uriVariables == null ? new HashMap<>() : uriVariables;

        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, HttpMethod.POST, httpEntity, responseClazz, uriVariables);
        return responseEntity;
    }

    public static <RESP> ResponseEntity<RESP> post(RestTemplate restTemplate, ParameterizedTypeReference<RESP> responseTypeReference, String url, HttpHeaders headers, Object body, Map<String, Object> params, Map<String, ?> uriVariables) {
        uriVariables = uriVariables == null ? new HashMap<>() : uriVariables;

        String u = UrlSugar.appendQueries(url, params);
        HttpEntity<?> httpEntity = httpEntity(body, headers);
        ResponseEntity<RESP> responseEntity = restTemplate.exchange(u, HttpMethod.POST, httpEntity, responseTypeReference, uriVariables);
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

    public static HttpHeaders getRequestHeadersFrom(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            List<String> headerValues = Collections.list(request.getHeaders(headerName));
            headers.put(headerName, headerValues);
        }
        return headers;
    }

    public static HttpMethod getHttpMethodFrom(HttpServletRequest request) {
        String method = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);
        return httpMethod;
    }

    public static String getRequestBody(HttpServletRequest request) {
        try {
            return request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }













//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, REQ body, Class<RESP> responseClazz, Map<String, ?> uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseClazz, uriVariables);
//        return responseEntity;
//    }
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, HttpHeaders headers, REQ body, ParameterizedTypeReference<RESP> responseClazz, Map<String, ?> uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseClazz, uriVariables);
//        return responseEntity;
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, Class<RESP> responseClazz, Map<String, ?> uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return get(restTemplate, url, headers, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, ParameterizedTypeReference<RESP> responseClazz, Map<String, ?> uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return get(restTemplate, url, headers, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, REQ body , Class<RESP> responseClazz, Map<String, ?> uriVariables) {
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return post(restTemplate, url, headers, body, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, HttpHeaders headers, REQ body, ParameterizedTypeReference<RESP> responseClazz, Map<String, ?> uriVariables) {
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
//    public static final <RESP, REQ> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, REQ body, Class<RESP> responseClazz, Map<String, ?> uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, body, responseClazz, uriVariables);
//        return responseEntity;
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> exchangeJsonUTF8(RestTemplate restTemplate, String url, HttpMethod method, ParameterizedTypeReference<RESP> responseClazz, Map<String, ?> uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        ResponseEntity<RESP> responseEntity = exchange(restTemplate, url, method, headers, responseClazz, uriVariables);
//        return responseEntity;
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, Class<RESP> responseClazz, Map<String, ?> uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return get(restTemplate, url, headers, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP> ResponseEntity<RESP> getJsonUTF8(RestTemplate restTemplate, String url, ParameterizedTypeReference<RESP> responseClazz, Map<String, ?> uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return get(restTemplate, url, headers, responseClazz, uriVariables);
//    }
//
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, Class<RESP> responseClazz, Map<String, ?> uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return post(restTemplate, url, headers, body, responseClazz, uriVariables);
//    }
//
//    @Deprecated
//    public static final <RESP, REQ> ResponseEntity<RESP> postJsonUTF8(RestTemplate restTemplate, String url, REQ body, ParameterizedTypeReference<RESP> responseClazz, Map<String, ?> uriVariables) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        return post(restTemplate, url, headers, body, responseClazz, uriVariables);
//    }
}
