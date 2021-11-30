package com.aio.portable.swiss.design.actor.message;


import com.aio.portable.swiss.suite.algorithm.identity.IDS;

public class Message<T> {
    private String traceId = IDS.uuid();

    private T data;

    public String getTraceId() {
        return traceId;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Message(T data) {
        this.data = data;
    }

    public <R> ReturnMessage<R> buildReturnMessage(R returnData) {
        ReturnMessage<R> returnMessage = new ReturnMessage<>(this.traceId, returnData);
        return returnMessage;
    }
}
