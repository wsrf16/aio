package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.factories.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.cipher.AlgorithmSugar;
import com.aio.portable.swiss.suite.algorithm.cipher.RSASugar;
import com.aio.portable.swiss.suite.algorithm.transcode.Transcoder;
import com.aio.portable.swiss.suite.algorithm.transcode.classic.TranscoderBase64;
import com.aio.portable.swiss.suite.algorithm.transcode.Transcodable;
import com.aio.portable.swiss.suite.security.authentication.jwt.encryption.TokenAlgorithm;
import com.auth0.jwt.JWT;
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

public class JWTSession implements JWTAction, Transcodable, TokenAlgorithm {
    private JWTProperties jwtProperties;

    private Transcoder transcoder = new TranscoderBase64();

    private Algorithm algorithm;

    public JWTProperties getJwtProperties() {
        return jwtProperties;
    }

    public void setTranscoder(Transcoder transcoder) {
        this.transcoder = transcoder;
    }

    @Override
    public Transcoder transcoder() {
        return transcoder;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    @Override
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public JWTSession(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        Algorithm algorithm;
        switch (jwtProperties.getSignAlgorithm().toUpperCase()) {
            case "RSA256":
                algorithm = AlgorithmSugar.newRSA(AlgorithmSugar.RSA.RSA256,
                        (RSAPublicKey) RSASugar.getPublicKey(jwtProperties.getPublicKey()),
                        (RSAPrivateKey) RSASugar.getPrivateKey(jwtProperties.getPrivateKey())
                        );
                break;
            case "RSA384":
                algorithm = AlgorithmSugar.newRSA(AlgorithmSugar.RSA.RSA384,
                        (RSAPublicKey) RSASugar.getPublicKey(jwtProperties.getPublicKey()),
                        (RSAPrivateKey) RSASugar.getPrivateKey(jwtProperties.getPrivateKey())
                );
                break;
            case "RSA512":
                algorithm = AlgorithmSugar.newRSA(AlgorithmSugar.RSA.RSA512,
                        (RSAPublicKey) RSASugar.getPublicKey(jwtProperties.getPublicKey()),
                        (RSAPrivateKey) RSASugar.getPrivateKey(jwtProperties.getPrivateKey())
                );
                break;
            case "ECDSA256":
                algorithm = AlgorithmSugar.newECDSA(AlgorithmSugar.ECDSA.ECDSA256,
                        (ECPublicKey) RSASugar.getPublicKey(jwtProperties.getPublicKey()),
                        (ECPrivateKey) RSASugar.getPrivateKey(jwtProperties.getPrivateKey())
                );
                break;
            case "ECDSA384":
                algorithm = AlgorithmSugar.newECDSA(AlgorithmSugar.ECDSA.ECDSA384,
                        (ECPublicKey) RSASugar.getPublicKey(jwtProperties.getPublicKey()),
                        (ECPrivateKey) RSASugar.getPrivateKey(jwtProperties.getPrivateKey())
                );
                break;
            case "ECDSA512":
                algorithm = AlgorithmSugar.newECDSA(AlgorithmSugar.ECDSA.ECDSA512,
                        (ECPublicKey) RSASugar.getPublicKey(jwtProperties.getPublicKey()),
                        (ECPrivateKey) RSASugar.getPrivateKey(jwtProperties.getPrivateKey())
                );
                break;
            case "HMAC384":
                algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC384, jwtProperties.getSecret());
                break;
            case "HMAC512":
                algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC512, jwtProperties.getSecret());
                break;
            case "HMAC256":
            default:
                algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC256, jwtProperties.getSecret());
                break;
        }
        setAlgorithm(algorithm);
    }

    public JWTCreator.Builder createBuilder() {
        JWTCreator.Builder builder = JWT.create();
        if (jwtProperties.getKeyId() != null)
            builder.withKeyId(jwtProperties.getKeyId());
        if (jwtProperties.getIssuer() != null)
            builder.withIssuer(jwtProperties.getIssuer());
        if (jwtProperties.getSubject() != null)
            builder.withSubject(jwtProperties.getSubject());
        if (jwtProperties.getAudience() != null)
            builder.withAudience(jwtProperties.getAudience());
        if (jwtProperties.getNotBefore() != null)
            builder.withNotBefore(jwtProperties.getNotBefore());
        if (jwtProperties.getJWTId() != null)
            builder.withJWTId(jwtProperties.getJWTId());
        final JWTDate jwtDate = this.generateDate();
        if (jwtDate.isValid()) {
            builder.withIssuedAt(jwtDate.getIssuedAt());
            builder.withExpiresAt(jwtDate.getExpiresAt());
        }

        return builder;
    }

    public JWTDate generateDate() {
        final JWTDate jwtDate = new JWTDate();
        Calendar now = DateTimeSugar.CalendarUtils.now();
        if (now.getTime() != null) {
            Date issuedAt = now.getTime();
            jwtDate.issuedAt = issuedAt;
        }
        if (jwtProperties.getExpiredMinutes() != null) {
            Date expiresAt = JWTSugar.getExpiredMinutes(now, jwtProperties.getExpiredMinutes());
            jwtDate.expiresAt = expiresAt;
        }
        return jwtDate;
    }

    public static class JWTDate {
        private Date issuedAt;
        private Date expiresAt;

        public Date getIssuedAt() {
            return issuedAt;
        }

        public Date getExpiresAt() {
            return expiresAt;
        }

        public boolean isValid() {
            return issuedAt != null && expiresAt != null;
        }
    }



    @Override
    public String sign(JWTCreator.Builder builder) {
        String token = encode(builder, algorithm);
        String encodedToken = transcoder().encode(token);
        return encodedToken;
    }

    public String sign() {
        JWTCreator.Builder builder = createBuilder();
        return sign(builder);
    }

    public String sign(String issuer) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);
        return sign(builder);
    }

    public String sign(String issuer, Map<String, Object> map) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);
        map.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    public String sign(String issuer, int expireMinutes, Map<String, Object> map) {
        JWTCreator.Builder builder = createBuilder();
        builder.withIssuer(issuer);
        Calendar now = DateTimeSugar.CalendarUtils.now();
        Date issuedAt = now.getTime();
        builder.withIssuedAt(issuedAt);
        Date expiresAt = JWTSugar.getExpiredMinutes(now, expireMinutes);
        builder.withExpiresAt(expiresAt);

        map.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    @Override
    public Boolean validate(String encodedToken) {
        String decodedToken = transcoder().decode(encodedToken);
        return verify(decodedToken, algorithm);
    }

    @Override
    public DecodedJWT parse(String encodedToken) {
        String decodedToken = transcoder().decode(encodedToken);
        DecodedJWT parse = parse(decodedToken, algorithm);
        return parse;
    }
}
