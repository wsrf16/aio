package com.aio.portable.swiss.autoconfigure;

import com.aio.portable.swiss.suite.cache.RedisLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;

//@Configuration
@ConditionalOnClass({RedisOperations.class})
// org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
public class RedisAutoConfiguration {
    @Bean
    public RedisLock redisLock(RedisConnectionFactory redisConnectionFactory) {
        // org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
        return new RedisLock(redisConnectionFactory);
    }

}
