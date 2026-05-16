package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder.PasswordEncoderFactoriesSugar;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class BcryptConvert {
    private static final PasswordEncoder bcrypt = PasswordEncoderFactoriesSugar.createPasswordEncoder("bcrypt");

    public static String encode(CharSequence rawPassword) {
        return bcrypt.encode(rawPassword);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return bcrypt.matches(rawPassword, encodedPassword);
    }

    public static boolean upgradeEncoding(String encodedPassword) {
        return bcrypt.upgradeEncoding(encodedPassword);
    }

}
