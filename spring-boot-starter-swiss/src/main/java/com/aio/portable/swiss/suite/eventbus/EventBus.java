package com.aio.portable.swiss.suite.eventbus;


import com.aio.portable.swiss.suite.eventbus.event.TopicEvent;
import com.aio.portable.swiss.suite.eventbus.listen.listener.EventListener;
import com.aio.portable.swiss.suite.eventbus.listen.listener.SubscriberEventListener;
import com.aio.portable.swiss.suite.eventbus.listen.registry.EventListenerGroup;
import com.aio.portable.swiss.suite.eventbus.listen.registry.HashMapEventListenerGroup;
import com.aio.portable.swiss.suite.eventbus.subscribe.subscriber.Subscriber;
//import com.aio.portable.swiss.suite.eventbus.subscribe.registry.RestTemplateSubscriberRegistry;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class EventBus {
    static EventBus instance;

    EventListenerGroup<Subscriber> eventListenerGroup;

    public EventListenerGroup<Subscriber> getEventListenerGroup() {
        return eventListenerGroup;
    }

    public void setEventListenerGroup(EventListenerGroup<Subscriber> eventListenerGroup) {
        this.eventListenerGroup = eventListenerGroup;
    }

    public static <T extends EventListenerGroup<Subscriber>> EventBus build(Class<T> clazz) {
        try {
            T t = clazz.getConstructor().newInstance();
            return new EventBus(t);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static EventBus build() {
        return EventBus.build(HashMapEventListenerGroup.class);
    }

    public static EventBus singletonInstance() {
        return singletonInstance(HashMapEventListenerGroup.class);
    }

    public synchronized static <T extends EventListenerGroup<Subscriber>> EventBus singletonInstance(Class<T> clazz) {
        instance = instance == null ? EventBus.build(clazz) : instance;
        return instance;
    }

    private EventBus(EventListenerGroup<Subscriber> eventListenerGroup) {
        this.eventListenerGroup = eventListenerGroup;
    }

    public void register(SubscriberEventListener<Subscriber> listener) {
        eventListenerGroup.add(listener);
    }

    public void unregister(SubscriberEventListener<Subscriber> listener) {
        eventListenerGroup.remove(listener.getGroup());
    }

    public void unregister(String group) {
        eventListenerGroup.remove(group);
    }

    public boolean isRegister(SubscriberEventListener<Subscriber> listener) {
        return eventListenerGroup.get(listener.getGroup()) != null;
    }

    public boolean isRegister(String group) {
        return eventListenerGroup.get(group) != null;
    }

    public EventListener getListen(String group) {
        return eventListenerGroup.get(group);
    }

    public Map<String, SubscriberEventListener<Subscriber>> listListeners() {
        return eventListenerGroup.collection();
    }


//    public void publish(final Event event) {
//        listenerRegistry.collection().values().forEach(listener -> {
//            publish(event, listener);
//        });
//    }

    public <E extends TopicEvent> void publish(final E event) {
        eventListenerGroup.collection().values().stream().parallel().forEach(listener -> {
            publish(listener, event);
        });
    }

    private final static <E extends TopicEvent> void publish(EventListener listener, E event) {
        listener.onEvent(event);
    }
}
