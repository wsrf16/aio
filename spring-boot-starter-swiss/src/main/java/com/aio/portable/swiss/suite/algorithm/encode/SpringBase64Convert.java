package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.spring.web.Base64MultipartFile;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.io.IOSugar;
import org.springframework.util.Base64Utils;

import java.io.File;

public abstract class SpringBase64Convert {
    public static final byte[] encode(byte[] bytes) {
        return Base64Utils.encode(bytes);
    }

    public static final byte[] decode(byte[] bytes) {
        return Base64Utils.decode(bytes);
    }



    public static final byte[] encode(String text) {
        return Base64Utils.encode(text.getBytes());
    }

    public static final String encodeToString(byte[] bytes) {
        return Base64Utils.encodeToString(bytes);
    }

    public static final String encodeToString(String text) {
        return Base64Utils.encodeToString(text.getBytes());
    }

    public static final String encodeToString(File file) {
        return SpringBase64Convert.encodeToString(IOSugar.Streams.toByteArray(file));
    }

    public static final String encodeToString(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return Base64Utils.encodeToString(json.getBytes());
    }


    public static final byte[] decode(String text) {
        return Base64Utils.decode(text.getBytes());
    }

    public static final String decodeToString(byte[] bytes) {
        return Base64Utils.encodeToString(bytes);
    }

    public static final String decodeToString(String text) {
        return Base64Utils.encodeToString(text.getBytes());
    }

    public static final <T> T decodeToT(String cipher, Class<T> clazz) {
        String json = decodeToString(cipher);
        return JacksonSugar.json2T(json, clazz);
    }

    public static final String convertHexToBase64(String hex) {
        return SpringBase64Convert.encodeToString(HexConvert.decode(hex));
    }

    public static final String convertBase64ToHex(String encoded) {
        return HexConvert.encode(SpringBase64Convert.decode(encoded));
    }

    public static final Base64MultipartFile decodeToMultipartFile(String text) {
        return Base64MultipartFile.toMultipartFile(text);
    }

}
