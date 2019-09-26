package com.aio.portable.swiss.assist.jwt;

import com.aio.portable.swiss.algorithm.AlgorithmWorld;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public abstract class JWTWorld {
    /**
     * sign
     * @param builder JWT.create().xx().xx()
     * @param algorithm
     * @return
     */
    public final static String sign(JWTCreator.Builder builder, Algorithm algorithm) {
        return builder.sign(algorithm);
    }

    /**
     * parseByHMAC
     * @param token
     * @param secret
     * @param hmac
     * @return
     */
    public final static DecodedJWT parseByHMAC(String token, String secret, AlgorithmWorld.HMAC hmac) {
        Algorithm algorithm = AlgorithmWorld.newHMAC(secret, hmac);
        DecodedJWT jwt = newDecodedJWT(token, algorithm);
        return jwt;
    }

    /**
     * parseByECDSA
     * @param token
     * @param publicKey
     * @param privateKey
     * @param ecdsa
     * @return
     */
    public final static DecodedJWT parseByECDSA(String token, ECPublicKey publicKey, ECPrivateKey privateKey, AlgorithmWorld.ECDSA ecdsa) {
        Algorithm algorithm = AlgorithmWorld.newECDSA(publicKey, privateKey, ecdsa);
        DecodedJWT jwt = newDecodedJWT(token, algorithm);
        return jwt;
    }

    /**
     * parseByRSA
     * @param token
     * @param publicKey
     * @param privateKey
     * @param rsa
     * @return
     */
    public final static DecodedJWT parseByRSA(String token, RSAPublicKey publicKey, RSAPrivateKey privateKey, AlgorithmWorld.RSA rsa) {
        Algorithm algorithm = AlgorithmWorld.newRSA(publicKey, privateKey, rsa);
        DecodedJWT jwt = newDecodedJWT(token, algorithm);
        return jwt;
    }


    /**
     * newDecodedJWT
     * @param token
     * @param algorithm
     * @return
     */
    private final static DecodedJWT newDecodedJWT(String token, Algorithm algorithm) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
//        return verifier.verify(new String(Base64.getDecoder().decode(token), StandardCharsets.UTF_8));
    }
}
