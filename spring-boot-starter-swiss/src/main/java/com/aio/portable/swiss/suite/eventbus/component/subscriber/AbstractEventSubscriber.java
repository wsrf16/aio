package com.aio.portable.swiss.suite.eventbus.component.subscriber;

import com.aio.portable.swiss.suite.bean.structure.BaseCollection;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.handler.EventHandler;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "className", visible = true)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = DiskEventListener.class, name = "DiskEventListener"),
//        @JsonSubTypes.Type(value = HashMapEventListener.class, name = "HashMapEventListener")})
public abstract class AbstractEventSubscriber implements BaseCollection<EventHandler> {
    private String subscriber;

    private String group;

    private List<String> tags = new ArrayList<>();

    private String className;

    private boolean enabled = true;

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public AbstractEventSubscriber(@NotNull String group, @NotNull String subscriber) {
        setGroup(group);
        setSubscriber(subscriber);
    }

    public abstract void remove(EventHandler handler);

    public abstract boolean exists();

    public abstract <E extends Event> Map<String, EventHandler> onReceive(E event);
}
