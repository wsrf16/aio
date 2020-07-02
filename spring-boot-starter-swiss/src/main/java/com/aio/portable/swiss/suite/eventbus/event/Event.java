package com.aio.portable.swiss.suite.eventbus.event;

public class Event {
    private Object source;

    public Object getSource() {
        return source;
    }

    public Event(Object source) {
        this.source = source;
    }
}
