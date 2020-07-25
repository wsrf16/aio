package com.aio.portable.swiss.suite.eventbus.event;

public class AbstractEvent {
    private Object source;

    public Object getSource() {
        return source;
    }

    public AbstractEvent(Object source) {
        this.source = source;
    }
}
