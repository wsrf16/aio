package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.springframework.util.Base64Utils;

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

    public static final String encodeToString(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return Base64Utils.encodeToString(json.getBytes());
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

    public static final <T> T decodeToT(String cipher, Class<T> clazz) {
        String json = decodeToString(cipher);
        return JacksonSugar.json2T(json, clazz);
    }


    public static final String convertHexToBase64(String hex) {
        return JDKBase64Convert.encodeToString(HexConvert.decode(hex));
    }

    public static final String convertBase64ToHex(String encoded) {
        return HexConvert.encode(JDKBase64Convert.decode(encoded));
    }
}
