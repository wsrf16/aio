package com.aio.portable.swiss.suite.eventbus.listen.listener;

import com.aio.portable.swiss.suite.eventbus.event.TopicEvent;
import com.aio.portable.swiss.suite.eventbus.subscribe.registry.SubscriberRegistry;
import com.aio.portable.swiss.suite.eventbus.subscribe.subscriber.Subscriber;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class SubscriberEventListener<SUBSCRIBER extends Subscriber> extends EventListener {
    protected SubscriberRegistry<SUBSCRIBER> subscriberRegistry = new SubscriberRegistry<>();

    public SubscriberRegistry<SUBSCRIBER> getSubscriberRegistry() {
        return subscriberRegistry;
    }

    public void setSubscriberRegistry(SubscriberRegistry<SUBSCRIBER> subscriberRegistry) {
        if (StringUtils.isEmpty(subscriberRegistry.getGroup()))
            throw new IllegalArgumentException("SubscriberRegistry name is null.");
        this.subscriberRegistry = subscriberRegistry;
    }

    public void add(SUBSCRIBER subscriber) {
        if (subscriber == null)
            throw new NullPointerException("subscriber is null.");
        if (StringUtils.isEmpty(subscriber.getName()))
            throw new IllegalArgumentException("subscriber name is null.");
        if (this.subscriberRegistry == null)
            throw new NullPointerException("subscriberRegistry is null.");
        this.subscriberRegistry.add(subscriber);
    }

    public void remove(SUBSCRIBER subscriber) {
        if (subscriber == null)
            throw new NullPointerException("subscriber is null.");
        if (StringUtils.isEmpty(subscriber.getName()))
            throw new IllegalArgumentException("subscriber name is null.");
        if (this.subscriberRegistry == null)
            throw new NullPointerException("subscriberRegistry is null.");
        this.subscriberRegistry.remove(subscriber.getName());
    }

    public void remove(String subscriber) {
        if (this.subscriberRegistry == null)
            throw new NullPointerException("subscriberRegistry is null.");
        this.subscriberRegistry.remove(subscriber);
    }

    public SUBSCRIBER get(String subscriber) {
        if (this.subscriberRegistry == null)
            throw new NullPointerException("subscriberRegistry is null.");
        return this.subscriberRegistry.get(subscriber);
    }

    public Map<String, SUBSCRIBER> collection() {
        if (this.subscriberRegistry == null)
            throw new NullPointerException("subscriberRegistry is null.");
        return this.subscriberRegistry.getSubscriberMap();
    }

    public SubscriberEventListener() {
        super();
    }

    public SubscriberEventListener(@NotNull String group) {
        super(group);
        this.setGroup(group);
    }

    @Override
    public void setGroup(String group) {
        super.setGroup(group);
        subscriberRegistry.setGroup(group);
    }


    @Override
    public <E extends TopicEvent> void onEvent(E event) {
        subscriberRegistry.dispatch(event);
    }
}
