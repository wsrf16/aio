package com.aio.portable.swiss.suite.algorithm.crypto.rsa;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSASugar {
    private static final int MAX_DECRYPT_LENGTH = 128;
    private static final int MAX_ENCRYPT_LENGTH = 117;

    public static final RSAKeyPair generateRSAKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
            KeyPair keyPair = generator.generateKeyPair();
            RSAPublicKey rsaPubKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey rsaPriKey = (RSAPrivateKey) keyPair.getPrivate();
            RSAKeyPair rsaKeyPair = new RSAKeyPair(rsaPubKey, rsaPriKey);
            return rsaKeyPair;
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    private static final String RSA = "RSA";
    public static final String SHA1WithRSA = "SHA1WithRSA";
    private static final String X509 = "X.509";
//    public static final CertificateFactory CERTIFICATE_FACTORY = certificateFactoryInstance();

    public static final KeyFactory KEY_FACTORY = keyFactoryInstance();


    private static final KeyFactory keyFactoryInstance() {
        try {
            return KeyFactory.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    private static final CertificateFactory certificateFactoryInstance() {
//        try {
//            return CertificateFactory.getInstance(X509);
//        } catch (CertificateException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


    public static final PrivateKey getPrivateKey(byte[] privateKeyBytes) {
        try {
            return KEY_FACTORY.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static final PrivateKey getPrivateKey(String privateKey) {
        byte[] keyBytes = JDKBase64Convert.decode(privateKey);
        return getPrivateKey(keyBytes);
    }

    public static final PublicKey getPublicKey(byte[] publicKeyBytes) {
        try {
            return KEY_FACTORY.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public static final PublicKey getPublicKey1(byte[] publicKeyBytes) {
//        try (InputStream in = new ByteArrayInputStream(publicKeyBytes)) {
//            PublicKey publicKey = CERTIFICATE_FACTORY
//                    .generateCertificate(in)
//                    .getPublicKey();
//            return publicKey;
//        } catch (IOException | CertificateException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    public static final PublicKey getPublicKey(String publicKey) {
        byte[] keyBytes = JDKBase64Convert.decode(publicKey.getBytes());
        return getPublicKey(keyBytes);
    }

    public static final byte[] encrypt(byte[] bytes, Key key) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key);
//            return cipher.doFinal(bytes);

            int length = bytes.length;
            int offset = 0;
            byte[] cache;
            while (length - offset > 0) {
                if (length - offset > MAX_ENCRYPT_LENGTH) {
                    cache = cipher.doFinal(bytes, offset, MAX_ENCRYPT_LENGTH);
                } else {
                    cache = cipher.doFinal(bytes, offset, length - offset);
                }
                out.write(cache, 0, cache.length);
                offset = offset + MAX_ENCRYPT_LENGTH;
            }
            return out.toByteArray();
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException
                | IOException
                e) {
            throw new RuntimeException(e);
        }
    }

    public static final byte[] encrypt(byte[] bytes, byte[] publicKeyBytes) {
        PublicKey publicKey = getPublicKey(publicKeyBytes);
        return encrypt(bytes, publicKey);
    }

//    public static final String encrypt(String text, Key key) {
//        byte[] bytes = text.getBytes();
//        byte[] encrypt = encrypt(bytes, key);
//        return JDKBase64Convert.encodeToString(encrypt);
//    }

    public static final String encrypt(String text, String publicKey) {
        byte[] bytes = text.getBytes();
        byte[] publicKeyBytes = JDKBase64Convert.decode(publicKey);
        byte[] encrypt = encrypt(bytes, publicKeyBytes);
        return JDKBase64Convert.encodeToString(encrypt);
    }

    public static final byte[] decrypt(byte[] bytes, Key key) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, key);
//            return cipher.doFinal(bytes);

            int length = bytes.length;
            int offset = 0;
            byte[] cache;
            while (length - offset > 0) {
                if (length - offset > MAX_DECRYPT_LENGTH) {
                    cache = cipher.doFinal(bytes, offset, MAX_DECRYPT_LENGTH);
                } else {
                    cache = cipher.doFinal(bytes, offset, length - offset);
                }
                out.write(cache, 0, cache.length);
                offset = offset + MAX_DECRYPT_LENGTH;
            }
            return out.toByteArray();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | IOException e
        ) {
            throw new RuntimeException(e);
        }
    }

    public static final byte[] decrypt(byte[] bytes, byte[] privateKeyBytes) {
        PrivateKey privateKey = getPrivateKey(privateKeyBytes);
        return decrypt(bytes, privateKey);
    }

//    public static final String decrypt(String text, Key key) {
//        byte[] bytes = JDKBase64Convert.decode(text);
//        byte[] decrypt = decrypt(bytes, key);
//        return new String(decrypt);
//    }

    public static final String decrypt(String text, String privateKey) {
        byte[] bytes = JDKBase64Convert.decode(text);
        byte[] privateKeyBytes = JDKBase64Convert.decode(privateKey);
        byte[] decrypt = decrypt(bytes, privateKeyBytes);
        return new String(decrypt);
    }

    public static final byte[] sign(byte[] bytes, PrivateKey key) {
        try {
            Signature signature = Signature.getInstance(SHA1WithRSA);
            signature.initSign(key);
            signature.update(bytes);
            byte[] signed = signature.sign();
            return signed;
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static final byte[] sign(byte[] bytes, byte[] privateKeyBytes) {
        PrivateKey privateKey = getPrivateKey(privateKeyBytes);
        return sign(bytes, privateKey);
    }

    public static final String sign(String text, String privateKey) {
        byte[] privateKeyBytes = JDKBase64Convert.decode(privateKey);
        byte[] bytes = text.getBytes();
        byte[] sign = sign(bytes, privateKeyBytes);
        return JDKBase64Convert.encodeToString(sign);
    }

    public static boolean verify(byte[] text, byte[] sign, PublicKey publicKey) {
        try {
            Signature signature = Signature.getInstance(SHA1WithRSA);
            signature.initVerify(publicKey);
            signature.update(text);
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException|InvalidKeyException|SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String text, String sign, String publicKey) {
        byte[] publicKeyBytes = JDKBase64Convert.decode(publicKey);
        byte[] bytes = text.getBytes();
        byte[] signBytes = JDKBase64Convert.decode(sign);
        return verify(bytes, signBytes, publicKeyBytes);
    }

    public static boolean verify(byte[] text, byte[] sign, byte[] publicKeyBytes) {
        PublicKey publicKey = getPublicKey(publicKeyBytes);
        return verify(text, sign, publicKey);
    }

}
