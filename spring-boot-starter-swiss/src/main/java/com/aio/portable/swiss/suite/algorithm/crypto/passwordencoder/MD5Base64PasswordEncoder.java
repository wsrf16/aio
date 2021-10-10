package com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder;

import com.aio.portable.swiss.suite.algorithm.encode.SpringMD5Convert;
import org.springframework.security.crypto.password.PasswordEncoder;

class MD5Base64PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return digest(rawPassword);
    }

    private final static String digest(CharSequence rawPassword) {
        String encodedPassword = SpringMD5Convert.encodeToBase64(rawPassword.toString());
        return encodedPassword;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String digest = digest(rawPassword);
        return digest.equals(encodedPassword);
    }

//    @Override
//    public boolean upgradeEncoding(String encodedPassword) {
//        return false;
//    }
}
