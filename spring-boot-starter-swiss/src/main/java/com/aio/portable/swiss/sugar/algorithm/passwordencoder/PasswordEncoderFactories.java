package com.aio.portable.swiss.sugar.algorithm.passwordencoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

public class PasswordEncoderFactories {
    private final static Map<String, PasswordEncoder> encoders = new HashMap();

    static {
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        encoders.put("ldap", new LdapShaPasswordEncoder());
        encoders.put("MD4", new Md4PasswordEncoder());
        encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("SHA-1", new MessageDigestPasswordEncoder("SHA-1"));
        encoders.put("SHA-256", new MessageDigestPasswordEncoder("SHA-256"));
        encoders.put("sha256", new StandardPasswordEncoder());
        encoders.put("MD5Base64", new MD5Base64PasswordEncoder());
    }

    public final static PasswordEncoder createDelegatingPasswordEncoder(String encodingId) {
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }


    public final static PasswordEncoder createDelegatingMD5PasswordEncoder() {
        Map<String, PasswordEncoder> encoders = new HashMap();
        encoders.put("MD5", new MD5Base64PasswordEncoder());
        return new DelegatingPasswordEncoder("MD5", encoders);
    }
}
