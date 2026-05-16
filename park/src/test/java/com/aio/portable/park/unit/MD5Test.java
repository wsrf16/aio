package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder.PasswordEncoderFactoriesSugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKMD5Convert;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestComponent
public class MD5Test {
    @Test
    public void foobar() {
        String md5AsHex = JDKMD5Convert.encodeToHex("1");
        String md5AsBase64 = JDKMD5Convert.encodeToBase64("1");

        String s1 = JDKMD5Convert.convertBase64ToHex(md5AsBase64);
        String s2 = JDKMD5Convert.convertHexToBase64(md5AsHex);

        PasswordEncoder md5PasswordEncoder = PasswordEncoderFactoriesSugar.createPureMD5PasswordEncoder();
        String md5 = md5PasswordEncoder.encode("1");
        boolean md51 = md5PasswordEncoder.matches("1", md5);
    }
}
