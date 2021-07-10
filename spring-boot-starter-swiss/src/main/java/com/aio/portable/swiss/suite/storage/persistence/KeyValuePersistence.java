package com.aio.portable.swiss.suite.storage.persistence;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

public interface KeyValuePersistence {
    String EMPTY = "";

    String spellPath(String key, String... parent);

    void set(String key, Object value, String... parent);

    void remove(String key, String... parent);

    default void remove() {
        remove(EMPTY);
    }

    void clear(String key, String... parent);

    default void clear() {
        clear(EMPTY);
    }

    boolean exists(String key, String... parent);

    default boolean exists() {
        return exists(EMPTY);
    }

    List<String> keys(String key, String... parent);

    default List<String> keys(){
        return keys(EMPTY);
    }

    <T> T get(String key, Class<T> clazz, String... parent);

    <T> T get(String key, TypeReference<T> valueTypeRef, String... parent);

    <T> Map<String, T> getChildren(String key, Class<T> clazz, String... parent);

    <T> Map<String, T> getChildren(String key, TypeReference<T> valueTypeRef, String... parent);
}
