package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.identity.SerialNumberWorker;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class BasicTest {
    @Test
    public void foobar() {
        SerialNumberWorker.SerialNumberBuilder serialNumberBuilder = SerialNumberWorker.builder();
        String s1 = serialNumberBuilder.build();
        String s2 = serialNumberBuilder.build();
    }
}
