package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.identity.SerialNumber;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class BasicTest {
    @Test
    public void foobar() {
        SerialNumber.SerialNumberBuilder serialNumberBuilder = new SerialNumber().serialNumberBuilder();
        String s1 = serialNumberBuilder.build();
        String s2 = serialNumberBuilder.build();
    }
}
