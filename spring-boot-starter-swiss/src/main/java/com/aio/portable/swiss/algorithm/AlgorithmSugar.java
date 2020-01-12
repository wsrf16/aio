package com.aio.portable.swiss.algorithm;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

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

    public final static Algorithm newHMAC(String secret, HMAC hmac) {
        Algorithm algorithm;
        switch (hmac) {
            case HMAC256:
                algorithm = Algorithm.HMAC256(secret);
                break;
            case HMAC384:
                algorithm = Algorithm.HMAC384(secret);
                break;
            case HMAC512:
                algorithm = Algorithm.HMAC512(secret);
                break;
            default:
                algorithm = Algorithm.HMAC256(secret);
                break;
        }

        return algorithm;
    }

    public final static Algorithm newRSA(RSAPublicKey publicKey, RSAPrivateKey privateKey, RSA rsa) {
        Algorithm algorithm;
        switch (rsa) {
            case RSA256:
                algorithm = Algorithm.RSA256(publicKey, privateKey);
                break;
            case RSA384:
                algorithm = Algorithm.RSA384(publicKey, privateKey);
                break;
            case RSA512:
                algorithm = Algorithm.RSA512(publicKey, privateKey);
                break;
            default:
                algorithm = Algorithm.RSA256(publicKey, privateKey);
                break;
        }

        return algorithm;
    }


    public final static Algorithm newECDSA(ECPublicKey publicKey, ECPrivateKey privateKey, ECDSA ecdsa) {
        Algorithm algorithm;
        switch (ecdsa) {
            case ECDSA256:
                algorithm = Algorithm.ECDSA256(publicKey, privateKey);
                break;
            case ECDSA384:
                algorithm = Algorithm.ECDSA384(publicKey, privateKey);
                break;
            case ECDSA512:
                algorithm = Algorithm.ECDSA512(publicKey, privateKey);
                break;
            default:
                algorithm = Algorithm.ECDSA256(publicKey, privateKey);
                break;
        }

        return algorithm;
    }


}
