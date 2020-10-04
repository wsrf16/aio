package com.aio.portable.swiss.suite.eventbus.component.subscriber.persistence;

import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

public class EventSubscriberPersistentContainer extends PersistentContainer {
    private final static String TABLE = EventBusConfig.KEY_VALUE_EVENT_SUBSCRIBER_TABLE;
    private final static String KEY = EventBusConfig.EVENT_SUBSCRIBER_KEY;

    public EventSubscriberPersistentContainer(@NotNull NodePersistence nodePersistence) {
        super(nodePersistence);
    }

//    @Override
//    public String joinTables(String... tables) {
////        String table = MessageFormat.format("{0}/{1}/{2}", EventBusConfig.EVENT_BUS_TABLE, getNamespace(), getListener());
//        String table = String.join("/", tables);
//        return table;
//    }

    @Override
    public String getActualTable(String table) {
        return table;

    }

    @Override
    public String getActualKey(String key) {
        return MessageFormat.format(KEY, key);
    }
}
