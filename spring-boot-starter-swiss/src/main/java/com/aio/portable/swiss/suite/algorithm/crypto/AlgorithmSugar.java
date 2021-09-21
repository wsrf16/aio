package com.aio.portable.swiss.suite.algorithm.crypto;

import com.auth0.jwt.algorithms.Algorithm;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public abstract class AlgorithmSugar {
    public enum HMAC {
        HMAC256,
        HMAC384,
        HMAC512
    }

    public enum RSA {
        RSA256,
        RSA384,
        RSA512
    }

    public enum ECDSA {
        ECDSA256,
        ECDSA384,
        ECDSA512
    }

    public final static Algorithm newHMAC(HMAC hmac, String secret) {
        Algorithm algorithm;
        switch (hmac) {
            case HMAC384:
                algorithm = Algorithm.HMAC384(secret);
                break;
            case HMAC512:
                algorithm = Algorithm.HMAC512(secret);
                break;
            case HMAC256:
            default:
                algorithm = Algorithm.HMAC256(secret);
                break;
        }

        return algorithm;
    }

    public final static Algorithm newRSA(RSA rsa, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        Algorithm algorithm;
        switch (rsa) {
            case RSA384:
                algorithm = Algorithm.RSA384(publicKey, privateKey);
                break;
            case RSA512:
                algorithm = Algorithm.RSA512(publicKey, privateKey);
                break;
            case RSA256:
            default:
                algorithm = Algorithm.RSA256(publicKey, privateKey);
                break;
        }

        return algorithm;
    }

    public final static Algorithm newECDSA(ECDSA ecdsa, ECPublicKey publicKey, ECPrivateKey privateKey) {
        Algorithm algorithm;
        switch (ecdsa) {
            case ECDSA384:
                algorithm = Algorithm.ECDSA384(publicKey, privateKey);
                break;
            case ECDSA512:
                algorithm = Algorithm.ECDSA512(publicKey, privateKey);
                break;
            case ECDSA256:
            default:
                algorithm = Algorithm.ECDSA256(publicKey, privateKey);
                break;
        }

        return algorithm;
    }




}
