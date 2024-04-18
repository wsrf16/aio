package com.aio.portable.swiss.suite.algorithm.crypto.rsa;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;

import java.security.PrivateKey;
import java.security.PublicKey;

public class RSAKeyPair {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public RSAKeyPair(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPrivateKeyString() {
        return JDKBase64Convert.encodeToString(privateKey.getEncoded());
    }

    public String getPublicKeyString() {
        return JDKBase64Convert.encodeToString(publicKey.getEncoded());
    }
}
