package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.net.tcp.http.RestTemplater;
import org.checkerframework.checker.units.qual.K;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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

    }
}
