package com.aio.portable.swiss.designpattern.actor.message;

public class MessageReturn<R> {
    private String traceId;

    private R data;

    public MessageReturn(String traceId, R data) {
        this.traceId = traceId;
        this.data = data;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
    }
}
