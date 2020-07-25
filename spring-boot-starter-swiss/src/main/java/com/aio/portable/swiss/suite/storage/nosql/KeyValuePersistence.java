package com.aio.portable.swiss.suite.storage.nosql;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

public interface KeyValuePersistence {
    void set(String table, String key, Object value);

    void createTable(String table);

    void remove(String table, String key);

    void clearTable(String table);

    void removeTable(String table);

    void clearDatabase();

    void removeDatabase();

    <T> T get(String table, String key, Class<T> clazz);

    <T> T get(String table, String key, TypeReference<T> valueTypeRef);

    List<String> getChildren(String table);

    <T> Map<String, T> getAll(String table, Class<T> clazz);

    <T> Map<String, T> getAll(String table, TypeReference<T> valueTypeRef);

    boolean exists(String table, String key);

    boolean existsTable(String table);

    boolean existsDatabase();

    List<String> keys(String table);

    List<String> tables();
}
