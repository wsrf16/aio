package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.algorithm.ciphering.CipheringSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class CipherTest {
    @Test
    public static void todo() {
        String a1 = CipheringSugar.ApacheCommon.md5("aaa");
        String a2 = CipheringSugar.SpringFrameWorkUtil.encodeBase64("aaa");
    }
}
