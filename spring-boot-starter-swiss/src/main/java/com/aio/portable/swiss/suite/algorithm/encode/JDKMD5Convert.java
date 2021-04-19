package com.aio.portable.swiss.suite.algorithm.encode;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class JDKMD5Convert {

    public final static byte[] md5(byte[] bytes) {
        try {
            return MessageDigest.getInstance("MD5").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String md5AsHex(String text) {
        try {
            byte[] input = text.getBytes(StandardCharsets.UTF_8);
            byte[] bytes = md5(input);

            return HexConvert.toHex1(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String md5AsBase64(String text) {
        byte[] input = text.getBytes(StandardCharsets.UTF_8);
        byte[] md5Bytes = md5(input);
        return java.util.Base64.getEncoder().encodeToString(md5Bytes);
    }
}
