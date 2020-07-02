package com.aio.portable.swiss.suite.eventbus.listen.registry;

import com.aio.portable.swiss.suite.eventbus.listen.listener.EventListener;
import com.aio.portable.swiss.suite.eventbus.listen.listener.SubscriberEventListener;
import com.aio.portable.swiss.suite.eventbus.subscribe.subscriber.Subscriber;

import java.util.Map;

public interface EventListenerGroup<SUBSCRIBER extends Subscriber> {
    void add(SubscriberEventListener<SUBSCRIBER> listener);

    void remove(String name);

    default void remove(SubscriberEventListener<SUBSCRIBER> listener) {
        remove(listener.getGroup());
    }

    SubscriberEventListener<SUBSCRIBER> get(String name);

    Map<String, SubscriberEventListener<SUBSCRIBER>> collection();
}
