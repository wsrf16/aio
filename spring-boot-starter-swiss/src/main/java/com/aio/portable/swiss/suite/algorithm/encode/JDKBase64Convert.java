package com.aio.portable.swiss.suite.algorithm.encode;

import java.util.Base64;

public abstract class JDKBase64Convert {
    public static final byte[] encode(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public static final byte[] decode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }



    public static final byte[] encode(String text) {
        return Base64.getEncoder().encode(text.getBytes());
    }

    public static final String encodeToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static final String encodeToString(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public static final byte[] decode(String text) {
        return Base64.getDecoder().decode(text.getBytes());
    }

    public static final String decodeToString(byte[] bytes) {
        return new String(decode(bytes));
    }

    public static final String decodeToString(String cipher) {
        return new String(Base64.getDecoder().decode(cipher));
    }

    public static final String convertHexToBase64(String hex) {
        String base64 = JDKBase64Convert.encodeToString(HexConvert.decode(hex));
        return base64;
    }

    public static final String convertBase64ToHex(String encoded) {
        String hex = HexConvert.encode(JDKBase64Convert.decode(encoded));
        return hex;
    }
}
