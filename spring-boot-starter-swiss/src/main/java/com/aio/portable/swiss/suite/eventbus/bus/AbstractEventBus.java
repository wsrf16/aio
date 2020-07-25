package com.aio.portable.swiss.suite.eventbus.bus;

import com.aio.portable.swiss.suite.bean.structure.BaseCollection;
import com.aio.portable.swiss.suite.eventbus.event.Event;
import com.aio.portable.swiss.suite.eventbus.group.EventGroup;

public abstract class AbstractEventBus implements BaseCollection<EventGroup> {
//    public abstract void add(String group);

//    public abstract void addGroupAndListener(EventListener listener);

//    public abstract void removeListener(String group, String listenerName);

//    public abstract boolean containListener(String group, String listenerName);

//    public abstract EventListener getListen(String group, String listenerName);

//    public abstract Map<String, EventListener> listListeners(String group);

    public abstract <E extends Event> void send(final E event);
}

