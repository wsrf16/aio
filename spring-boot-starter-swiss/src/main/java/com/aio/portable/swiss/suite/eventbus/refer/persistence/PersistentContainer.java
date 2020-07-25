package com.aio.portable.swiss.suite.eventbus.refer.persistence;

import com.aio.portable.swiss.suite.eventbus.group.persistence.KeyValueEventGroupPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.group.persistence.NodeEventGroupPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.listener.persistence.KeyValueEventListenerPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.listener.persistence.NodeEventListenerPersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.KeyValuePersistence;
import com.aio.portable.swiss.suite.storage.nosql.zookeeper.ZooKeeperPO;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public abstract class PersistentContainer {
    //    public static String DATABASE_NAME = "event-bus" ;
    KeyValuePersistence keyValuePersistence;

    public KeyValuePersistence getKeyValuePersistence() {
        return keyValuePersistence;
    }

    public PersistentContainer(@NotNull KeyValuePersistence keyValuePersistence) {
        this.keyValuePersistence = keyValuePersistence;
    }

    public abstract String getActualTable(String table);

    public abstract String getActualKey(String key);

    public <T> void set(String table, String key, T t) {
        String actualTable = getActualTable(table);
        String actualKey = getActualKey(key);
        keyValuePersistence.set(actualTable, actualKey, t);
    }

    public void remove(String table, String key) {
        String actualTable = getActualTable(table);
        String actualKey = getActualKey(key);
        keyValuePersistence.remove(actualTable, actualKey);
    }

    public <T> T get(String table, String key, TypeReference<T> valueTypeRef) {
        String actualKey = getActualKey(key);
        String actualTable = getActualTable(table);
        T t = keyValuePersistence.existsTable(actualTable) ? keyValuePersistence.get(actualTable, actualKey, valueTypeRef) : null;
        return t;
    }

    public <T> T get(String table, String key, Class<T> clazz) {
        String actualTable = getActualTable(table);
        String actualKey = getActualKey(key);
        T t = keyValuePersistence.existsTable(actualTable) ? keyValuePersistence.get(actualTable, actualKey, clazz) : null;
        return t;
    }

    public List<String> getChildren(String table) {
        String actualTable = getActualTable(table);
        return keyValuePersistence.getChildren(actualTable);
    }

    public boolean exists(String table, String key) {
        String actualTable = getActualTable(table);
        String actualKey = getActualKey(key);
        return keyValuePersistence.exists(actualTable, actualKey);
    }

    public boolean existsTable(String table) {
        String actualTable = getActualTable(table);
        return keyValuePersistence.existsTable(actualTable);
    }

    public <T> Map<String, T> getAll(String table, TypeReference<T> valueTypeRef) {
        String actualTable = getActualTable(table);
        return keyValuePersistence.existsTable(actualTable) ? keyValuePersistence.getAll(actualTable, valueTypeRef) : null;
    }

    public <T> Map<String, T> getAll(String table, Class<T> clazz) {
        String actualTable = getActualTable(table);
        return keyValuePersistence.existsTable(actualTable) ? keyValuePersistence.getAll(actualTable, clazz) : null;
    }

    public void clear(String table) {
        String actualTable = getActualTable(table);
        keyValuePersistence.clearTable(actualTable);
    }

    public final static PersistentContainer buildEventGroupPersistentContainer(KeyValuePersistence keyValuePersistence) {
        PersistentContainer persistentContainer;
        if (keyValuePersistence instanceof ZooKeeperPO) {
            persistentContainer = new NodeEventGroupPersistentContainer(keyValuePersistence);
        } else {
            persistentContainer = new KeyValueEventGroupPersistentContainer(keyValuePersistence);
        }
        return persistentContainer;
    }

    public final static PersistentContainer buildEventListenerPersistentContainer(KeyValuePersistence keyValuePersistence) {
        PersistentContainer persistentContainer;
        if (keyValuePersistence instanceof ZooKeeperPO) {
            persistentContainer = new NodeEventListenerPersistentContainer(keyValuePersistence);
        } else {
            persistentContainer = new KeyValueEventListenerPersistentContainer(keyValuePersistence);
        }
        return persistentContainer;
    }
}

