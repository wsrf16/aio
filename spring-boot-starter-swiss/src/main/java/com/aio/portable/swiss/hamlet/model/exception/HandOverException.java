package com.aio.portable.swiss.hamlet.model.exception;

import com.aio.portable.swiss.hamlet.model.RequestRecord;

public class HandOverException extends RuntimeException {
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    @Override
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

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

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    //    private int code;
//    private String message;
    private Exception exception;
    private RequestRecord requestRecord;
    private String traceId;

    public HandOverException(Exception exception, RequestRecord requestRecord, String traceId) {
        this.exception = exception;
        this.requestRecord = requestRecord;
        this.traceId = traceId;
    }

//    public HandOverException(int code, String message){
//        this.code = code;
//        this.message = message;
//    }
}
