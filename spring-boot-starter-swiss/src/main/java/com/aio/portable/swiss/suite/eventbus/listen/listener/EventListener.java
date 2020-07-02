package com.aio.portable.swiss.suite.eventbus.listen.listener;

import com.aio.portable.swiss.suite.eventbus.event.TopicEvent;

import javax.validation.constraints.NotNull;

public abstract class EventListener {
    private String group;

    public EventListener() {
    }

    public EventListener(@NotNull String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public abstract <E extends TopicEvent> void onEvent(E event);
}
