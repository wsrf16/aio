package com.aio.portable.swiss.sugar.ciphering;

//import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Base64Utils;
//import sun.misc.BASE64Encoder;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by York on 2017/11/28.
 */
public abstract class CipheringSugar {


//    public final static String md5(String text) {
//        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
//
//        try {
//            byte[] btInput = text.getBytes("utf-8");
//            MessageDigest digest = MessageDigest.newInstance("MD5");
//
//            // digest.update(btInput);
//            // byte[] md = digest.digest();
//            // or
//            byte[] md = digest.digest(btInput);
//
//
//            // 把密文转换成十六进制的字符串形式
//            int j = md.length;
//            char str[] = new char[j * 2];
//            int k = 0;
//            for (int i = 0; i < j; i++) {
//                byte byte0 = md[i];
//                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
//                str[k++] = hexDigits[byte0 & 0xf];
//            }
//            return new String(str);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }


//    public final static String md5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        byte[] bytes = text.getBytes("utf-8");
//        MessageDigest digest = MessageDigest.newInstance("MD5");
//        digest.update(bytes);
//
////        BASE64Encoder base64en = new BASE64Encoder();
////        String newstr = base64en.encode(digest.digest(text.getBytes("utf-8")));
////        return newstr;
//
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
    }

    public final static class JavaUtil {
        public final static String encode(String plain){
            byte[] bytes = java.util.Base64.getEncoder().encode(plain.getBytes());
            String cipher = new String(bytes);
            return cipher;
        }

        public final static String encode(String plain, Charset charset){
            byte[] bytes = java.util.Base64.getEncoder().encode(plain.getBytes());
            String cipher = new String(bytes, charset);
            return cipher;
        }

        public final static String decode(String cipher){
            byte[] bytes = java.util.Base64.getDecoder().decode(cipher);
            String plain = new String(bytes);
            return plain;
        }

        public final static String decode(String cipher, Charset charset){
            byte[] bytes = java.util.Base64.getDecoder().decode(cipher);
            String plain = new String(bytes, charset);
            return plain;
        }
    }

    public final static class ApacheCommon {
        public final static String sha1(String text) {
            String cipher = org.apache.commons.codec.digest.DigestUtils.sha1Hex(text);
            return cipher;
        }

        public final static String md5(String text) {
            String cipher = org.apache.commons.codec.digest.DigestUtils.md5Hex(text);
            return cipher;
        }


        public final static String encodeBase64(org.apache.commons.codec.binary.Base64 base64, String text) {
            String cipher = base64.encodeAsString(text.getBytes());
            return cipher;
        }

        public final static String encodeBase64(org.apache.commons.codec.binary.Base64 base64, String text, Charset charset) {
            String cipher = base64.encodeAsString(text.getBytes(charset));
            return cipher;
        }

        public final static String encodeBase64(org.apache.commons.codec.binary.Base64 base64, String text, String charsetName) throws UnsupportedEncodingException {
            String cipher = base64.encodeAsString(text.getBytes(charsetName));
            return cipher;
        }

        public final static String decodeBase64(org.apache.commons.codec.binary.Base64 base64, String cipher) {
            byte[] bytes = base64.decode(cipher);
            String plain = new String(bytes);
            return plain;
        }

        public final static String decodeBase64(org.apache.commons.codec.binary.Base64 base64, String cipher, Charset charset) {
            byte[] bytes = base64.decode(cipher);
            String plain = new String(bytes, charset);
            return plain;
        }

        public final static String decodeBase64(org.apache.commons.codec.binary.Base64 base64, String cipher, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = base64.decode(cipher);
            String plain = new String(bytes, charsetName);
            return plain;
        }

        public final static String encodeBase64(String plain) {
            byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(plain.getBytes());
            String cipher = new String(bytes);
            return cipher;
        }

        public final static String encodeBase64(String plain, Charset charset) {
            byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(plain.getBytes());
            String cipher = new String(bytes, charset);
            return cipher;
        }

        public final static String encodeBase64(String plain, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = org.apache.commons.codec.binary.Base64.encodeBase64(plain.getBytes());
            String cipher = new String(bytes, charsetName);
            return cipher;
        }

        public final static String decodeBase64(String cipher) {
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(cipher);
            String plain = new String(bytes);
            return plain;
        }

        public final static String decodeBase64(String cipher, Charset charset) {
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(cipher);
            String plain = new String(bytes, charset);
            return plain;
        }

        public final static String decodeBase64(String cipher, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = org.apache.commons.codec.binary.Base64.decodeBase64(cipher);
            String plain = new String(bytes, charsetName);
            return plain;
        }
    }

//    public final static String encryptDES(String text, String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
//        Key keySpec = new SecretKeySpec(key.getBytes(), "DES");
//        Cipher cipher = Cipher.newInstance("AES/CBC/PKCS5Padding");
//    }

//    public final static String encryptDES(String text, String key) {
//        Key keySpec = new SecretKeySpec(key.getBytes(), "AES");
//
//    }



    private static class BlahUnit {
        private static void todo() {
            String a1 = CipheringSugar.ApacheCommon.md5("aaa");
            String a2 = SpringFrameWorkUtil.encodeBase64("aaa");
        }
    }
}
