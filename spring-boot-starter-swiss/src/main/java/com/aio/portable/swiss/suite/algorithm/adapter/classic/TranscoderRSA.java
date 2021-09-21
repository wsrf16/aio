package com.aio.portable.swiss.suite.algorithm.adapter.classic;

import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSAKeyPair;
import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSASugar;
import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.algorithm.adapter.Transcoder;

import java.nio.charset.Charset;

public class TranscoderRSA implements Transcoder {
    String privateKey;
    String publicKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public TranscoderRSA() {
        RSAKeyPair rsaKeyPair = RSASugar.generateRSAKeyPair();
        this.privateKey = JDKBase64Convert.encodeToString(rsaKeyPair.getPrivateKey().getEncoded());
        this.publicKey = JDKBase64Convert.encodeToString(rsaKeyPair.getPublicKey().getEncoded());
    }

    public TranscoderRSA(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    @Override
    public String encode(String plain, Charset charset) {
        return new String(RSASugar.encrypt(plain.getBytes(charset), publicKey.getBytes(charset)));
    }

    @Override
    public String decode(String cipher, Charset charset) {
        return new String(RSASugar.decrypt(cipher.getBytes(charset), privateKey.getBytes(charset)));
    }

    @Override
    public String encode(String plain) {
        return RSASugar.encrypt(plain, publicKey);
    }

    @Override
    public String decode(String cipher) {
        return RSASugar.decrypt(cipher, privateKey);
    }



}
