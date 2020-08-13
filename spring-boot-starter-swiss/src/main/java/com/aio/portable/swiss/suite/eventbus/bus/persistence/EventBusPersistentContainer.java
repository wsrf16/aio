package com.aio.portable.swiss.suite.eventbus.bus.persistence;

import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

public class EventBusPersistentContainer extends PersistentContainer {
    private final static String TABLE = EventBusConfig.EVENT_BUS_TABLE;
    private final static String KEY = EventBusConfig.EVENT_BUS_KEY;

    public EventBusPersistentContainer(@NotNull NodePersistence nodePersistence) {
        super(nodePersistence);
    }

//    @Override
//    public String joinTables(String... tables) {
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
