package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder.PasswordEncoderFactoriesSugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.security.crypto.password.PasswordEncoder;

@TestComponent
public class GeoHashTest {
    @Test
    public void foobar() {
        {
            String base64 = JDKBase64Convert.encodeToString("1111".getBytes());
            byte[] binary = JDKBase64Convert.decode(base64);
        }
        JacksonSugar.obj2Json()
        PasswordEncoder passwordEncoder = PasswordEncoderFactoriesSugar.createPureMD5PasswordEncoder();
        String md5 = passwordEncoder.encode("1");
        boolean md51 = passwordEncoder.matches("1", md5);
    }
}
