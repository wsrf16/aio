package com.aio.portable.swiss.structure.net.protocol.http.jwt;

import com.aio.portable.swiss.algorithm.AlgorithmSugar;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public abstract class JWTSugar {
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
    public final static DecodedJWT parseByHMAC(String token, String secret, AlgorithmSugar.HMAC hmac) {
        Algorithm algorithm = AlgorithmSugar.newHMAC(secret, hmac);
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
    public final static DecodedJWT parseByECDSA(String token, ECPublicKey publicKey, ECPrivateKey privateKey, AlgorithmSugar.ECDSA ecdsa) {
        Algorithm algorithm = AlgorithmSugar.newECDSA(publicKey, privateKey, ecdsa);
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
    public final static DecodedJWT parseByRSA(String token, RSAPublicKey publicKey, RSAPrivateKey privateKey, AlgorithmSugar.RSA rsa) {
        Algorithm algorithm = AlgorithmSugar.newRSA(publicKey, privateKey, rsa);
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


    /**
     * verify
     * @param token
     * @param algorithm
     * @return
     */
    public final static Boolean verify(String token, Algorithm algorithm) {
        Boolean verify;
        try {
            newDecodedJWT(token, algorithm);
            verify = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            verify = false;
        }
        return verify;
    }


}
