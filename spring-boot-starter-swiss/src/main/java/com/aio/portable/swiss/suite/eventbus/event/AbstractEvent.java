package com.aio.portable.swiss.suite.eventbus.event;

public class AbstractEvent {
    private Object source;

    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public AbstractEvent() {
    }

    public AbstractEvent(Object source) {
        this.source = source;
    }
}
