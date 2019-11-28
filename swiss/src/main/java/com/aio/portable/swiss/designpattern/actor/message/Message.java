package com.aio.portable.swiss.designpattern.actor.message;


import java.util.UUID;

public class Message<T> {
    private String traceId = UUID.randomUUID().toString();

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

    public <R> MessageReturn<R> toReturn(R returnData) {
        MessageReturn<R> messageReturn = new MessageReturn<>(this.traceId, returnData);
        return messageReturn;
    }
}
