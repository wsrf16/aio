package com.aio.portable.swiss.suite.eventbus.component.namespace;

import com.aio.portable.swiss.suite.bean.structure.BaseCollection;
import com.aio.portable.swiss.suite.eventbus.component.subscriber.EventSubscriber;

import javax.validation.constraints.NotNull;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "className", visible = true)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = DiskEventListenerNamespace.class, name = "DiskEventListenerNamespace"),
//        @JsonSubTypes.Type(value = HashMapEventListenerNamespace.class, name = "HashMapEventListenerNamespace")})
public abstract class AbstractEventNamespace implements BaseCollection<EventSubscriber> {
    private String namespace;

    private String className;

    private boolean enabled = true;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    public AbstractEventNamespace() {
    }

    public AbstractEventNamespace(@NotNull String namespace) {
        this.namespace = namespace;
    }

    public abstract void remove(EventSubscriber eventListener);

    public abstract boolean exists();
}
