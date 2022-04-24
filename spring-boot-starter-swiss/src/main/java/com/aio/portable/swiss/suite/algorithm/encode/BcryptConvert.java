package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class BcryptConvert {
        private static final PasswordEncoder bcrypt = PasswordEncoderFactories.createPasswordEncoder("bcrypt");

    public static final String encode(CharSequence rawPassword) {
        return bcrypt.encode(rawPassword);
    }

    public final boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bcrypt.matches(rawPassword, encodedPassword);
    }

    public final boolean upgradeEncoding(String encodedPassword) {
        return bcrypt.upgradeEncoding(encodedPassword);
    }

}
