package com.aio.portable.swiss.suite.security;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * bcrypt - BCryptPasswordEncoder (Also used for encoding)
 * ldap - LdapShaPasswordEncoder
 * MD4 - Md4PasswordEncoder
 * MD5 - new MessageDigestPasswordEncoder("MD5")
 * noop - NoOpPasswordEncoder
 * pbkdf2 - Pbkdf2PasswordEncoder
 * scrypt - SCryptPasswordEncoder
 * SHA-1 - new MessageDigestPasswordEncoder("SHA-1")
 * SHA-256 - new MessageDigestPasswordEncoder("SHA-256")
 * sha256 - StandardPasswordEncoder
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    private static final PasswordEncoder INSTANCE = new CustomPasswordEncoder();

    public static PasswordEncoder getInstance() {
        return INSTANCE;
    }

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
