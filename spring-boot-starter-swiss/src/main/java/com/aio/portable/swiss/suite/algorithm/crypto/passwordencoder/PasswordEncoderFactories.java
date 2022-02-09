package com.aio.portable.swiss.suite.algorithm.crypto.passwordencoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public class PasswordEncoderFactories {
    public static final Map<String, PasswordEncoder> encoders = new HashMap();

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

    public static final PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt";
        return createDelegatingPasswordEncoder(encodingId);
    }

    public static final PasswordEncoder createDelegatingPasswordEncoder(String encodingId) {
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }


    public static final PasswordEncoder createDelegatingPasswordEncoderMD5Base64() {
        return new DelegatingPasswordEncoder("MD5Base64", encoders);
    }
}
