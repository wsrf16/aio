package com.aio.portable.swiss.suite.storage.persistence.redis;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.location.PathSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.adapter.JacksonSerializerAdapterImpl;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class RedisPO implements NodePersistence {
    private final static String DELIMITER = ":";
    private final static String EMPTY = "";

    private String database;

    protected JacksonSerializerAdapterImpl serializerAdapter = new JacksonSerializerAdapterImpl();

    public void setDatabase(String database) {
        this.database = database;
    }

    private StringRedisTemplate stringRedisTemplate;

    private static RedisPO instance;


    public final static RedisPO singletonInstance(StringRedisTemplate stringRedisTemplate, String database) {
        return instance = instance == null ? new RedisPO(stringRedisTemplate, database) : instance;
    }


    public RedisPO(StringRedisTemplate stringRedisTemplate, String database) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.database = database;
        set(EMPTY, new Object());
    }

    private final static String getKeyName(String key) {
        return key.substring(key.lastIndexOf(":") + 1);
    }

    @Override
    public String spellPath(String key, String... parent) {
        List<String> tableList = new ArrayList<>();
        tableList.add(database);
        tableList.addAll(Arrays.asList(parent));
        if (StringUtils.hasLength(key))
            tableList.add(key);

        String[] join = CollectionSugar.toArray(tableList, String[]::new);
        String path = PathSugar.concatBy(DELIMITER, join);
        return path;


    }

    @Override
    public void set(String key, Object value, String... parent) {
        String path = spellPath(key, parent);
        stringRedisTemplate.opsForValue().set(path, serializerAdapter.serialize(value));
    }

    @Override
    public void remove(String key, String... parent) {
        String path = spellPath(key, parent);
        stringRedisTemplate.delete(path);
    }

    @Override
    public void clear(String key, String... parent) {
        String path = spellPath(key, parent);
        Set<String> keys = stringRedisTemplate.opsForValue().getOperations().keys(path + ":*");
        keys.stream().forEach(c -> {
            stringRedisTemplate.delete(c);
        });
    }

    @Override
    public boolean exists(String key, String... parent) {
        String path = spellPath(key, parent);
        return stringRedisTemplate.opsForValue().getOperations().hasKey(path);
    }

    @Override
    public List<String> keys(String key, String... parent) {
        String path = spellPath(key, parent);
        List<String> subKeys = CollectionSugar.toList(stringRedisTemplate.opsForValue().getOperations().keys(path).iterator());
//        List<String> subKeys = stringRedisTemplate.keys(path).stream().map(c -> getKeyName(c)).collect(Collectors.toList());
        return subKeys;
    }

    @Override
    public <T> T get(String key, Class<T> clazz, String... parent) {
        String path = spellPath(key, parent);
        String json = stringRedisTemplate.opsForValue().get(path);
        return serializerAdapter.deserialize(json, clazz);
    }

    @Override
    public <T> T get(String key, TypeReference<T> valueTypeRef, String... parent) {
        String path = spellPath(key, parent);
        String json = stringRedisTemplate.opsForValue().get(path);
        return serializerAdapter.deserialize(json, valueTypeRef);
    }


    private List<String> getSubKeyPaths(String key, String... parent) {
        String path = spellPath(key, parent);
        Set<String> subKeys = stringRedisTemplate.keys(path);
        return CollectionSugar.toList(subKeys.iterator());
    }

    @Override
    public <T> Map<String, T> getChildren(String key, Class<T> clazz, String... parent) {
        List<String> children = getSubKeyPaths(key, parent);
        Map<String, T> collect = children.stream().collect(Collectors.toMap(c -> getKeyName(c), c -> serializerAdapter.deserialize(stringRedisTemplate.opsForValue().get(c), clazz)));
        return collect;
    }

    @Override
    public <T> Map<String, T> getChildren(String key, TypeReference<T> valueTypeRef, String... parent) {
        List<String> children = getSubKeyPaths(key, parent);
        Map<String, T> collect = children.stream().collect(Collectors.toMap(c -> getKeyName(c), c -> serializerAdapter.deserialize(stringRedisTemplate.opsForValue().get(c), valueTypeRef)));
        return collect;
    }


}
