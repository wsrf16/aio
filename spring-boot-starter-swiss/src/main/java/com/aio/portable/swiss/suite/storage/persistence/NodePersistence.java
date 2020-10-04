package com.aio.portable.swiss.suite.storage.persistence;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public interface NodePersistence extends KeyValuePersistence {
//    void setTable(String table, String... tables);

    void setTable(String table, Object value, String... tables);

    <T> T getTable(String table, Class<T> clazz, String... tables);

    <T> T getTable(String table, TypeReference<T> valueTypeRef, String... tables);

    <T> Map<String, T> getAllTable(String table, Class<T> clazz, String... tables);

    <T> Map<String, T> getAllTable(String table, TypeReference<T> valueTypeRef, String... tables);
}
