package com.aio.portable.swiss.hamlet.interceptor.classic.log.request;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.hamlet.bean.ResponseBean;
import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import org.aspectj.lang.JoinPoint;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestRecord {
    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getGlobalId() {
        String globalId = StringUtils.isEmpty(this.getTraceId()) ?
                MessageFormat.format("{0}", this.getSpanId()) :
                MessageFormat.format("{0}-{1}", this.getTraceId(), this.getSpanId());
        return globalId;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public static void fillSpanId(HttpServletResponse response, String spanId) {
        if (response != null && !response.containsHeader(ResponseBean.SPAN_ID_HEADER))
            response.addHeader(ResponseBean.SPAN_ID_HEADER, spanId);
    }

    public static void addHeader(HttpServletResponse response, RequestRecord requestRecord) {
        if (response != null && !response.containsHeader(ResponseBean.SPAN_ID_HEADER)) {
            if (requestRecord.getSpanId() != null)
                response.addHeader(ResponseBean.SPAN_ID_HEADER, requestRecord.getSpanId());
            if (requestRecord.getTraceId() != null)
                response.addHeader(ResponseBean.SPAN_ID_HEADER, requestRecord.getTraceId());
        }
    }

    private String spanId;
    private String traceId;
    private String requestURL;
    private String requestMethod;
    private String remoteAddress;
    private String classMethod;
    private Object[] arguments;
    private Map<String, String> requestHeaders;

    private static String generateUniqueId() {
        return IDS.uuid();
    }

    public static RequestRecord newInstance(HttpServletRequest request, JoinPoint joinPoint) {
        String spanId = generateUniqueId();
        RequestRecord requestRecord = new RequestRecord();
        requestRecord.setSpanId(spanId);

        ThrowableSugar.runIfCatch(() -> {
            if (request != null) {
                requestRecord.setRemoteAddress(request.getRemoteAddr());
                String url = request.getRequestURL().toString() + (!StringUtils.hasText(request.getQueryString()) ? Constant.EMPTY : "?" + request.getQueryString());
                requestRecord.setRequestURL(url);
                requestRecord.setRequestMethod(request.getMethod());
                requestRecord.setRequestHeaders(parseHeader(request));
                requestRecord.setTraceId(request.getHeader(ResponseBean.TRACE_ID_HEADER));
            }
            if (joinPoint != null) {
                requestRecord.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "::" + joinPoint.getSignature().getName());

                Object[] args = filterArguments(joinPoint.getArgs());
                requestRecord.setArguments(args);
            }
        }, true);
        return requestRecord;
    }

    private static Map<String, String> parseHeader(HttpServletRequest request) {
        HashMap<String, String> map = new HashMap<>();
        Enumeration<String> nameEnumeration = request.getHeaderNames();
        while(nameEnumeration.hasMoreElements()){
            String name = nameEnumeration.nextElement();
            String value = request.getHeader(name);
            map.put(name, value);
        }
        return map;
    }

    private static Object[] filterArguments(Object[] args) {
        Object[] newArgs = Arrays.stream(args).filter(c -> filterArgumentsCondition(c)).collect(Collectors.toList()).toArray();
        return newArgs;
    }

    private static boolean filterArgumentsCondition(Object arg) {
        boolean b = arg != null;
        b = b && !(arg instanceof HttpServletRequest);
        b = b && !(arg instanceof HttpServletResponse);
        b = b && !(arg instanceof BindingResult);
        return b;
    }
}