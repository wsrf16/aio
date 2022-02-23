package com.aio.portable.swiss.suite.security.authorization.jwt;

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
import java.util.Map;
//import com.nimbusds.jwt.JWTParser;

public class JWTTemplateType implements JWTAction, Base64TokenAlgorithm, JWTExplicitType {
    private JWTConfig jwtConfig;

    private Algorithm algorithm;

    @Override
    public synchronized Algorithm getAlgorithm() {
        return algorithm = algorithm == null ? newAlgorithm(jwtConfig) : algorithm;
    }

    @Override
    public Boolean getExplicit() {
        return this.jwtConfig.getExplicit();
    }

    public JWTTemplateType(JWTConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
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

    public final JWTCreator.Builder createBuilder() {
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

    public String sign(String issuer, int expireMinutes) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);

        JWTExpiredDate expiredDate = JWTExpiredDate.getExpiredMinutes(expireMinutes);
        builder.withIssuedAt(expiredDate.getIssuedAt());
        builder.withExpiresAt(expiredDate.getExpiresAt());
        return sign(builder);
    }

    public String sign(String issuer, Map<String, Object> addition) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);
        Map<String, Object> enhanced = explicit(addition);
        enhanced.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    public String sign(Map<String, Object> addition) {
        JWTCreator.Builder builder = createBuilder();
        Map<String, Object> enhanced = explicit(addition);
        enhanced.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    public String sign(Map<String, Object> addition, int expireMinutes) {
        JWTCreator.Builder builder = createBuilder();
        JWTExpiredDate expiredDate = JWTExpiredDate.getExpiredMinutes(expireMinutes);
        builder.withIssuedAt(expiredDate.getIssuedAt());
        builder.withExpiresAt(expiredDate.getExpiresAt());

        Map<String, Object> enhanced = explicit(addition);
        enhanced.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    public String sign(String issuer, Map<String, Object> addition, int expireMinutes) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);
        JWTExpiredDate expiredDate = JWTExpiredDate.getExpiredMinutes(expireMinutes);
        builder.withIssuedAt(expiredDate.getIssuedAt());
        builder.withExpiresAt(expiredDate.getExpiresAt());

        Map<String, Object> explicit = explicit(addition);
        explicit.entrySet().forEach(c -> {
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

    /**
     * parseForMap (be replaced of parseToMap)
     * @param token
     * @return
     */
    @Deprecated
    public Map<String, Object> parseForMap(String token) {
        return parseToMap(token);
    }

    public Map<String, Object> parseToMap(String token) {
        DecodedJWT parsed = this.parse(token);
        Map<String, Object> map = forMap(parsed);
        return map;
    }

    public <T> T parseTo(String token, String key) {
        DecodedJWT parsed = this.parse(token);
        Map<String, Object> map = forMap(parsed);
        return (T)map.get(key);
    }
}
