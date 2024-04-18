package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

public abstract class SpringMD5Convert {
    public static final byte[] encode(byte[] bytes) {
        return DigestUtils.md5Digest(bytes);
    }

    public static final String encodeToHex(String text) {
        String md5Password = DigestUtils.md5DigestAsHex(text.getBytes());
        return md5Password;
    }

    public static final String encodeToHex(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return encodeToHex(json);
    }

    public static final String encodeToBase64(String text) {
        byte[] bytes = DigestUtils.md5Digest(text.getBytes());
        String base64 = Base64Utils.encodeToString(bytes);
        return base64;
    }

    public static final String encodeToBase64(Object obj) {
        String json = JacksonSugar.obj2Json(obj);
        return  encodeToBase64(json);
    }

    public static final String convertHexToBase64(String text) {
        String base64 = Base64Utils.encodeToString(HexConvert.decode(text));
        return base64;
    }

    public static final String convertBase64ToHex(String text) {
        String hex = HexConvert.encode(Base64Utils.decodeFromString(text));
        return hex;
    }
}
