package com.aio.portable.swiss.sugar.algorithm.cipher;

//import org.apache.commons.codec.digest.DigestUtils;

import com.aio.portable.swiss.sugar.algorithm.HexConvert;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
//import sun.misc.BASE64Encoder;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Created by York on 2017/11/28.
 */
public abstract class CipherSugar {

//    private static String byteToHexString(byte b) {
//        int n = b;
//        if (n < 0)
//            n += 256;
//        int d1 = n / 16;   //取出高4位
//        int d2 = n % 16;   //取出低4位
//        return new String(new char[]{hexDigits[d1], hexDigits[d2]});
//    }
//    //将字节数组转换成16进制字符串
//    public static String byteArrayToHexString(byte[] bytes) {
//        StringBuffer resultSb = new StringBuffer();
//        for (int i = 0; i < bytes.length; i++)
//            resultSb.append(byteToHexString(bytes[i]));
//        return resultSb.toString();
//    }


    public static class SpringFrameWorkUtil {
        public final static String encodeBase64(String plain) {
            byte[] bytes = plain.getBytes();
            String cipher = Base64Utils.encodeToString(bytes);
            return cipher;
        }

        public final static String encodeBase64(String plain, Charset charset) {
            byte[] bytes = plain.getBytes(charset);
            String cipher = Base64Utils.encodeToString(bytes);
            return cipher;
        }

        public final static String encodeBase64(String plain, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = plain.getBytes(charsetName);
            String cipher = Base64Utils.encodeToString(bytes);
            return cipher;
        }

        public final static String decodeBase64(String cipher) {
            byte[] bytes = Base64Utils.decodeFromString(cipher);
            String plain = new String(bytes);
            return plain;
        }

        public final static String decodeBase64(String cipher, Charset charset) {
            byte[] bytes = Base64Utils.decodeFromString(cipher);
            String plain = new String(bytes, charset);
            return plain;
        }

        public final static String decodeBase64(String cipher, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = Base64Utils.decodeFromString(cipher);
            String plain = new String(bytes, charsetName);
            return plain;
        }

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
            String hex = HexConvert.toString2(Base64Utils.decodeFromString(text));
            return hex;
        }
    }

    public final static class JavaUtil {
        public final static String encodeBase64(String plain) {
            byte[] bytes = java.util.Base64.getEncoder().encode(plain.getBytes());
            String cipher = new String(bytes);
            return cipher;
        }

        public final static String encodeBase64(String plain, Charset charset) {
            byte[] bytes = java.util.Base64.getEncoder().encode(plain.getBytes());
            String cipher = new String(bytes, charset);
            return cipher;
        }

        public final static String decodeBase64(String cipher) {
            byte[] bytes = java.util.Base64.getDecoder().decode(cipher);
            String plain = new String(bytes);
            return plain;
        }

        public final static String decodeBase64(String cipher, Charset charset) {
            byte[] bytes = java.util.Base64.getDecoder().decode(cipher);
            String plain = new String(bytes, charset);
            return plain;
        }

        public final static byte[] md5(byte[] bytes) {
            try {
                return MessageDigest.getInstance("MD5").digest(bytes);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return null;
            }
        }

        public final static String md5AsHex(String text) {
            try {
                byte[] input = text.getBytes("utf-8");
                byte[] bytes = md5(input);

                // 把密文转换成十六进制的字符串形式
                return HexConvert.toString1(bytes);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        public final static String md5AsBase64(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            byte[] textBytes = text.getBytes("utf-8");
            byte[] md5Bytes = MessageDigest.getInstance("MD5").digest(textBytes);
//        String retS = new String(md5Bytes);
            String md5Base64 = java.util.Base64.getEncoder().encodeToString(md5Bytes);
            return md5Base64;
        }

        public final static String md5FromHexToBase64(String text) {
            byte[] bytes = java.util.Base64.getEncoder().encode(HexConvert.toBytes(text));
            String base64 = new String(bytes);
            return base64;
        }

        public final static String md5FromBase64ToHex(String text) {
            String hex = HexConvert.toString2(java.util.Base64.getDecoder().decode(text));
            return hex;
        }
    }

//    public final static class ApacheCommon {
//        public final static String sha1(String text) {
//            String cipher = org.apache.commons.codec.digest.DigestUtils.sha1Hex(text);
//            return cipher;
//        }
//
//        public final static String md5(String text) {
//            String cipher = org.apache.commons.codec.digest.DigestUtils.md5Hex(text);
//            return cipher;
//        }
//
//
//        public final static String encodeBase64(org.apache.commons.codec.binary.Base64 base64, String text) {
//            String cipher = base64.encodeAsString(text.getBytes());
//            return cipher;
//        }
//
//        public final static String encodeBase64(org.apache.commons.codec.binary.Base64 base64, String text, Charset charset) {
//            String cipher = base64.encodeAsString(text.getBytes(charset));
//            return cipher;
//        }
//
//        public final static String encodeBase64(org.apache.commons.codec.binary.Base64 base64, String text, String charsetName) throws UnsupportedEncodingException {
//            String cipher = base64.encodeAsString(text.getBytes(charsetName));
//            return cipher;
//        }
//
//        public final static String decodeBase64(org.apache.commons.codec.binary.Base64 base64, String cipher) {
//            byte[] bytes = base64.decode(cipher);
//            String plain = new String(bytes);
//            return plain;
//        }
//
//        public final static String decodeBase64(org.apache.commons.codec.binary.Base64 base64, String cipher, Charset charset) {
//            byte[] bytes = base64.decode(cipher);
//            String plain = new String(bytes, charset);
//            return plain;
//        }
//
//        public final static String decodeBase64(org.apache.commons.codec.binary.Base64 base64, String cipher, String charsetName) throws UnsupportedEncodingException {
//            byte[] bytes = base64.decode(cipher);
//            String plain = new String(bytes, charsetName);
//            return plain;
//        }
//
//        public final static String encodeBase64(String plain) {
//            byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(plain.getBytes());
//            String cipher = new String(bytes);
//            return cipher;
//        }
//
//        public final static String encodeBase64(String plain, Charset charset) {
//            byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(plain.getBytes());
//            String cipher = new String(bytes, charset);
//            return cipher;
//        }
//
//        public final static String encodeBase64(String plain, String charsetName) throws UnsupportedEncodingException {
//            byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(plain.getBytes());
//            String cipher = new String(bytes, charsetName);
//            return cipher;
//        }
//
//        public final static String decodeBase64(String cipher) {
//            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(cipher);
//            String plain = new String(bytes);
//            return plain;
//        }
//
//        public final static String decodeBase64(String cipher, Charset charset) {
//            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(cipher);
//            String plain = new String(bytes, charset);
//            return plain;
//        }
//
//        public final static String decodeBase64(String cipher, String charsetName) throws UnsupportedEncodingException {
//            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(cipher);
//            String plain = new String(bytes, charsetName);
//            return plain;
//        }
//    }

//    public final static String encryptDES(String text, String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
//        Key keySpec = new SecretKeySpec(key.getBytes(), "DES");
//        Cipher cipher = Cipher.newInstance("AES/CBC/PKCS5Padding");
//    }

//    public final static String encryptDES(String text, String key) {
//        Key keySpec = new SecretKeySpec(key.getBytes(), "AES");
//
//    }


}
