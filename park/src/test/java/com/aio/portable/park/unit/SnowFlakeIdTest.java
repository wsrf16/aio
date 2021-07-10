package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.identity.SnowflakeIdWorker;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class SnowFlakeIdTest {
    @Test
    public final static void foobar(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}
