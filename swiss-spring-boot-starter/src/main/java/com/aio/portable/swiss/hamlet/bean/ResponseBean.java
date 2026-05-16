package com.aio.portable.swiss.hamlet.bean;

import java.util.Date;

public interface ResponseBean<T> {
    String SPAN_ID_HEADER = "Span-Id";
    String TRACE_ID_HEADER = "Trace-Id";

    String getSpanId();
    String getTraceId();
    int getCode();
    String getMessage();
    T getData();
    Date getAccessTime();
    Long getTimestamp();

    ResponseBean<T> setSpanId(String spanId);
    ResponseBean<T> setTraceId(String traceId);
    ResponseBean<T> setCode(int code);
    ResponseBean<T> setMessage(String message);
    ResponseBean<T> setData(T data);
    ResponseBean<T> setAccessTime(Date accessTime);
    ResponseBean<T> setTimestamp(Long timestamp);
}
