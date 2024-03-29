package com.aio.portable.swiss.design.actor.message;

public class ReturnMessage<R> {
    private String spanId;

    private R data;

    public ReturnMessage(String spanId, R data) {
        this.spanId = spanId;
        this.data = data;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public R getData() {
        return data;
    }

    public void setData(R data) {
        this.data = data;
    }
}
