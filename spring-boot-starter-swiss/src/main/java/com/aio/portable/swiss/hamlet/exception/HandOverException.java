package com.aio.portable.swiss.hamlet.exception;

import com.aio.portable.swiss.hamlet.bean.RequestRecord;

public class HandOverException extends RuntimeException {
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public RequestRecord getRequestRecord() {
        return requestRecord;
    }

    public void setRequestRecord(RequestRecord requestRecord) {
        this.requestRecord = requestRecord;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }


    private Exception exception;
    private RequestRecord requestRecord;
    private String spanId;

    public HandOverException(Exception exception, RequestRecord requestRecord, String spanId) {
        this.exception = exception;
        this.requestRecord = requestRecord;
        this.spanId = spanId;
    }


}
