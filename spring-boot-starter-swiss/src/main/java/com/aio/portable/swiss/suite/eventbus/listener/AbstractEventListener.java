package com.aio.portable.swiss.suite.eventbus.listener;

import com.aio.portable.swiss.suite.bean.structure.BaseCollection;
import com.aio.portable.swiss.suite.eventbus.event.Event;
import com.aio.portable.swiss.suite.eventbus.subscriber.Subscriber;

import javax.validation.constraints.NotNull;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "className", visible = true)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = DiskEventListener.class, name = "DiskEventListener"),
//        @JsonSubTypes.Type(value = HashMapEventListener.class, name = "HashMapEventListener")})
public abstract class AbstractEventListener implements BaseCollection<Subscriber> {
    private String listener;

    private String group;

    private String className;

    private boolean enabled = true;

    public String getListener() {
        return listener;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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

    public AbstractEventListener() {
    }

    public AbstractEventListener(@NotNull String group, @NotNull String listener) {
        setGroup(group);
        setListener(listener);
    }

    public abstract void remove(Subscriber subscriber);

    public abstract boolean exists();

    public abstract <E extends Event> void onReceiveEvent(E event);
}
