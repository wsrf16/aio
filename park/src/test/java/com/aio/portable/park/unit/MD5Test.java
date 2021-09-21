package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder.PasswordEncoderFactories;
import com.aio.portable.swiss.suite.algorithm.encode.SpringMD5Convert;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestComponent
public class MD5Test {
    @Test
    public void foobar() {
        String md5AsHex = SpringMD5Convert.encodeToHex("1");
        String md5AsBase64 = SpringMD5Convert.encodeToBase64("1");

        String s1 = SpringMD5Convert.convertBase64ToHex(md5AsBase64);
        String s2 = SpringMD5Convert.convertHexToBase64(md5AsHex);

        PasswordEncoder md5PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoderMD5Base64();
        String md5 = md5PasswordEncoder.encode("1");
        boolean md51 = md5PasswordEncoder.matches("1", md5);
    }
}
