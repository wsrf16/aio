package com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder;

import com.aio.portable.swiss.suite.algorithm.encode.SpringMD5Convert;
import org.springframework.security.crypto.password.PasswordEncoder;

class PureMD5PasswordEncoder implements PasswordEncoder {
    private boolean encodeHashAsBase64;

    public void setEncodeHashAsBase64(boolean encodeHashAsBase64) {
        this.encodeHashAsBase64 = encodeHashAsBase64;
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return digest(rawPassword);
    }

    private final String digest(CharSequence rawPassword) {
        String encodedPassword = this.encodeHashAsBase64 ?
                SpringMD5Convert.encodeToBase64(rawPassword.toString()) :
                SpringMD5Convert.encodeToHex(rawPassword.toString());
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
