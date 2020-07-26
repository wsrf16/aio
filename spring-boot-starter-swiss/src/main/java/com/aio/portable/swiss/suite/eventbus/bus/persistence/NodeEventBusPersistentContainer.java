package com.aio.portable.swiss.suite.eventbus.bus.persistence;

import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.KeyValuePersistence;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

public class NodeEventBusPersistentContainer extends PersistentContainer {
    private final static String TABLE = EventBusConfig.EVENT_BUS_TABLE;
    private final static String KEY = EventBusConfig.EVENT_BUS_KEY;

    public NodeEventBusPersistentContainer(@NotNull KeyValuePersistence keyValuePersistence) {
        super(keyValuePersistence);
    }

    @Override
    public String joinIntoTable(String... items) {
        String table = String.join("/", items);
        return table;
    }

    @Override
    public String getActualTable(String table) {
        return table;
    }

    @Override
    public String getActualKey(String key) {
        return MessageFormat.format(KEY, key);
    }



}
