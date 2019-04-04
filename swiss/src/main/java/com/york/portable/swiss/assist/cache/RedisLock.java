package com.york.portable.swiss.assist.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

public class RedisLock {
    private final static String KEY_NAME = "REDIS_LOCK";
    private RedisConnectionFactory redisConnectionFactory;

    public RedisLock(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public String lock(String lockName) {
        return acquireLock(lockName, 60000, 60000);
    }

    /**
     * 获取锁
     *
     * @param key
     * @param acquireMillisecond
     * @param expireMillisecond
     * @return
     */
    public String acquireLock(String key, long acquireMillisecond, long expireMillisecond) {
        if (StringUtils.isBlank(key))
            throw new IllegalArgumentException(key);

        String identifier = UUID.randomUUID().toString();
        String lockKey = MessageFormat.format("{0}:{1}", KEY_NAME, key);
        byte[] lockKeyBytes = lockKey.getBytes();
        long lockExpire = expireMillisecond / 1000;
        long end = System.currentTimeMillis() + acquireMillisecond;

        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        while (System.currentTimeMillis() < end) {
            if (redisConnection.setNX(lockKeyBytes, identifier.getBytes())) {
                redisConnection.expire(lockKeyBytes, lockExpire);
                break;
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
            } else if (redisConnection.ttl(lockKeyBytes) == -1) {
                redisConnection.expire(lockKeyBytes, lockExpire);
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        RedisConnectionUtils.releaseConnection(redisConnection, redisConnectionFactory);
        return identifier;
    }

    /**
     * 释放锁
     *
     * @param key
     * @param identifier
     * @return
     */
    public boolean releaseLock(String key, String identifier) {
        if (StringUtils.isBlank(key))
            throw new IllegalArgumentException(key);
        if (StringUtils.isBlank(identifier))
            throw new IllegalArgumentException(identifier);

        String lockKey = MessageFormat.format("{0}:{1}", KEY_NAME, key);
        byte[] lockKeyBytes = lockKey.getBytes();
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        boolean releaseFlag = false;
        while (true) {
            // 监视lock，开始事务
            redisConnection.watch(lockKeyBytes);
            // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
            byte[] valueBytes = redisConnection.get(lockKeyBytes);
            if (valueBytes == null || !identifier.equals(new String(valueBytes))) {
                releaseFlag = false;
            } else {
                redisConnection.multi();
                redisConnection.del(lockKeyBytes);
                List<Object> results = redisConnection.exec();
                if (results == null) {
                    continue;
                }
                releaseFlag = true;
            }
            redisConnection.unwatch();
            break;
        }
        RedisConnectionUtils.releaseConnection(redisConnection, redisConnectionFactory);
        return releaseFlag;
    }

}

