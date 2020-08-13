package com.aio.portable.swiss.suite.eventbus.event;

public abstract class EventObject {
    private Object source;

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public EventObject() {
    }

    public EventObject(Object source) {
        this.source = source;
    }
}
