package com.aio.portable.swiss.suite.eventbus.refer.persistence;

import com.aio.portable.swiss.suite.eventbus.bus.persistence.EventBusPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.component.group.persistence.EventGroupPersistentContainer;
import com.aio.portable.swiss.suite.eventbus.component.subscriber.persistence.EventSubscriberPersistentContainer;
import com.aio.portable.swiss.suite.storage.nosql.NodePersistence;
import com.fasterxml.jackson.core.type.TypeReference;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public abstract class PersistentContainer {
    NodePersistence nodePersistence;

    public NodePersistence getNodePersistence() {
        return nodePersistence;
    }

    public PersistentContainer(@NotNull NodePersistence nodePersistence) {
        this.nodePersistence = nodePersistence;
    }

    public String joinTables(String table, String... tables) {
        return nodePersistence.join(table, tables);
    }

    public abstract String getActualTable(String table);

    public abstract String getActualKey(String key);

    public <T> void set(String key, T t, String... tables) {
//        table = joinTables(table, tables);
//        String actualTable = getActualTable(table);
        String actualKey = getActualKey(key);
        nodePersistence.set(actualKey, t, tables);
    }

    public <T> void setTable(String table, T t, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        nodePersistence.setTable(actualTable, t, tables);
    }

    public void removeTable(String table, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        nodePersistence.removeTable(actualTable, tables);
    }

    public void remove(String key, String... tables) {
//        table = joinTables(table, tables);
//        String actualTable = getActualTable(table);
        String actualKey = getActualKey(key);
        nodePersistence.remove(actualKey, tables);
    }

    public <T> T get(String key, TypeReference<T> valueTypeRef, String... tables) {
//        table = joinTables(table, tables);
        String actualKey = getActualKey(key);
        T t = nodePersistence.exists(actualKey, tables) ? nodePersistence.get(actualKey, valueTypeRef, tables) : null;
        return t;
    }

    public <T> T get(String key, Class<T> clazz, String... tables) {
//        table = joinTables(table, tables);
//        String actualTable = getActualTable(table);
        String actualKey = getActualKey(key);
        T t = nodePersistence.exists(actualKey, tables) ? nodePersistence.get(actualKey, clazz, tables) : null;
        return t;
    }

    public <T> T getTable(String table, TypeReference<T> valueTypeRef, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        T t = nodePersistence.existsTable(actualTable, tables) ? nodePersistence.getTable(actualTable, valueTypeRef, tables) : null;
        return t;
    }

    public <T> T getTable(String table, Class<T> clazz, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        T t = nodePersistence.existsTable(actualTable, tables) ? nodePersistence.getTable(actualTable, clazz, tables) : null;
        return t;
    }

    public List<String> getChildren(String table, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        return nodePersistence.getChildren(actualTable, tables);
    }

    public boolean exists(String key, String... tables) {
//        table = joinTables(table, tables);
//        String actualTable = getActualTable(table);
        String actualKey = getActualKey(key);
        return nodePersistence.exists(actualKey, tables);
    }

    public boolean existsTable(String table, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        return nodePersistence.existsTable(actualTable, tables);
    }

    public <T> Map<String, T> getAll(String table, TypeReference<T> valueTypeRef, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        return nodePersistence.existsTable(actualTable, tables) ? nodePersistence.getAll(actualTable, valueTypeRef, tables) : null;
    }

    public <T> Map<String, T> getAll(String table, Class<T> clazz, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        return nodePersistence.existsTable(actualTable, tables) ? nodePersistence.getAll(actualTable, clazz, tables) : null;
    }

    public <T> Map<String, T> getAllTable(String table, TypeReference<T> valueTypeRef, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        return nodePersistence.existsTable(actualTable, tables) ? nodePersistence.getAllTable(actualTable, valueTypeRef, tables) : null;
    }

    public <T> Map<String, T> getAllTable(String table, Class<T> clazz, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        return nodePersistence.existsTable(actualTable, tables) ? nodePersistence.getAllTable(actualTable, clazz, tables) : null;
    }

    public void clearTable(String table, String... tables) {
//        table = joinTables(table, tables);
        String actualTable = getActualTable(table);
        nodePersistence.clearTable(actualTable, tables);
    }

    public final static PersistentContainer buildEventGroupPersistentContainer(NodePersistence nodePersistence) {
        PersistentContainer persistentContainer = new EventGroupPersistentContainer(nodePersistence);
        return persistentContainer;
    }

    public final static PersistentContainer buildEventSubscriberPersistentContainer(NodePersistence nodePersistence) {
        PersistentContainer persistentContainer = new EventSubscriberPersistentContainer(nodePersistence);
        return persistentContainer;
    }

    public final static PersistentContainer buildEventBusPersistentContainer(NodePersistence nodePersistence) {
        PersistentContainer persistentContainer = new EventBusPersistentContainer(nodePersistence);
        return persistentContainer;
    }
}

