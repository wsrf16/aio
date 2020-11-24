package com.aio.portable.swiss.suite.storage.cache;

import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

// Redisson
public class RedisLock {
    private final static String KEY_NAME = "REDIS_LOCK";
    private RedisConnectionFactory redisConnectionFactory;

    public RedisLock(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    private static String getKeyName(String key) {
        String lockKey = MessageFormat.format("{0}:{1}", KEY_NAME, key);
        return lockKey;
    }

    public String lock(String lockName, long expireMillisecond) {
        return tryLock(lockName, expireMillisecond, 1);
    }


    /**
     * tryLock
     *
     * @param key
     * @param expireMillisecond
     * @param tryTimeout
     * @return
     */
    public String tryLock(String key, long expireMillisecond, long tryTimeout) {
        if (!StringUtils.hasText(key))
            throw new IllegalArgumentException(key);

        String identifier = IDS.uuid();
        String lockKey = getKeyName(key);
        byte[] lockKeyBytes = lockKey.getBytes();
        long lockExpire = expireMillisecond / 1000;
        long end = System.currentTimeMillis() + tryTimeout;

        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        while (true) {
//            if (redisConnection.setNX(lockKeyBytes, identifier.getBytes())) {
//                redisConnection.expire(lockKeyBytes, lockExpire);
//            }
            Boolean b = redisConnection.set(lockKeyBytes, identifier.getBytes(), Expiration.milliseconds(expireMillisecond), RedisStringCommands.SetOption.SET_IF_PRESENT);
            if (b) {
                break;
                // 返回-1代表key没有设置超时时间，为key设置一个超时时间
            } else if (redisConnection.ttl(lockKeyBytes) == -1) {
                redisConnection.expire(lockKeyBytes, lockExpire);
            } else {
                if (System.currentTimeMillis() > end)
                    break;
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
     * @return
     */
    public boolean releaseLock(String key) {
        if (!StringUtils.hasText(key))
            throw new IllegalArgumentException(key);

        String lockKey = getKeyName(key);
        byte[] lockKeyBytes = lockKey.getBytes();
        RedisConnection redisConnection = redisConnectionFactory.getConnection();
        boolean releaseFlag;
        while (true) {
            // 监视lock，开始事务
            redisConnection.watch(lockKeyBytes);
            // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
            byte[] valueBytes = redisConnection.get(lockKeyBytes);
            if (valueBytes == null) {
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

