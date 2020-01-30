package com.aio.portable.swiss.sugar.algorithm.passwordencoder;

import com.aio.portable.swiss.sugar.algorithm.cipher.CipherSugar;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class MD5Base64PasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return digest(rawPassword);
    }

    private final static String digest(CharSequence rawPassword) {
        String encodedPassword = CipherSugar.SpringFrameWorkUtil.md5AsBase64(rawPassword.toString());
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
