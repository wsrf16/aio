package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.crypto.AlgorithmSugar;
import com.aio.portable.swiss.suite.algorithm.crypto.rsa.RSASugar;
import com.aio.portable.swiss.suite.security.authorization.jwt.encryption.Base64TokenAlgorithm;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
//import com.nimbusds.jwt.JWTParser;

public class JWTTemplate implements JWTAction, Base64TokenAlgorithm {
    private JWTConfig jwtConfig;

    private Algorithm algorithm;



    public JWTConfig getJwtConfig() {
        return jwtConfig;
    }

    @Override
    public Algorithm getAlgorithm() {
        return algorithm;
    }



    public JWTTemplate(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        this.algorithm = newAlgorithm(jwtConfig);
    }

    private static Algorithm newAlgorithm(JWTConfig jwtConfig) {
        Algorithm algorithm;
        String signAlgorithm = jwtConfig.getSignAlgorithm() == null ? "HMAC256" : jwtConfig.getSignAlgorithm().toUpperCase();
        switch (signAlgorithm) {
            case "RSA256":
                algorithm = AlgorithmSugar.newRSA(AlgorithmSugar.RSA.RSA256,
                        (RSAPublicKey) RSASugar.getPublicKey(jwtConfig.getPublicKey()),
                        (RSAPrivateKey) RSASugar.getPrivateKey(jwtConfig.getPrivateKey())
                        );
                break;
            case "RSA384":
                algorithm = AlgorithmSugar.newRSA(AlgorithmSugar.RSA.RSA384,
                        (RSAPublicKey) RSASugar.getPublicKey(jwtConfig.getPublicKey()),
                        (RSAPrivateKey) RSASugar.getPrivateKey(jwtConfig.getPrivateKey())
                );
                break;
            case "RSA512":
                algorithm = AlgorithmSugar.newRSA(AlgorithmSugar.RSA.RSA512,
                        (RSAPublicKey) RSASugar.getPublicKey(jwtConfig.getPublicKey()),
                        (RSAPrivateKey) RSASugar.getPrivateKey(jwtConfig.getPrivateKey())
                );
                break;
            case "ECDSA256":
                algorithm = AlgorithmSugar.newECDSA(AlgorithmSugar.ECDSA.ECDSA256,
                        (ECPublicKey) RSASugar.getPublicKey(jwtConfig.getPublicKey()),
                        (ECPrivateKey) RSASugar.getPrivateKey(jwtConfig.getPrivateKey())
                );
                break;
            case "ECDSA384":
                algorithm = AlgorithmSugar.newECDSA(AlgorithmSugar.ECDSA.ECDSA384,
                        (ECPublicKey) RSASugar.getPublicKey(jwtConfig.getPublicKey()),
                        (ECPrivateKey) RSASugar.getPrivateKey(jwtConfig.getPrivateKey())
                );
                break;
            case "ECDSA512":
                algorithm = AlgorithmSugar.newECDSA(AlgorithmSugar.ECDSA.ECDSA512,
                        (ECPublicKey) RSASugar.getPublicKey(jwtConfig.getPublicKey()),
                        (ECPrivateKey) RSASugar.getPrivateKey(jwtConfig.getPrivateKey())
                );
                break;
            case "HMAC384":
                algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC384, jwtConfig.getSecret());
                break;
            case "HMAC512":
                algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC512, jwtConfig.getSecret());
                break;
            case "HMAC256":
            default:
                algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC256, jwtConfig.getSecret());
                break;
        }
        return algorithm;
    }

    public JWTCreator.Builder createBuilder() {
        return jwtConfig.createBuilder();
    }

    @Override
    public String sign(JWTCreator.Builder builder) {
        String token = Base64TokenAlgorithm.super.sign(builder);
        return token;
    }

//    public String sign() {
//        JWTCreator.Builder builder = createBuilder();
//        return sign(builder);
//    }

    public String sign(String issuer) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);
        return sign(builder);
    }

    public String sign(String issuer, Map<String, Object> addition) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);
        addition.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    public String sign(Map<String, Object> addition) {
        JWTCreator.Builder builder = createBuilder();
        addition.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    public String sign(String issuer, int expireMinutes, Map<String, Object> addition) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);
        Calendar now = DateTimeSugar.CalendarUtils.now();
        Date issuedAt = now.getTime();
        builder.withIssuedAt(issuedAt);
        Date expiresAt = JWTSugar.getExpiredMinutes(now, expireMinutes);
        builder.withExpiresAt(expiresAt);

        addition.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    @Override
    public boolean validate(String token) {
        return Base64TokenAlgorithm.super.validate(token);
    }

    public boolean isExpired(String token) {
        return Base64TokenAlgorithm.super.isExpired(token);
    }

    @Override
    public DecodedJWT parse(String token) {
        return Base64TokenAlgorithm.super.parse(token);
    }
}
