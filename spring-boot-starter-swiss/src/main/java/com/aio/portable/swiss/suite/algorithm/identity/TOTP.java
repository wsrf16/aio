package com.aio.portable.swiss.suite.algorithm.identity;

import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.encode.HexConvert;

import java.security.MessageDigest;
import java.util.regex.Pattern;

// Time-based One-time Password RFC6238
public class TOTP {
    public static final String build(String secret) {
        return build(secret, 30);
    }

    public static final String build(String secret, int TS) {
        return build(secret, TS, 6);
    }

    private static final String build(String secret, int TS, int digits) {
        Pattern pattern = Pattern.compile("\\d");
        String key = secret;

        String TC = String.valueOf((int) Math.floor(DateTimeSugar.UnixTime.nowUnixSeconds() / TS));
        String TOTP = hash(key + TC);
        String result = afterHash(TOTP, digits);
        return result;
    }

    private static final String hash(String text) {
//        return JDKBase64Convert.encodeToString(text);
        return hash("SHA-1", text);
    }

    private static final String afterHash(String TOTP, int digits) {
        String result = TOTP.replaceAll("\\D", "");
        if (result.length() > digits)
            result = result.substring(result.length() - digits);
        else {
            throw new RuntimeException("digit is too long.");
        }
        return result;
    }

    private static final String hash(String algorithm, String text) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] bytes = md.digest(text.getBytes("utf-8"));
            return HexConvert.encode(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
