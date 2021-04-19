package com.aio.portable.swiss.suite.algorithm.encode;

import org.springframework.util.Base64Utils;
import sun.net.www.content.text.plain;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public abstract class SpringBase64Convert {
    public final static byte[] encode(byte[] bytes) {
        return Base64Utils.encode(bytes);
    }

    public final static String encodeToString(byte[] bytes) {
        return Base64Utils.encodeToString(bytes);
    }

    public final static byte[] decode(byte[] bytes) {
        return Base64Utils.decode(bytes);
    }

    public final static byte[] decodeFromString(String cipher) {
        return Base64Utils.decodeFromString(cipher);
    }
}
