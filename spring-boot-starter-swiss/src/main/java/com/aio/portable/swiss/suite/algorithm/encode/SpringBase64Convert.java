package com.aio.portable.swiss.suite.algorithm.encode;

import org.springframework.util.Base64Utils;

public abstract class SpringBase64Convert {
    public final static byte[] encode(byte[] bytes) {
        return Base64Utils.encode(bytes);
    }

    public final static byte[] decode(byte[] bytes) {
        return Base64Utils.decode(bytes);
    }



    public final static byte[] encode(String text) {
        return Base64Utils.encode(text.getBytes());
    }

    public final static String encodeToString(byte[] bytes) {
        return Base64Utils.encodeToString(bytes);
    }

    public final static String encodeToString(String text) {
        return Base64Utils.encodeToString(text.getBytes());
    }

    public final static byte[] decode(String text) {
        return Base64Utils.decode(text.getBytes());
    }

    public final static String decodeToString(byte[] bytes) {
        return Base64Utils.encodeToString(bytes);
    }

    public final static String decodeToString(String text) {
        return Base64Utils.encodeToString(text.getBytes());
    }
}
