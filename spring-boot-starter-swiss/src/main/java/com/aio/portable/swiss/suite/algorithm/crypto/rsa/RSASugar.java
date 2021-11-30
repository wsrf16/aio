package com.aio.portable.swiss.suite.algorithm.crypto.rsa;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSASugar {

    public final static RSAKeyPair generateRSAKeyPair() {
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


    private final static String RSA = "RSA";
    public final static String SHA1WithRSA = "SHA1WithRSA";
    private final static String X509 = "X.509";
//    public final static CertificateFactory CERTIFICATE_FACTORY = certificateFactoryInstance();

    public final static KeyFactory KEY_FACTORY = keyFactoryInstance();


    private final static KeyFactory keyFactoryInstance() {
        try {
            return KeyFactory.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    private final static CertificateFactory certificateFactoryInstance() {
//        try {
//            return CertificateFactory.getInstance(X509);
//        } catch (CertificateException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }


    public final static PrivateKey getPrivateKey(byte[] privateKeyBytes) {
        try {
            return KEY_FACTORY.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static PrivateKey getPrivateKey(String privateKey) {
        byte[] keyBytes = JDKBase64Convert.decode(privateKey.getBytes());
        return getPrivateKey(keyBytes);
    }

    public final static PublicKey getPublicKey(byte[] publicKeyBytes) {
        try {
            return KEY_FACTORY.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

//    public final static PublicKey getPublicKey1(byte[] publicKeyBytes) {
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

    public final static PublicKey getPublicKey(String publicKey) {
        byte[] keyBytes = JDKBase64Convert.decode(publicKey.getBytes());
        return getPublicKey(keyBytes);
    }

    public final static byte[] encrypt(byte[] bytes, byte[] publicKeyBytes) {
        try {
            PublicKey publicKey = KEY_FACTORY.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | BadPaddingException
                | InvalidKeySpecException
                | IllegalBlockSizeException
                e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String encrypt(String text, String publicKey) {
        byte[] publicKeyBytes = JDKBase64Convert.decode(publicKey);
        byte[] bytes = text.getBytes();
        byte[] encrypt = encrypt(bytes, publicKeyBytes);
        return JDKBase64Convert.encodeToString(encrypt);
    }

    public final static byte[] decrypt(byte[] bytes, byte[] privateKeyBytes) {
        try {
            PrivateKey privateKey = getPrivateKey(privateKeyBytes);
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(bytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String decrypt(String text, String privateKey) {
        byte[] privateKeyBytes = JDKBase64Convert.decode(privateKey);
        byte[] bytes = JDKBase64Convert.decode(text);
        byte[] encrypt = decrypt(bytes, privateKeyBytes);
        return new String(encrypt);
    }

    public final static byte[] sign(byte[] bytes, byte[] privateKeyBytes) {
        PrivateKey privateKey = getPrivateKey(privateKeyBytes);
        try {
            Signature signature = Signature.getInstance(SHA1WithRSA);
            signature.initSign(privateKey);
            signature.update(bytes);
            byte[] signed = signature.sign();
            return signed;
        } catch (NoSuchAlgorithmException|InvalidKeyException|SignatureException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public final static String sign(String text, String privateKey) {
        byte[] privateKeyBytes = JDKBase64Convert.decode(privateKey);
        byte[] bytes = text.getBytes();
        byte[] sign = sign(bytes, privateKeyBytes);
        return JDKBase64Convert.encodeToString(sign);
    }

    public static boolean verify(String text, String sign, String publicKey) {
        byte[] publicKeyBytes = JDKBase64Convert.decode(publicKey);
        byte[] bytes = text.getBytes();
        byte[] signBytes = JDKBase64Convert.decode(sign);
        return verify(bytes, signBytes, publicKeyBytes);
    }

    public static boolean verify(byte[] text, byte[] sign, byte[] publicKeyBytes) {
        try {
            Signature signature = Signature.getInstance(SHA1WithRSA);
            PublicKey publicKey = getPublicKey(publicKeyBytes);
            signature.initVerify(publicKey);
            signature.update(text);
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException|InvalidKeyException|SignatureException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
