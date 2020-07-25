package com.aio.portable.swiss.suite.eventbus.event;

public class Event extends AbstractEvent {
    private String tag;

    public String getTag() {
        return tag;
    }

    public Event(String tag, Object source) {
        super(source);
        this.tag = tag;
    }
}
