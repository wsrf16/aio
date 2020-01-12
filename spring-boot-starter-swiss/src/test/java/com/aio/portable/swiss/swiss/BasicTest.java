package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.BasicType;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class BasicTest {
    @Test
    public static void todo() {
        BasicType.SerialNumberBuilder serialNumberBuilder = new BasicType().serialNumberBuilder();
        String s1 = serialNumberBuilder.build();
        String s2 = serialNumberBuilder.build();
    }
}
