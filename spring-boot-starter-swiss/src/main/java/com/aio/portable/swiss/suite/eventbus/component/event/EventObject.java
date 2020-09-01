package com.aio.portable.swiss.suite.eventbus.component.event;

public abstract class EventObject {
    private Object payload;

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public EventObject() {
    }

    public EventObject(Object payload) {
        this.payload = payload;
    }
}
