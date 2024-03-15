package com.aio.portable.swiss.suite.algorithm.encode;

import java.nio.charset.StandardCharsets;

public abstract class Base32Convert {
    public static final byte[] encode(byte[] bytes) {
        return encodeToString(bytes).getBytes(StandardCharsets.UTF_8);
    }

    public static final byte[] decode(byte[] bytes) {
        return decode(new String(bytes));
    }

    public static final byte[] encode(String text) {
        return encode(text.getBytes(StandardCharsets.UTF_8));
    }

    public static final String encodeToString(byte[] bytes) {
        return Base32.encode(bytes);
    }

    public static final String encodeToString(String text) {
        return Base32.encode(text.getBytes());
    }

    public static final byte[] decode(String text) {
        return Base32.decode(text);
    }

    public static final String decodeToString(byte[] bytes) {
        return new String(decode(bytes));
    }

    public static final String decodeToString(String cipher) {
        return new String(decode(cipher));
    }

}
