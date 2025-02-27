package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class JDKMD5Convert {
    public static final byte[] encode(byte[] bytes) {
        try {
            return MessageDigest.getInstance("MD5").digest(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String encodeToHex(String text) {
        byte[] input = text.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = encode(input);
        return HexConvert.encode(bytes);
    }

    public static final String encodeToHex(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return encodeToHex(json);
    }

    public static final String encodeToBase64(String text) {
        byte[] input = text.getBytes(StandardCharsets.UTF_8);
        byte[] bytes = encode(input);
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }

    public static final String encodeToBase64(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return encodeToBase64(json);
    }

//    public static final <T> T byteToObj(byte[] data, Class<T> clazz){
//        String json = new String(data, StandardCharsets.UTF_8);
//        return JacksonSugar.json2T(json, clazz);
//    }
//
//    public static final byte[] objToByte(Object obj){
//        String json = JacksonSugar.obj2ShortJson(obj);
//        return json.getBytes(StandardCharsets.UTF_8);
//    }

    public static final String encode(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }


}
