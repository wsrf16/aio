package com.aio.portable.swiss.suite.storage.persistence;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

public interface KeyValuePersistence {
//    String join(String node, String... nodes);
//    String join(@NotNull String... nodes);

    String join(String node, String... prefixNodes);

    void set(String key, Object value, String... tables);

    void remove(String key, String... tables);

    void clearTable(String table, String... tables);

    void removeTable(String table, String... tables);

    void clearDatabase();

    void removeDatabase();

    <T> T get(String key, Class<T> clazz, String... tables);

    <T> T get(String key, TypeReference<T> valueTypeRef, String... tables);

    List<String> getChildren(String table, String... tables);

    <T> Map<String, T> getAll(String table, Class<T> clazz, String... tables);

    <T> Map<String, T> getAll(String table, TypeReference<T> valueTypeRef, String... tables);

    boolean exists(String key, String... tables);

    boolean existsTable(String table, String... tables);

    boolean existsDatabase();

    List<String> keys(String table, String... tables);

    List<String> tables();
}
