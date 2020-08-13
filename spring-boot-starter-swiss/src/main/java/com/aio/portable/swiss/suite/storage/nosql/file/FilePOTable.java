package com.aio.portable.swiss.suite.storage.nosql.file;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

//public class FilePOTable {
//    private FilePO jsonCache;
//    private String table;
//
//    public FilePOTable(FilePO jsonCache, String table) {
//        this.jsonCache = jsonCache;
//        this.table = table;
//    }
//
//    public <T> void set(String key, T subscriber) {
//        jsonCache.set(table, key, subscriber);
//    }
//
//    public void remove(String key) {
//        jsonCache.remove(table, key);
//    }
//
//    public <T> T get(String key, Class<T> clazz) {
//        return jsonCache.get(table, key, clazz);
//    }
//
//    public <T> T get(String key, TypeReference<T> valueTypeRef) {
//        return jsonCache.get(table, key, valueTypeRef);
//    }
//
//    public <T> T getNode(String key, Class<T> clazz) {
//        return jsonCache.get(table, key, clazz);
//    }
//
//
//    public <T> Map<String, T> all(String key, Class<T> clazz) {
//        return jsonCache.getAll(table, key, clazz);
//    }
//
//    public <T> Map<String, T> all(String key, TypeReference<T> valueTypeRef) {
//        return jsonCache.getAll(table, key, valueTypeRef);
//    }
//
//    public boolean exists(String key) {
//        return jsonCache.exists(table, key);
//    }
//
//    public List<String> keys(String key) {
//        return jsonCache.keys(table);
//    }
//
//    public <T> Map<String, T> list(TypeReference<T> valueTypeRef) {
//        return jsonCache.getAll(table, valueTypeRef);
//    }
//
//    public void clear() {
//        jsonCache.clearTable(table);
//    }
//}
//
