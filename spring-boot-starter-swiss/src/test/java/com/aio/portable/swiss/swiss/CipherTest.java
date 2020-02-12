package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.sugar.algorithm.cipher.CipherSugar;
import com.aio.portable.swiss.sugar.algorithm.cipher.passwordencoder.PasswordEncoderFactories;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestComponent
public class CipherTest {
    @Test
    public static void todo() {
        String md5AsHex = CipherSugar.SpringFrameWorkUtil.md5AsHex("1");
        String md5AsBase64 = CipherSugar.SpringFrameWorkUtil.md5AsBase64("1");

        String s1 = CipherSugar.SpringFrameWorkUtil.md5FromBase64ToHex(md5AsBase64);
        String s2 = CipherSugar.SpringFrameWorkUtil.md5FromHexToBase64(md5AsHex);

        PasswordEncoder md5PasswordEncoder = PasswordEncoderFactories.createDelegatingMD5PasswordEncoder();
        String md5 = md5PasswordEncoder.encode("1");
        boolean md51 = md5PasswordEncoder.matches("1", md5);
    }
}
