package com.aio.portable.swiss.suite.eventbus.bus;

import com.aio.portable.swiss.suite.bean.structure.BaseCollection;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.group.EventGroup;
import com.aio.portable.swiss.suite.eventbus.component.subscriber.EventSubscriber;

import java.util.Map;

public abstract class AbstractEventBus implements BaseCollection<EventGroup> {
    public abstract <E extends Event> Map<String, EventSubscriber> send(final E event);
}

