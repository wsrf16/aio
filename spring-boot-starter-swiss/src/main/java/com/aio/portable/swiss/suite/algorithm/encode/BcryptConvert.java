package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class BcryptConvert {
        private final static PasswordEncoder bcrypt = PasswordEncoderFactories.createDelegatingPasswordEncoder("bcrypt");

    public final static String encode(CharSequence rawPassword) {
        return bcrypt.encode(rawPassword);
    }

    public final boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bcrypt.matches(rawPassword, encodedPassword);
    }

    public final boolean upgradeEncoding(String encodedPassword) {
        return bcrypt.upgradeEncoding(encodedPassword);
    }

}
