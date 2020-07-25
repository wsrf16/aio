package com.aio.portable.swiss.suite.eventbus.listener.persistence;

import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.KeyValuePersistence;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

public class KeyValueEventListenerPersistentContainer extends PersistentContainer {
    private final static String TABLE = EventBusConfig.KEY_VALUE_EVENT_LISTENER_TABLE;
    private final static String KEY = EventBusConfig.EVENT_LISTENER_KEY;

    public KeyValueEventListenerPersistentContainer(@NotNull KeyValuePersistence keyValuePersistence) {
        super(keyValuePersistence);
    }

    @Override
    public String getActualTable(String table) {
        return MessageFormat.format(TABLE, table);
    }

    @Override
    public String getActualKey(String key) {
        return MessageFormat.format(KEY, key);
    }
}
