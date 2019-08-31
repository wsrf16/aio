package com.aio.portable.swiss.ciphering;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Base64Utils;
//import sun.misc.BASE64Encoder;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by York on 2017/11/28.
 */
public class TotalEncrypt {
    public final static String sha1(String text) {
        String ret = DigestUtils.sha1Hex(text);
        return ret;
    }

    public final static String md5(String text) {
        String ret = DigestUtils.md5Hex(text);
        return ret;
    }

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

    public static class SpringFrameWork {
        public final static String encodeBase64(String text) {
            byte[] bytes = text.getBytes();
            String ret = Base64Utils.encodeToString(bytes);
            return ret;
        }

        public final static String encodeBase64(String text, Charset charset) {
            byte[] bytes = text.getBytes(charset);
            String ret = Base64Utils.encodeToString(bytes);
            return ret;
        }

        public final static String encodeBase64(String text, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = text.getBytes(charsetName);
            String ret = Base64Utils.encodeToString(bytes);
            return ret;
        }

        public final static String decodeBase64(String text) {
            byte[] bytes = Base64Utils.decodeFromString(text);
            String ret = new String(bytes);
            return ret;
        }

        public final static String decodeBase64(String text, Charset charset) {
            byte[] bytes = Base64Utils.decodeFromString(text);
            String ret = new String(bytes, charset);
            return ret;
        }

        public final static String decodeBase64(String text, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = Base64Utils.decodeFromString(text);
            String ret = new String(bytes, charsetName);
            return ret;
        }
    }


    public final static class JavaUtil {
        public final static String encode(String text){
            byte[] bytes = java.util.Base64.getEncoder().encode(text.getBytes());
            String result = new String(bytes);
            return result;
        }

        public final static String encode(String text, Charset charset){
            byte[] bytes = java.util.Base64.getEncoder().encode(text.getBytes());
            String result = new String(bytes, charset);
            return result;
        }

        public final static String decode(String text){
            byte[] bytes = java.util.Base64.getDecoder().decode(text);
            String result = new String(bytes);
            return result;
        }

        public final static String decode(String text, Charset charset){
            byte[] bytes = java.util.Base64.getDecoder().decode(text);
            String result = new String(bytes, charset);
            return result;
        }
    }

    public final static class ApacheCommon {
        public final static String encode(Base64 base64, String text) {
            String ret = base64.encodeAsString(text.getBytes());
            return ret;
        }

        public final static String encode(Base64 base64, String text, Charset charset) {
            String ret = base64.encodeAsString(text.getBytes(charset));
            return ret;
        }

        public final static String encode(Base64 base64, String text, String charsetName) throws UnsupportedEncodingException {
            String ret = base64.encodeAsString(text.getBytes(charsetName));
            return ret;
        }

        public final static String decode(Base64 base64, String text) {
            byte[] bytes = base64.decode(text);
            String ret = new String(bytes);
            return ret;
        }

        public final static String decode(Base64 base64, String text, Charset charset) {
            byte[] bytes = base64.decode(text);
            String ret = new String(bytes, charset);
            return ret;
        }

        public final static String decode(Base64 base64, String text, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = base64.decode(text);
            String ret = new String(bytes, charsetName);
            return ret;
        }

        public final static String encode(String text) {
            byte[] bytes = Base64.encodeBase64(text.getBytes());
            String result = new String(bytes);
            return result;
        }

        public final static String encode(String text, Charset charset) {
            byte[] bytes = Base64.encodeBase64(text.getBytes());
            String result = new String(bytes, charset);
            return result;
        }

        public final static String encode(String text, String charsetName) throws UnsupportedEncodingException {
            byte[] bytes = Base64.encodeBase64(text.getBytes());
            String result = new String(bytes, charsetName);
            return result;
        }

        public final static String decode(String text) {
            byte[] bytes = Base64.decodeBase64(text);
            String result = new String(bytes);
            return result;
        }

        public final static String decode(String text, Charset charset) {
            byte[] bytes = Base64.decodeBase64(text);
            String result = new String(bytes, charset);
            return result;
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
            String a1 = TotalEncrypt.md5("aaa");
            String a2 = TotalEncrypt.SpringFrameWork.encodeBase64("aaa");
        }
    }
}
