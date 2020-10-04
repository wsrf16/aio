package com.aio.portable.swiss.suite.storage.persistence.redis;

import com.aio.portable.swiss.sugar.CollectionSugar;
import com.aio.portable.swiss.suite.bean.serializer.SerializerConverters;
import com.aio.portable.swiss.suite.io.PathSugar;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class RedisPO implements NodePersistence {
    private final static String INTERVAL = ":";
    private final static String EMPTY = "";

    private String database;

    protected SerializerConverters.JacksonConverter serializerConverter = new SerializerConverters.JacksonConverter();

    public void setDatabase(String database) {
        this.database = database;
    }

    private StringRedisTemplate stringRedisTemplate;

    private static RedisPO instance;

    public final static RedisPO singletonInstance(StringRedisTemplate stringRedisTemplate) {
        return instance = instance == null ? new RedisPO(stringRedisTemplate) : instance;
    }

    public final static RedisPO singletonInstance(StringRedisTemplate stringRedisTemplate, String database) {
        return instance = instance == null ? new RedisPO(stringRedisTemplate, database) : instance;
    }

    public RedisPO(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public RedisPO(StringRedisTemplate stringRedisTemplate, String database) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.database = database;
    }

    private final static String getKeyName(String key) {
        return key.substring(key.lastIndexOf(":") + 1);
    }

    @Override
    public String join(String keyOrTable, String... tables) {
//        PathSugar.concatBy(INTERVAL, prefixNodes);

        List<String> tableList = new ArrayList<>();
        tableList.add(database);
        tableList.addAll(Arrays.asList(tables));
        if (StringUtils.hasLength(keyOrTable))
            tableList.add(keyOrTable);

        String[] join = CollectionSugar.toArray(tableList, String[]::new);
        String path = PathSugar.concatBy(INTERVAL, join);
        return path;


    }

    @Override
    public void set(String key, Object value, String... tables) {
        String path = join(key, tables);
        stringRedisTemplate.opsForValue().set(path, serializerConverter.serialize(value));
    }

    @Override
    public void remove(String key, String... tables) {
        String path = join(key, tables);
        stringRedisTemplate.delete(path);
    }

    @Override
    public void clearTable(String table, String... tables) {
        String path = join(table, tables);
        Set<String> keys = stringRedisTemplate.opsForValue().getOperations().keys(path);
        keys.stream().forEach(c -> {
            String subPath = join(c, path);
            stringRedisTemplate.delete(subPath);
        });
    }

    @Override
    public void removeTable(String table, String... tables) {
        String path = join(table, tables);
        stringRedisTemplate.delete(path);
    }

    @Override
    public void clearDatabase() {

    }

    @Override
    public void removeDatabase() {
        String path = join(EMPTY);
        stringRedisTemplate.delete(path);
    }

    @Override
    public <T> T get(String key, Class<T> clazz, String... tables) {
        String path = join(key, tables);
        String json = stringRedisTemplate.opsForValue().get(path);
        return serializerConverter.deserialize(json, clazz);
    }

    @Override
    public <T> T get(String key, TypeReference<T> valueTypeRef, String... tables) {
        String path = join(key, tables);
        String json = stringRedisTemplate.opsForValue().get(path);
        return serializerConverter.deserialize(json, valueTypeRef);
    }

    @Override
    public List<String> getChildren(String table, String... tables) {
        String path = join(table, tables);
        List<String> keys = stringRedisTemplate.keys(path).stream().map(c -> getKeyName(c)).collect(Collectors.toList());
        return keys;
    }

    private List<String> getChildrenPath(String table, String... tables) {
        String path = join(table, tables);
        Set<String> keys = stringRedisTemplate.keys(path);
        return CollectionSugar.toList(keys.iterator());
    }

    @Override
    public <T> Map<String, T> getAll(String table, Class<T> clazz, String... tables) {
        List<String> children = getChildrenPath(table, tables);
        Map<String, T> collect = children.stream().collect(Collectors.toMap(c -> getKeyName(c), c -> serializerConverter.deserialize(stringRedisTemplate.opsForValue().get(c), clazz)));
        return collect;
    }



    @Override
    public <T> Map<String, T> getAll(String table, TypeReference<T> valueTypeRef, String... tables) {
        List<String> children = getChildrenPath(table, tables);
        Map<String, T> collect = children.stream().collect(Collectors.toMap(c -> getKeyName(c), c -> serializerConverter.deserialize(stringRedisTemplate.opsForValue().get(c), valueTypeRef)));
        return collect;
    }

    @Override
    public boolean exists(String key, String... tables) {
        String path = join(key, tables);
        return stringRedisTemplate.opsForValue().getOperations().hasKey(path);
    }

    @Override
    public boolean existsTable(String table, String... tables) {
        String path = join(table, tables);
        return stringRedisTemplate.opsForValue().getOperations().hasKey(path);
    }

    @Override
    public boolean existsDatabase() {
        String path = join(EMPTY);
        return stringRedisTemplate.opsForValue().getOperations().hasKey(path);
    }

    @Override
    public List<String> keys(String table, String... tables) {
        String path = join(table, tables);
        List<String> keys = CollectionSugar.toList(stringRedisTemplate.opsForValue().getOperations().keys(path).iterator());
        return keys;
    }

    @Override
    public List<String> tables() {
        return keys(EMPTY);
    }

    @Override
    public void setTable(String table, Object value, String... tables) {
        set(table, value, tables);
    }

    @Override
    public <T> T getTable(String table, Class<T> clazz, String... tables) {
        return get(table, clazz, tables);
    }

    @Override
    public <T> T getTable(String table, TypeReference<T> valueTypeRef, String... tables) {
        return get(table, valueTypeRef, tables);
    }

    @Override
    public <T> Map<String, T> getAllTable(String table, Class<T> clazz, String... tables) {
        return getAll(table, clazz, tables);
    }

    @Override
    public <T> Map<String, T> getAllTable(String table, TypeReference<T> valueTypeRef, String... tables) {
        return getAll(table, valueTypeRef, tables);
    }
}
