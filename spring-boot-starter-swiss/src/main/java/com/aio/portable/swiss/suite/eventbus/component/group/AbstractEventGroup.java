package com.aio.portable.swiss.suite.eventbus.component.group;

import com.aio.portable.swiss.suite.bean.structure.BaseCollection;
import com.aio.portable.swiss.suite.eventbus.component.subscriber.EventSubscriber;

import javax.validation.constraints.NotNull;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "className", visible = true)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = DiskEventListenerGroup.class, name = "DiskEventListenerGroup"),
//        @JsonSubTypes.Type(value = HashMapEventListenerGroup.class, name = "HashMapEventListenerGroup")})
public abstract class AbstractEventGroup implements BaseCollection<EventSubscriber> {
    private String group;

    private String className;

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

    public AbstractEventGroup() {
    }

    public AbstractEventGroup(@NotNull String group) {
        this.group = group;
    }

    public abstract void remove(EventSubscriber eventListener);

    public abstract boolean exists();
}
