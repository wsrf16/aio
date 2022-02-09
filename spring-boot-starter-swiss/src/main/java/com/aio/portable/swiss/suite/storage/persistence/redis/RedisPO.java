package com.aio.portable.swiss.suite.storage.persistence.redis;

import com.aio.portable.swiss.sugar.type.CollectionSugar;
import com.aio.portable.swiss.sugar.location.PathSugar;
import com.aio.portable.swiss.suite.storage.persistence.NodePersistence;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class RedisPO implements NodePersistence {
    private static final String DELIMITER = ":";
    private static final String EMPTY = "";

    private String database;

//    protected JacksonSerializerAdapterImpl serializerAdapter = SerializerAdapterBuilder.buildJackson();

    public void setDatabase(String database) {
        this.database = database;
    }

    private RedisTemplate redisTemplate;

    private static RedisPO instance;


    public static final RedisPO singletonInstance(RedisTemplate redisTemplate, String database) {
        return instance = instance == null ? new RedisPO(redisTemplate, database) : instance;
    }


    public RedisPO(RedisTemplate redisTemplate, String database) {
        this.redisTemplate = redisTemplate;
        this.database = database;
    }

    private static final String getKeyName(String key) {
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
        redisTemplate.opsForValue().set(path, value);
    }

    @Override
    public void remove(String key, String... parent) {
        String path = spellPath(key, parent);
        redisTemplate.delete(path);
    }

    @Override
    public void clear(String key, String... parent) {
        String path = spellPath(key, parent);
        Set<String> keys = redisTemplate.opsForValue().getOperations().keys(path + ":*");
        keys.stream().forEach(c -> {
            redisTemplate.delete(c);
        });
    }

    @Override
    public boolean exists(String key, String... parent) {
        String path = spellPath(key, parent);
        return redisTemplate.opsForValue().getOperations().hasKey(path);
    }

    @Override
    public List<String> keys(String key, String... parent) {
        String path = spellPath(key, parent);
        List<String> subKeys = CollectionSugar.toList(redisTemplate.opsForValue().getOperations().keys(path).iterator());
//        List<String> subKeys = stringRedisTemplate.keys(path).stream().map(c -> getKeyName(c)).collect(Collectors.toList());
        return subKeys;
    }

    @Override
    public <T> T get(String key, Class<T> clazz, String... parent) {
        String path = spellPath(key, parent);
        return (T) redisTemplate.opsForValue().get(path);
    }

    @Override
    public <T> T get(String key, TypeReference<T> valueTypeRef, String... parent) {
        String path = spellPath(key, parent);
        return (T) redisTemplate.opsForValue().get(path);
    }


    private List<String> getSubKeyPaths(String key, String... parent) {
        String path = spellPath(key, parent);
        Set<String> subKeys = redisTemplate.keys(path);
        return CollectionSugar.toList(subKeys.iterator());
    }

    @Override
    public <T> Map<String, T> getChildren(String key, Class<T> clazz, String... parent) {
        List<String> children = getSubKeyPaths(key, parent);
        Map<String, T> collect = children.stream().collect(Collectors.toMap(c -> getKeyName(c), c -> (T) redisTemplate.opsForValue().get(c)));
        return collect;
    }

    @Override
    public <T> Map<String, T> getChildren(String key, TypeReference<T> valueTypeRef, String... parent) {
        List<String> children = getSubKeyPaths(key, parent);
        Map<String, T> collect = children.stream().collect(Collectors.toMap(c -> getKeyName(c), c -> (T) redisTemplate.opsForValue().get(c)));
        return collect;
    }


}
