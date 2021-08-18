package com.aio.portable.swiss.suite.security.authorization.shiro.cache;

import org.apache.shiro.cache.Cache;

import java.util.HashMap;
import java.util.Set;

public class HashMapCache<K, V> extends HashMap<K, V> implements Cache<K, V> {
    @Override
    public Set<K> keys() {
        return this.keySet();
    }
}
