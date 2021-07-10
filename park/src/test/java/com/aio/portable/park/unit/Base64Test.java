package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.cipher.passwordencoder.PasswordEncoderFactories;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.algorithm.encode.SpringBase64Convert;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestComponent
public class Base64Test {
    @Test
    public void foobar() {
        {
            String base64 = JDKBase64Convert.encodeToString("1111".getBytes());
            byte[] binary = JDKBase64Convert.decodeFromString(base64);
        }
        {
            String base64 = SpringBase64Convert.encodeToString("1111".getBytes());
            byte[] binary = SpringBase64Convert.decodeFromString(base64);
        }
        PasswordEncoder md5PasswordEncoder = PasswordEncoderFactories.createDelegatingMD5PasswordEncoder();
        String md5 = md5PasswordEncoder.encode("1");
        boolean md51 = md5PasswordEncoder.matches("1", md5);
    }
}
