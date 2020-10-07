package com.aio.portable.swiss.suite.eventbus.component.handler;

import com.aio.portable.swiss.sugar.SpringContextHolder;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.handler.http.HttpAttempt;
import com.aio.portable.swiss.suite.log.parts.LogThrowable;
import com.aio.portable.swiss.suite.net.protocol.http.RestTemplater;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.function.Function;

public class RestTemplateEventHandler extends SimpleEventHandler {
    public static RestTemplate restTemplate;

    public static void setRestTemplate(RestTemplate restTemplate) {
        RestTemplateEventHandler.restTemplate = restTemplate;
    }

    private HttpAttempt httpAttempt;

    protected String backURL;

//    protected String errorURL;

    public HttpAttempt getHttpAttempt() {
        return httpAttempt;
    }

    public void setHttpAttempt(HttpAttempt httpAttempt) {
        this.httpAttempt = httpAttempt;
    }

    public String getBackURL() {
        return backURL;
    }

    public void setBackURL(String backURL) {
        this.backURL = backURL;
    }

//    public String getErrorURL() {
//        return errorURL;
//    }
//
//    public void setErrorURL(String errorURL) {
//        this.errorURL = errorURL;
//    }

    static {
        restTemplate = SpringContextHolder.getApplicationContext().containsBean("restTemplate") ? (RestTemplate) SpringContextHolder.getApplicationContext().getBean("restTemplate") : new RestTemplate();
    }

    public RestTemplateEventHandler() {
        super();
        this.func = this::httpPush;
        this.succeedBack = this::echoBack;
        this.failedBack = this::echoError;
    }

    public RestTemplateEventHandler(@NotNull String name, @NotNull HttpAttempt httpAttempt) {
        super(name);
        this.httpAttempt = httpAttempt;

        this.func = this::httpPush;
        this.succeedBack = this::echoBack;
        this.failedBack = this::echoError;
    }

    @Override
    public <E extends Event> ResponseEntity<String> push(E event) {
        ResponseEntity<String> responseEntity = proxy(this::httpPush, event);
        return responseEntity;
    }

    public <E extends Event> ResponseEntity<String> proxy(Function<E, Object> func, E event) {
        ResponseEntity<String> responseEntity = null;
        Exception exp = null;

        for(int todo = 1; todo - 1 <= this.getRetry(); todo++) {
            try {
                responseEntity = (ResponseEntity<String>) func.apply(event);
                exp = null;
                break;
            } catch (Exception e) {
                e.printStackTrace();
                exp = e;
                responseEntity = null;
            }
        }

        if (exp != null) {
            if (StringUtils.hasLength(backURL)) {
                ResponseEntity<String> errorResponseEntity =
                        echoError(exp);
            }
        } else {
            if (StringUtils.hasLength(backURL)) {
                ResponseEntity<?> backResponseEntity =
                        echoBack(responseEntity);
            }
        }
        return responseEntity;
    }

    private HttpHeaders merge(HttpHeaders httpHeaders1, HttpHeaders httpHeaders2) {
        HttpHeaders httpHeaders;
        if (!CollectionUtils.isEmpty(httpHeaders1) && CollectionUtils.isEmpty(httpHeaders2)) {
            httpHeaders = httpHeaders1;
        } else if (CollectionUtils.isEmpty(httpHeaders1) && !CollectionUtils.isEmpty(httpHeaders2)) {
            httpHeaders = httpHeaders2;
        } else if (!CollectionUtils.isEmpty(httpHeaders1) && !CollectionUtils.isEmpty(httpHeaders2)) {
            httpHeaders1.addAll(httpHeaders2);
            httpHeaders = httpHeaders1;
        } else  {
            httpHeaders = new HttpHeaders();
        }
        return httpHeaders;
    }

    private <E extends Event> ResponseEntity<String> httpPush(E event) {
        HttpMethod method = httpAttempt.getMethod();
        Object source = event.getPayload();
        String url = httpAttempt.getUrl();
        HttpHeaders httpHeaders = merge(event.getHeaders(), httpAttempt.getHeaders());
        ResponseEntity<String> responseEntity;
        switch (method) {
            case DELETE: {
                responseEntity = RestTemplater.exchange(restTemplate, url, HttpMethod.DELETE, httpHeaders, source, String.class);
            }
            break;
            case GET: {
                String getURL = RestTemplater.Http.toURL(url, source);
                responseEntity = RestTemplater.exchange(restTemplate, getURL, HttpMethod.GET, httpHeaders, source, String.class);
            }
            break;
            case HEAD: {
                responseEntity = RestTemplater.exchange(restTemplate, url, HttpMethod.HEAD, httpHeaders, source, String.class);
            }
            break;
            case OPTIONS: {
                responseEntity = RestTemplater.exchange(restTemplate, url, HttpMethod.OPTIONS, httpHeaders, source, String.class);
            }
            break;
            case PATCH: {
                responseEntity = RestTemplater.exchange(restTemplate, url, HttpMethod.PATCH, httpHeaders, source, String.class);
            }
            break;
            case POST: {
                responseEntity = RestTemplater.exchange(restTemplate, url, HttpMethod.POST, httpHeaders, source, String.class);
            }
            break;
            case PUT: {
                responseEntity = RestTemplater.exchange(restTemplate, url, HttpMethod.PUT, httpHeaders, source, String.class);
            }
            break;
            case TRACE: {
                responseEntity = RestTemplater.exchange(restTemplate, url, HttpMethod.TRACE, httpHeaders, source, String.class);
            }
            break;
            default:
                throw new RuntimeException(MessageFormat.format("Not found method {0}.", method));
        }

        return responseEntity;
    }


    private ResponseEntity<String> echoError(Exception e) {
        HttpHeaders httpHeaders = RestTemplater.Http.jsonHttpHead();
        ResponseEntity<String> errorResponseEntity = RestTemplater.exchange(restTemplate, backURL, HttpMethod.POST, httpHeaders, LogThrowable.build(e), String.class);
        return errorResponseEntity;
    }

    private ResponseEntity<String> echoBack(Object responseEntity) {
        HttpHeaders httpHeaders = RestTemplater.Http.jsonHttpHead();
        ResponseEntity<String> backResponseEntity = RestTemplater.exchange(restTemplate, backURL, HttpMethod.POST, httpHeaders, responseEntity, String.class);
        return backResponseEntity;
    }

}
