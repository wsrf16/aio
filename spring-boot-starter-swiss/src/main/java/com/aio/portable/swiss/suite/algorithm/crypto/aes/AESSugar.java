package com.aio.portable.swiss.suite.algorithm.crypto.aes;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESSugar {
    private final static String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private final static String AES = "AES";


    public final static SecretKeySpec generateSecretKey(byte[] secretKeyBytes) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
            // 加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(secretKeyBytes);
            keyGenerator.init(128, random);
            SecretKey secretKey = keyGenerator.generateKey();

            byte[] secretKeyEncoded = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(secretKeyEncoded, AES);
            return key;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public final static byte[] encrypt(byte[] bytes, byte[] secretKeyBytes) {
        try {
            SecretKeySpec secretKey = generateSecretKey(secretKeyBytes);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(bytes);
            return result;
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public final static String encrypt(String plain, String password) {
        byte[] bytes = plain.getBytes();
        byte[] passwordBytes = password.getBytes();
        byte[] cipher = encrypt(bytes, passwordBytes);
        return JDKBase64Convert.encodeToString(cipher);
    }


    public final static byte[] decrypt(byte[] bytes, byte[] secretKeyBytes) {
        try {
            SecretKeySpec secretKey = generateSecretKey(secretKeyBytes);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] result = cipher.doFinal(bytes);
            return result;
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | InvalidKeyException
                | IllegalBlockSizeException
                | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public final static String decrypt(String cipherBase64, String password) {
        byte[] bytes = JDKBase64Convert.decode(cipherBase64);
        byte[] passwordBytes = password.getBytes();
        byte[] plain = decrypt(bytes, passwordBytes);
        return new String(plain);
    }

}
