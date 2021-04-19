//package com.aio.portable.park.runner;
//
//import org.apache.commons.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.crypto.Cipher;
//import java.io.ByteArrayInputStream;
//import java.io.InputStream;
//import java.security.KeyFactory;
//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.security.Signature;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateFactory;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//
//public class RSASignature {
//    private static final Logger LOGGER = LoggerFactory.getLogger(RSASignature.class);
//    public static final String KEY_ALGORITHM = "RSA";
//    public static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
//    public static final String ENCODING = "utf-8";
//    public static final String X509 = "X.509";
//
//    /**
//     * 获取私钥
//     *
//     * @param key
//     * @return
//     * @throws Exception
//     */
//    public static PrivateKey getPrivateKey(String key) throws Exception {
//        byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
//        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
//        return privateKey;
//    }
//
//    /**
//     * 获取公钥
//     *
//     * @param key
//     * @return
//     * @throws Exception
//     */
//    public static PublicKey getPublicKey(String key) throws Exception {
//        byte[] keyBytes = Base64.decodeBase64(key.getBytes(ENCODING));
//        CertificateFactory certificateFactory = CertificateFactory.getInstance(X509);
//        InputStream in = new ByteArrayInputStream(keyBytes);
//        Certificate certificate = certificateFactory.generateCertificate(in);
//        PublicKey publicKey = certificate.getPublicKey();
//        return publicKey;
//    }
//
//    /**
//     * 使用公钥对明文进行加密，返回BASE64编码的字符串
//     *
//     * @param publicKey
//     * @param plainText
//     * @return
//     */
////    public static String encrypt(String publicKey, String plainText) {
////        try {
////            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
////            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes(ENCODING));
////            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
////            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
////            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
////            byte[] enBytes = cipher.doFinal(plainText.getBytes());
////            return new String(Base64.encodeBase64(enBytes));
////        } catch (Exception e) {
////            LOGGER.error("rsa encrypt exception: {}", e.getMessage(), e);
////        }
////        return null;
////    }
//    public static String encrypt(String publicKey, String plainText) {
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
////            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes(ENCODING));
//            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes());
//            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
//            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//            byte[] enBytes = cipher.doFinal(plainText.getBytes());
//            return new String(Base64.encodeBase64(enBytes));
//        } catch (Exception e) {
//            LOGGER.error("rsa encrypt exception: {}", e.getMessage(), e);
//        }
//        return null;
//    }
//
//    /**
//     * 使用私钥对明文密文进行解密
//     *
//     * @param privateKey
//     * @param enStr
//     * @return
//     */
//    public static String decrypt(String privateKey, String enStr) {
//        try {
//            PrivateKey priKey = getPrivateKey(privateKey);
//            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, priKey);
//            byte[] deBytes = cipher.doFinal(Base64.decodeBase64(enStr));
//            return new String(deBytes);
//        } catch (Exception e) {
//            LOGGER.error("rsa decrypt exception: {}", e.getMessage(), e);
//        }
//        return null;
//    }
//
//    /**
//     * RSA私钥签名
//     *
//     * @param content    待签名数据
//     * @param privateKey 私钥
//     * @return 签名值
//     */
//    public static String signByPrivateKey(String content, String privateKey) {
//        try {
//            PrivateKey priKey = getPrivateKey(privateKey);
//            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
//            signature.initSign(priKey);
//            signature.update(content.getBytes(ENCODING));
//            byte[] signed = signature.sign();
//            return new String((signed), ENCODING);
//        } catch (Exception e) {
//            LOGGER.error("sign error, content: {}", content, e);
//        }
//        return null;
//    }
//
//    /**
//     * 公钥验签
//     * @param content
//     * @param sign
//     * @param publicKey
//     * @return
//     */
//    public static boolean verifySignByPublicKey(String content, String sign, String publicKey) {
//        try {
//            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//            byte[] encodedKey = Base64.decodeBase64(publicKey.getBytes(ENCODING));
//            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
//
//            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
//            signature.initVerify(pubKey);
//            signature.update(content.getBytes(ENCODING));
//
//            return signature.verify((sign.getBytes(ENCODING)));
//
//        } catch (Exception e) {
//            LOGGER.error("verify sign error, content: {}, sign: {}", content, sign, e);
//        }
//        return false;
//    }
//}
