package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.identity.ULID;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class ULIDTest {
    ULID.MonotonicULIDSpi monotonicULIDSpi = ULID.monotonicULID();


    @Test
    public void foobar() {
        for (int i = 0; i < 100; i++) {
            ULID next = monotonicULIDSpi.next();
            System.out.println(next);
        }
    }

}
