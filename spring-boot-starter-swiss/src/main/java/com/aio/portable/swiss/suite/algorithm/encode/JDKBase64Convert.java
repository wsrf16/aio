package com.aio.portable.swiss.suite.algorithm.encode;

import java.util.Base64;

public abstract class JDKBase64Convert {
    public final static byte[] encode(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public final static byte[] decode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }



    public final static byte[] encode(String text) {
        return Base64.getEncoder().encode(text.getBytes());
    }

    public final static String encodeToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public final static String encodeToString(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    public final static byte[] decode(String text) {
        return Base64.getDecoder().decode(text.getBytes());
    }

    public final static String decodeToString(byte[] bytes) {
        return new String(decode(bytes));
    }

    public final static String decodeToString(String cipher) {
        return new String(Base64.getDecoder().decode(cipher));
    }

    public final static String convertHexToBase64(String text) {
        String base64 = JDKBase64Convert.encodeToString(HexConvert.decode(text));
        return base64;
    }

    public final static String convertBase64ToHex(String text) {
        String hex = HexConvert.encode(JDKBase64Convert.decode(text));
        return hex;
    }
}
