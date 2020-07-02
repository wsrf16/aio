package com.aio.portable.swiss.suite.eventbus.subscribe.registry;

import com.aio.portable.swiss.suite.eventbus.event.TopicEvent;
import com.aio.portable.swiss.suite.eventbus.listen.listener.SubscriberEventListener;
import com.aio.portable.swiss.suite.eventbus.subscribe.subscriber.RestTemplateSubscriber;
import com.aio.portable.swiss.suite.eventbus.subscribe.subscriber.Subscriber;
import org.springframework.http.ResponseEntity;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Stream;

public class SubscriberRegistry<SUBSCRIBER extends Subscriber> {
    private String group;
    private boolean concurrent = true;
    protected Map<String, SUBSCRIBER> subscriberMap = new HashMap<>();

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isConcurrent() {
        return concurrent;
    }

    public void setConcurrent(boolean concurrent) {
        this.concurrent = concurrent;
    }

    public Map<String, SUBSCRIBER> getSubscriberMap() {
        return subscriberMap;
    }

    public void setSubscriberMap(Map<String, SUBSCRIBER> subscriberMap) {
        this.subscriberMap = subscriberMap;
    }

    public SubscriberRegistry() {
    }

    public SubscriberRegistry(@NotNull String group) {
        this.group = group;
        this.subscriberMap = new HashMap<>();
    }

    public void add(SUBSCRIBER subscriber) {
        this.subscriberMap.put(subscriber.getName(), subscriber);
    }

    public void remove(SUBSCRIBER subscriber) {
        this.subscriberMap.remove(subscriber.getName());
    }

    public void remove(String name) {
        this.subscriberMap.remove(name);
    }

    public SUBSCRIBER get(String name) {
        return subscriberMap.get(name);
    }

    public void clear() {
        this.subscriberMap.clear();
    }

    public void subscribe(SubscriberEventListener listener) {
        listener.setSubscriberRegistry(this);
    }

    public <E extends TopicEvent> void dispatch(E event) {
        Stream<Map.Entry<String, SUBSCRIBER>> stream = concurrent ?
                this.getSubscriberMap().entrySet().parallelStream()
                : this.getSubscriberMap().entrySet().stream();
        stream.forEach(c -> {
            SUBSCRIBER subscriber = c.getValue();
            if (Objects.equals(subscriber.getTopic(), event.getTopic())) {
                if (subscriber instanceof RestTemplateSubscriber) {
                    Object push = subscriber.push(event);
                } else {
                    Object push = subscriber.push(event);
                }
            }
        });
    }


}
