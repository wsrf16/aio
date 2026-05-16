package com.aio.portable.swiss.suite.algorithm.crypto.sm;

import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSAKeyPair;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPrivateKey;
import org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class SM2Sugar {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static RSAKeyPair generateSM2KeyPair() {
        try {
            SecureRandom secureRandom = new SecureRandom();
            ECGenParameterSpec sm2Spec = new ECGenParameterSpec("sm2p256v1");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", new BouncyCastleProvider());
            keyPairGenerator.initialize(sm2Spec);
            keyPairGenerator.initialize(sm2Spec, secureRandom);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            BCECPublicKey rsaPubKey = (BCECPublicKey) keyPair.getPublic();
            BCECPrivateKey rsaPriKey = (BCECPrivateKey) keyPair.getPrivate();
            RSAKeyPair rsaKeyPair = new RSAKeyPair(rsaPubKey, rsaPriKey);
            return rsaKeyPair;

        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static final String EC = "EC";
    public static final KeyFactory KEY_FACTORY = keyFactoryInstance();


    private static KeyFactory keyFactoryInstance() {
        try {
            return KeyFactory.getInstance(EC, new BouncyCastleProvider());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getPublicKey
     * @param publicKey
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) {
        try{
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            return KEY_FACTORY.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * getPrivateKey
     * @param privateKey
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) {
        try{
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            return KEY_FACTORY.generatePrivate(pkcs8EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }






    /**
     * 根据publicKey对原始数据data，使用SM2加密
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) {
        ECPublicKeyParameters localECPublicKeyParameters = null;

        if (publicKey instanceof BCECPublicKey) {
            BCECPublicKey localECPublicKey = (BCECPublicKey) publicKey;
            ECParameterSpec localECParameterSpec = localECPublicKey.getParameters();
            ECDomainParameters localECDomainParameters = new ECDomainParameters(localECParameterSpec.getCurve(),
                    localECParameterSpec.getG(), localECParameterSpec.getN());
            localECPublicKeyParameters = new ECPublicKeyParameters(localECPublicKey.getQ(), localECDomainParameters);
        }
        SM2Engine localSM2Engine = new SM2Engine();
        localSM2Engine.init(true, new ParametersWithRandom(localECPublicKeyParameters, new SecureRandom()));
        byte[] arrayOfByte2;
        try {
            arrayOfByte2 = localSM2Engine.processBlock(data, 0, data.length);
            return arrayOfByte2;
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encrypt(byte[] bytes, String publicKey) {
        PublicKey publicKeyObject = getPublicKey(publicKey);
        byte[] encrypt = encrypt(bytes, publicKeyObject);
        return JDKBase64Convert.encodeToString(encrypt);
    }

    public static String encrypt(String text, String publicKey) {
        byte[] bytes = text.getBytes();
        PublicKey publicKeyObject = getPublicKey(publicKey);
        byte[] encrypt = encrypt(bytes, publicKeyObject);
        return JDKBase64Convert.encodeToString(encrypt);
    }

    /**
     * 根据privateKey对加密数据bytes，使用SM2解密
     */
    public static byte[] decrypt(byte[] bytes, PrivateKey privateKey) {
        SM2Engine localSM2Engine = new SM2Engine();
        BCECPrivateKey sm2PriK = (BCECPrivateKey) privateKey;
        ECParameterSpec localECParameterSpec = sm2PriK.getParameters();
        ECDomainParameters localECDomainParameters = new ECDomainParameters(localECParameterSpec.getCurve(),
                localECParameterSpec.getG(), localECParameterSpec.getN());
        ECPrivateKeyParameters localECPrivateKeyParameters = new ECPrivateKeyParameters(sm2PriK.getD(),
                localECDomainParameters);
        localSM2Engine.init(false, localECPrivateKeyParameters);
        try {
            byte[] arrayOfByte3 = localSM2Engine.processBlock(bytes, 0, bytes.length);
            return arrayOfByte3;
        } catch (InvalidCipherTextException e) {
            throw new RuntimeException(e);
        }
    }

    public static String decrypt(String text, String privateKey) {
        byte[] bytes = JDKBase64Convert.decode(text);
        PrivateKey privateKeyObject = getPrivateKey(privateKey);
        byte[] decrypt = decrypt(bytes, privateKeyObject);
        return new String(decrypt);
    }

    public static String decrypt(byte[] bytes, String privateKey) {
        PrivateKey privateKeyObject = getPrivateKey(privateKey);
        byte[] decrypt = decrypt(bytes, privateKeyObject);
        return new String(decrypt);
    }

    /**
     * 私钥签名
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey) {
        try {
            Signature sig = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), BouncyCastleProvider.PROVIDER_NAME);
            sig.initSign(privateKey);
            sig.update(data);
            byte[] ret = sig.sign();
            return ret;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static String sign(String text, String privateKey) {
        byte[] bytes = text.getBytes();
        PrivateKey privateKeyObject = getPrivateKey(privateKey);
        byte[] sign = sign(bytes, privateKeyObject);
        return JDKBase64Convert.encodeToString(sign);
    }

    /**
     * 公钥验签
     */
    public static boolean verify(byte[] data, PublicKey publicKey, byte[] signature) {
        try {
            Signature sig = Signature.getInstance(GMObjectIdentifiers.sm2sign_with_sm3.toString(), BouncyCastleProvider.PROVIDER_NAME);
            sig.initVerify(publicKey);
            sig.update(data);
            boolean ret = sig.verify(signature);
            return ret;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verify(String text, String sign, String publicKey) {
        byte[] publicKeyBytes = JDKBase64Convert.decode(publicKey);
        byte[] bytes = text.getBytes();
        PublicKey publicKeyObject = getPublicKey(publicKey);
        byte[] signBytes = JDKBase64Convert.decode(sign);
        return verify(bytes, publicKeyObject, signBytes);
    }
}
