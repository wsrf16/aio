package com.aio.portable.swiss.suite.algorithm.encode;

public abstract class JDKBase64Convert {
    public final static byte[] encode(byte[] bytes) {
        return java.util.Base64.getEncoder().encode(bytes);
    }

    public final static String encodeToString(byte[] bytes) {
        return new String(java.util.Base64.getEncoder().encode(bytes));
    }

    public final static byte[] decode(byte[] bytes) {
        return java.util.Base64.getDecoder().decode(bytes);
    }

    public final static byte[] decodeFromString(String cipher) {
        return java.util.Base64.getDecoder().decode(cipher);
    }

}
