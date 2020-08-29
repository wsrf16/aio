package com.aio.portable.swiss.suite.eventbus.bus;

import com.aio.portable.swiss.suite.bean.structure.BaseCollection;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.aio.portable.swiss.suite.eventbus.component.group.EventGroup;

public abstract class AbstractEventBus implements BaseCollection<EventGroup> {
    public abstract <E extends Event> void send(final E event);
}

