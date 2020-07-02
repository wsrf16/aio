package com.aio.portable.swiss.suite.eventbus.event;

public class TopicEvent extends Event {
    private String topic;

    public String getTopic() {
        return topic;
    }

    public TopicEvent(String topic, Object source) {
        super(source);
        this.topic = topic;
    }
}
