package com.aio.portable.swiss.suite.security.authentication.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Component;

//@Component
public class HashMapCacheManager implements CacheManager {
//    @Autowired
//    CustomCache cacheClient;

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new HashMapCache<K,V>();
    }
}





//
//
///**
// * 重写Shiro的Cache保存读取
// * @param <K>
// * @param <V>
// */
//public class ShiroCache<K,V> implements Cache<K,V> {
//
//    private CacheClient cacheClient;
//
//    public ShiroCache(CacheClient cacheClient) {
//        this.cacheClient = cacheClient;
//    }
//
//    /**
//     * 获取缓存
//     * @param key
//     * @return
//     * @throws CacheException
//     */
//    @Override
//    public Object get(Object key) throws CacheException {
//        String tempKey= this.getKey(key);
//        if(cacheClient.exists(tempKey)){
//            return cacheClient.getObject(tempKey);
//        }
//        return null;
//    }
//
//    /**
//     * 保存缓存
//     * @param key
//     * @param value
//     * @return
//     * @throws CacheException
//     */
//    @Override
//    public Object put(Object key, Object value) throws CacheException {
//        return cacheClient.setObject(this.getKey(key), value);
//    }
//
//    /**
//     * 移除缓存
//     * @param key
//     * @return
//     * @throws CacheException
//     */
//    @Override
//    public Object remove(Object key) throws CacheException {
//        String tempKey= this.getKey(key);
//        if(cacheClient.exists(tempKey)){
//            cacheClient.del(tempKey);
//        }
//        return null;
//    }
//
//    @Override
//    public void clear() throws CacheException {}
//
//    @Override
//    public int size() {
//        //@TODO
//        return 20;
//    }
//
//    @Override
//    public Set<K> keys() {
//        return null;
//    }
//
//    @Override
//    public Collection<V> values() {
//        Set keys = this.keys();
//        List<V> values = new ArrayList<>();
//        for (Object key : keys) {
//            values.add((V)cacheClient.getObject(this.getKey(key)));
//        }
//        return values;
//    }
//
//    /**
//     * 根据名称获取
//     * @param key
//     * @return
//     */
//    private String getKey(Object key) {
//        return SecurityConsts.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), SecurityConsts.ACCOUNT);
//    }
//}
