package com.aio.portable.swiss.hamlet.bean;

import com.aio.portable.swiss.global.Constant;
import org.aspectj.lang.JoinPoint;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestRecord {
    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
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

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    private String requestURL;
    private String httpMethod;
    private String remoteAddress;
    private String classMethod;
    private Object[] arguments;
    private Map<String, String> headers;



    public static RequestRecord newInstance(HttpServletRequest request, JoinPoint joinPoint) {
        RequestRecord requestRecord = new RequestRecord();
        if (request != null) {
            requestRecord.setRemoteAddress(request.getRemoteAddr());
            String url = request.getRequestURL().toString() + (!StringUtils.hasText(request.getQueryString()) ? Constant.EMPTY : "?" + request.getQueryString());
            requestRecord.setRequestURL(url);
            requestRecord.setHttpMethod(request.getMethod());
            requestRecord.setHeaders(parseHeader(request));
        }
        requestRecord.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        Object[] args = filterArguments(joinPoint.getArgs());
        requestRecord.setArguments(args);
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