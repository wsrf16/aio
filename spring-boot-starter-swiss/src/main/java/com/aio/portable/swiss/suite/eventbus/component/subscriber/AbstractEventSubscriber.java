package com.aio.portable.swiss.suite.eventbus.component.subscriber;

import com.aio.portable.swiss.suite.bean.structure.BaseCollection;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.action.EventAction;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "className", visible = true)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = DiskEventListener.class, name = "DiskEventListener"),
//        @JsonSubTypes.Type(value = HashMapEventListener.class, name = "HashMapEventListener")})
public abstract class AbstractEventSubscriber implements BaseCollection<EventAction> {
    private String subscriber;

    private String namespace;

    private List<String> topics = new ArrayList<>();

    private String className;

    private boolean enabled = true;

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    public String getClassName() {
        return className == null ? this.getClass().getSimpleName() : className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public AbstractEventSubscriber() {
    }

    public AbstractEventSubscriber(@NotNull String namespace, @NotNull List<String> topics, @NotNull String subscriber) {
        setNamespace(namespace);
        setTopics(topics);
        setSubscriber(subscriber);
    }

    public abstract void remove(EventAction action);

    public abstract boolean exists();

    public abstract <E extends Event> Map<String, EventAction> onReceive(E event);
}
