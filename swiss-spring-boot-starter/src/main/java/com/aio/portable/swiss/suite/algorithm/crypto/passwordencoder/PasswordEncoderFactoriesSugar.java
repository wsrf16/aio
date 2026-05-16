package com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

public class PasswordEncoderFactoriesSugar {
    public static final Map<String, PasswordEncoder> encoders;

    static {
        PasswordEncoder delegatingPasswordEncoder = org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder();

        encoders = ClassSugar.Fields.getDeclaredFieldValue(delegatingPasswordEncoder, "idToPasswordEncoder");
        encoders.put("PureMD5", new PureMD5PasswordEncoder());
    }

//    public static PasswordEncoder createDelegatingPasswordEncoder() {
//        String encodingId = "bcrypt";
//        return createDelegatingPasswordEncoder(encodingId);
//    }

    public static PasswordEncoder createPasswordEncoder(String encodingId) {
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }


    public static PasswordEncoder createPureMD5PasswordEncoder() {
        return createPasswordEncoder("PureMD5");
    }
}
