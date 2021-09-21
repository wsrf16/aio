package com.aio.portable.swiss.suite.algorithm.encode;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class JDKMD5Convert {

    public final static byte[] encode(byte[] bytes) {
        try {
            return MessageDigest.getInstance("MD5").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String encodeToHex(String text) {
        try {
            byte[] input = text.getBytes(StandardCharsets.UTF_8);
            byte[] bytes = encode(input);

            return HexConvert.encode(bytes);
        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String encodeToBase64(String text) {
        byte[] input = text.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = encode(input);
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }


}
