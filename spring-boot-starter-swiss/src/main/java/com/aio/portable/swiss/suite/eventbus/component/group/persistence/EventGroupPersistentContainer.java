package com.aio.portable.swiss.suite.eventbus.component.group.persistence;

import com.aio.portable.swiss.suite.eventbus.refer.EventBusConfig;
import com.aio.portable.swiss.suite.eventbus.refer.persistence.PersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;

import javax.validation.constraints.NotNull;
import java.text.MessageFormat;

public class EventGroupPersistentContainer extends PersistentContainer {
    private final static String TABLE = EventBusConfig.KEY_VALUE_EVENT_GROUP_TABLE;
    private final static String KEY = EventBusConfig.EVENT_GROUP_KEY;

    public EventGroupPersistentContainer(@NotNull NodePersistence nodePersistence) {
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
