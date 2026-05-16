package com.aio.portable.swiss.suite.algorithm.encode;

import com.aio.portable.swiss.spring.web.Base64MultipartFile;
import com.aio.portable.swiss.suite.io.IOSugar;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public abstract class JDKBase64Convert {
    public static byte[] encode(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public static byte[] encode(String text, Charset charset) {
        byte[] bytes = text.getBytes(charset);
        return encode(bytes);
    }

    public static byte[] encode(String text) {
        return encode(text, StandardCharsets.UTF_8);
    }

    public static String encodeToString(byte[] bytes, Charset charset) {
        byte[] encode = encode(bytes);
        return new String(encode, charset);
    }

    public static String encodeToString(byte[] bytes) {
        return encodeToString(bytes, StandardCharsets.UTF_8);
    }

    public static String encodeToString(String text, Charset charset) {
        byte[] bytes = text.getBytes(charset);
        byte[] encode = encode(bytes);
        return new String(encode, charset);
    }

    public static String encodeToString(String text) {
        return encodeToString(text, StandardCharsets.UTF_8);
    }

    public static String encodeToString(File file, Charset charset) {
        return JDKBase64Convert.encodeToString(IOSugar.Streams.toByteArray(file), charset);
    }

    public static String encodeToString(File file) {
        return JDKBase64Convert.encodeToString(IOSugar.Streams.toByteArray(file), StandardCharsets.UTF_8);
    }

    public static byte[] decode(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static byte[] decode(String text, Charset charset) {
        byte[] bytes = text.getBytes(charset);
        return decode(bytes);
    }

    public static byte[] decode(String text) {
        return decode(text, StandardCharsets.UTF_8);
    }

    public static String decodeToString(byte[] bytes, Charset charset) {
        byte[] decode = decode(bytes);
        return new String(decode, charset);
    }

    public static String decodeToString(byte[] bytes) {
        return decodeToString(bytes, StandardCharsets.UTF_8);
    }

    public static String decodeToString(String cipher, Charset charset) {
        byte[] decode = decode(cipher, charset);
        return new String(decode, charset);
    }

    public static String decodeToString(String cipher) {
        return decodeToString(cipher, StandardCharsets.UTF_8);
    }

    public static String convertHexToBase64(String hex) {
        return JDKBase64Convert.encodeToString(HexConvert.decode(hex));
    }

    public static String convertBase64ToHex(String encoded, Charset charset) {
        return HexConvert.encode(JDKBase64Convert.decode(encoded, charset));
    }

    public static String convertBase64ToHex(String encoded) {
        return convertBase64ToHex(encoded, StandardCharsets.UTF_8);
    }

    public static Base64MultipartFile decodeToMultipartFile(String cipher) {
        return Base64MultipartFile.toMultipartFile(cipher);
    }
}
