package com.york.portable.park.unit.swiss;

import com.york.portable.swiss.sugar.RegexUtils;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class RegexUtilsTest {

    @Test
    public static void regex() {
        String ret1 = RegexUtils.fakePhone("12345678901");
        String ret2 = RegexUtils.replaceAll("12345678901", "4567", "xxxx");
        boolean ret3 = RegexUtils.matches("12345678901", "456");
        System.out.println(ret1);
        System.out.println(ret2);
        System.out.println(ret3);
    }
}
