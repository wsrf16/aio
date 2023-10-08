package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.suite.storage.cache.RedisLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@ConditionalOnClass({RedisConnectionFactory.class})
@ConditionalOnBean({RedisConnectionFactory.class})
// org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
public class RedisAutoConfiguration {
    @Bean
    public RedisLock redisLock(RedisConnectionFactory redisConnectionFactory) {
        // org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
        return new RedisLock(redisConnectionFactory);
    }

}
