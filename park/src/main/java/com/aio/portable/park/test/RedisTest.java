package com.aio.portable.park.test;

import com.aio.portable.swiss.spring.PrefixRedisSerializer;
import com.aio.portable.swiss.suite.algorithm.encode.JDKMD5Convert;
import org.springframework.stereotype.Component;

@Component
public class RedisTest {
    public void redisSerializer() {
        String s = JDKMD5Convert.encodeToBase64("您");
        PrefixRedisSerializer ser = new PrefixRedisSerializer("park");
        byte[] abcs = ser.serialize("abc");
        String deserialize = ser.deserialize(abcs);

    }
}

