package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.algorithm.cipher.CipherSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class CipherTest {
    @Test
    public static void todo() {
        String a1 = CipherSugar.ApacheCommon.md5("aaa");
        String a2 = CipherSugar.SpringFrameWorkUtil.encodeBase64("aaa");
    }
}
