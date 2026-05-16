package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.springframework.util.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public abstract class JDKMD5Convert {
    public static byte[] encode(byte[] bytes) {
//        try {
//            return MessageDigest.getInstance("MD5").digest(bytes);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
        return DigestUtils.md5Digest(bytes);
    }

    public static String encodeToHex(String text, Charset charset) {
        byte[] input = text.getBytes(charset);
        byte[] bytes = encode(input);
        return HexConvert.encode(bytes);
    }

    public static String encodeToHex(String text) {
        return encodeToHex(text, StandardCharsets.UTF_8);
    }

    public static String encodeObjectToHex(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return encodeToHex(json);
    }

    public static String encodeToBase64(String text, Charset charset) {
        byte[] input = text.getBytes(charset);
        byte[] bytes = encode(input);
        return java.util.Base64.getEncoder().encodeToString(bytes);
    }

    public static String encodeToBase64(String text) {
        return encodeToBase64(text, StandardCharsets.UTF_8);
    }

    public static String encodeObjectToBase64(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return encodeToBase64(json);
    }

    public static String convertHexToBase64(String text) {
        String base64 = JDKBase64Convert.encodeToString(HexConvert.decode(text));
        return base64;
    }

    public static String convertBase64ToHex(String text) {
        String hex = HexConvert.encode(JDKBase64Convert.decode(text));
        return hex;
    }

//    public static <T> T byteToObj(byte[] data, Class<T> clazz){
//        String json = new String(data, StandardCharsets.UTF_8);
//        return JacksonSugar.json2T(json, clazz);
//    }
//
//    public static byte[] objToByte(Object obj){
//        String json = JacksonSugar.obj2ShortJson(obj);
//        return json.getBytes(StandardCharsets.UTF_8);
//    }

    public static String encode(File file) {
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
