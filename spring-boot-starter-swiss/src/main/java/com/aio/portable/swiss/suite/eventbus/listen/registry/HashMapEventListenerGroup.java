package com.aio.portable.swiss.suite.eventbus.listen.registry;

import com.aio.portable.swiss.suite.eventbus.listen.listener.SubscriberEventListener;
import com.aio.portable.swiss.suite.eventbus.subscribe.subscriber.Subscriber;

import java.util.HashMap;
import java.util.Map;

public class HashMapEventListenerGroup<SUBSCRIBER extends Subscriber> implements EventListenerGroup<SUBSCRIBER> {

    private Map<String, SubscriberEventListener<SUBSCRIBER>> listenerMap;

    public Map<String, SubscriberEventListener<SUBSCRIBER>> getListenerMap() {
        return listenerMap;
    }

    public void setListenerMap(Map<String, SubscriberEventListener<SUBSCRIBER>> listenerMap) {
        this.listenerMap = listenerMap;
    }

    public HashMapEventListenerGroup() {
        this.listenerMap = new HashMap<>();
    }

    @Override
    public void add(SubscriberEventListener<SUBSCRIBER> listener) {
        String group = new String(listener.getGroup());
        listenerMap.put(group, listener);
    }

    @Override
    public void remove(String name) {
        listenerMap.remove(name);
    }

    @Override
    public SubscriberEventListener<SUBSCRIBER> get(String group) {
        SubscriberEventListener<SUBSCRIBER> listener = listenerMap.get(group);
        return listener;
    }

    @Override
    public Map<String, SubscriberEventListener<SUBSCRIBER>> collection() {
        return listenerMap;
    }

}
