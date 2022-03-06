package com.aio.portable.park.unit;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.Set;

@TestComponent
public class RedisTemplateTest {
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void foobar() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        HashOperations hashOperations = redisTemplate.opsForHash();
        ListOperations listOperations = redisTemplate.opsForList();
        SetOperations setOperations = redisTemplate.opsForSet();
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();



    }


    public void todo() {
        RedisSerializer<String> keySerializer = redisTemplate.getKeySerializer();
        RedisSerializer<Object> valueSerializer = redisTemplate.getValueSerializer();
        redisTemplate.executePipelined((RedisCallback<Object>) pipeLine -> {
            try {
                for (int i = 0; i < 10000; i++) {
                    String redisKey = "key_" + i;
                    pipeLine.setEx(keySerializer.serialize(redisKey),
                            10,
                            valueSerializer.serialize(i));
                }
            } catch (Exception e) {
            }
            return null;
        });
    }

    public void zset() {
        long delayTime = 86400;
        // 生产
        redisTemplate.opsForZSet().add("key", "val", System.currentTimeMillis() + delayTime);
        // 消费：从 0 到 now，第0位取3个值
        Set<String> sets = redisTemplate.opsForZSet().rangeByScore("key", 0, System.currentTimeMillis(), 0, 3);
    }
}
