package com.aio.portable.swiss.suite.algorithm.encode;

import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

public abstract class SpringMD5Convert {
    public final static byte[] md5(byte[] bytes) {
        return DigestUtils.md5Digest(bytes);
    }

    public final static String md5AsHex(String text) {
        String md5Password = DigestUtils.md5DigestAsHex(text.getBytes());
        return md5Password;
    }

    public final static String md5AsBase64(String text) {
        byte[] bytes = DigestUtils.md5Digest(text.getBytes());
        String base64 = Base64Utils.encodeToString(bytes);
        return base64;
    }

    public final static String md5FromHexToBase64(String text) {
        String base64 = Base64Utils.encodeToString(HexConvert.toBytes(text));
        return base64;
    }

    public final static String md5FromBase64ToHex(String text) {
        String hex = HexConvert.toHex2(Base64Utils.decodeFromString(text));
        return hex;
    }
}
