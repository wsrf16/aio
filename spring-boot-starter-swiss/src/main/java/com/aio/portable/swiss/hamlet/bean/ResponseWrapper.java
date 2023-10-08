package com.aio.portable.swiss.hamlet.bean;

import java.util.Date;

public interface ResponseWrapper<T> {
    String SPAN_ID_HEADER = "span-id";

    String getSpanId();
    int getCode();
    String getMessage();
    T getData();
    Date getAccessTime();
    Long getTimestamp();

    ResponseWrapper<T> setSpanId(String spanId);
    ResponseWrapper<T> setCode(int code);
    ResponseWrapper<T> setMessage(String message);
    ResponseWrapper<T> setData(T data);
    ResponseWrapper<T> setAccessTime(Date accessTime);
    ResponseWrapper<T> setTimestamp(Long timestamp);
}
