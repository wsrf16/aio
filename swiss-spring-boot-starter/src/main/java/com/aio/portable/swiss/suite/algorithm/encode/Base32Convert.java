package com.aio.portable.swiss.suite.algorithm.encode;

import java.nio.charset.StandardCharsets;

public abstract class Base32Convert {
    public static byte[] encode(byte[] bytes) {
        return encodeToString(bytes).getBytes(StandardCharsets.UTF_8);
    }

    public static byte[] decode(byte[] bytes) {
        return decode(new String(bytes));
    }

    public static byte[] encode(String text) {
        return encode(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeToString(byte[] bytes) {
        return Base32.encode(bytes);
    }

    public static String encodeToString(String text) {
        return Base32.encode(text.getBytes());
    }

    public static byte[] decode(String text) {
        return Base32.decode(text);
    }

    public static String decodeToString(byte[] bytes) {
        return new String(decode(bytes));
    }

    public static String decodeToString(String cipher) {
        return new String(decode(cipher));
    }

}
