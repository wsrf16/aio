package com.aio.portable.swiss.suite.net.protocol.http;

import com.aio.portable.swiss.autoconfigure.properties.JWTClaims;
import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.cipher.AlgorithmSugar;
import com.aio.portable.swiss.suite.algorithm.cipher.CipherSugar;
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
import java.util.Calendar;
import java.util.Date;

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
     * @return
     */
    public final static DecodedJWT parseByHMAC256(String token, String secret) {
        Algorithm algorithm = AlgorithmSugar.newHMAC(secret, AlgorithmSugar.HMAC.HMAC256);
        DecodedJWT jwt = parse(token, algorithm);
        return jwt;
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
        DecodedJWT jwt = parse(token, algorithm);
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
        DecodedJWT jwt = parse(token, algorithm);
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
        DecodedJWT jwt = parse(token, algorithm);
        return jwt;
    }


    /**
     * newDecodedJWT
     * @param token
     * @param algorithm
     * @return
     */
    private final static DecodedJWT parse(String token, Algorithm algorithm) {
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
            parse(token, algorithm);
            verify = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            verify = false;
        }
        return verify;
    }

    public final static JWTCreator.Builder createJWTBuilder(JWTClaims jwtClaims) {
        JWTCreator.Builder builder = JWT.create();
//        if (jwtProperties.getKeyId() != null)
//            builder.withKeyId(jwtProperties.getKeyId());
        if (jwtClaims.getIssuer() != null)
            builder.withIssuer(jwtClaims.getIssuer());
        if (jwtClaims.getSubject() != null)
            builder.withSubject(jwtClaims.getSubject());
        if (jwtClaims.getAudience() != null)
            builder.withAudience(jwtClaims.getAudience());
        if (jwtClaims.getExpiresAt() != null)
            builder.withExpiresAt(jwtClaims.getExpiresAt());
        if (jwtClaims.getNotBefore() != null)
            builder.withNotBefore(jwtClaims.getNotBefore());
        if (jwtClaims.getIssuedAt() != null)
            builder.withIssuedAt(jwtClaims.getIssuedAt());
        if (jwtClaims.getJWTId() != null)
            builder.withJWTId(jwtClaims.getJWTId());
        return builder;
    }

    public final static JWTCreator.Builder createJWTBuilderWithIssuer(JWTClaims jwtClaims,
                                                                      String issuer) {
        JWTCreator.Builder builder = JWT.create();
//        if (jwtProperties.getKeyId() != null)
//            builder.withKeyId(jwtProperties.getKeyId());
        if (jwtClaims.getIssuer() != null)
            builder.withIssuer(issuer);
        if (jwtClaims.getSubject() != null)
            builder.withSubject(jwtClaims.getSubject());
        if (jwtClaims.getAudience() != null)
            builder.withAudience(jwtClaims.getAudience());
        if (jwtClaims.getExpiresAt() != null)
            builder.withExpiresAt(jwtClaims.getExpiresAt());
        if (jwtClaims.getNotBefore() != null)
            builder.withNotBefore(jwtClaims.getNotBefore());
        if (jwtClaims.getIssuedAt() != null)
            builder.withIssuedAt(jwtClaims.getIssuedAt());
        if (jwtClaims.getJWTId() != null)
            builder.withJWTId(jwtClaims.getJWTId());
        return builder;
    }





    public static class Classic {
        /**
         * generateHMAC256Token
         * @param builder JWT.create()
         * @param secret
         * @return
         */
        public final static String signForBase64TokenByHMAC256(JWTCreator.Builder builder, String secret) {
            Algorithm algorithm = AlgorithmSugar.newHMAC(secret, AlgorithmSugar.HMAC.HMAC256);
            String token = JWTSugar.sign(builder, algorithm);
            token = CipherSugar.JavaUtil.encodeBase64(token, StandardCharsets.UTF_8);
            return token;
        }

        /**
         * generateRSA256Token
         * @param userName
         * @param publicKey
         * @param privateKey
         * @param days
         * @return
         */
        public final static String signForRSA256Token(String userName, RSAPublicKey publicKey, RSAPrivateKey privateKey, int days) {
            Calendar calendar = Calendar.getInstance();
            Date now = now(calendar);
            Date expired = getExpiredDate(calendar, days);
            Algorithm algorithm = AlgorithmSugar.newRSA(publicKey, privateKey, AlgorithmSugar.RSA.RSA256);
            JWTCreator.Builder builder = com.auth0.jwt.JWT
                    .create()
                    .withIssuer(userName)
                    .withIssuedAt(now)
                    .withExpiresAt(expired);

            String token = JWTSugar.sign(builder, algorithm);
            token = new String(Base64.getEncoder().encode(token.getBytes()), StandardCharsets.UTF_8);
            return token;
        }

        /**
         * parseByHMAC
         * @param token
         * @param secret
         * @return
         */
        public final static DecodedJWT parseBase64TokenByHMAC256(String token, String secret) {
            token = CipherSugar.JavaUtil.decodeBase64(token, StandardCharsets.UTF_8);
            return JWTSugar.parseByHMAC(token, secret, AlgorithmSugar.HMAC.HMAC256);
        }

//    public final static DecodedJWT parseByRSA256(String token, String secret) {
//        token = CipherSugar.JavaUtil.decode(token, StandardCharsets.UTF_8);
//        return JWTSugar.parseByRSA(token, secret, AlgorithmSugar.RSA.RSA256);
//    }


        /**
         * verifyByHMAC
         * @param token
         * @param secret
         * @return
         */
        public static boolean verifyByHMAC(String token, String secret) {
            DecodedJWT parse;
            Boolean verify;
            try {
                parse = Classic.parseBase64TokenByHMAC256(token, secret);
                if (parse != null)
                    verify = new Date().after(parse.getExpiresAt()) ? false : true;
                else
                    verify = true;
            } catch (Exception e) {
                e.printStackTrace();
                verify = false;
            }

            return verify;
        }


        public final static Date getExpiredDate(Calendar calendar, int hours) {
            return DateTimeSugar.CalendarUtils.add(calendar, Calendar.HOUR, hours).getTime();
        }

        public final static Date getExpiredDate(Calendar calendar, int calendarField, int mount) {
            return DateTimeSugar.CalendarUtils.add(calendar, calendarField, mount).getTime();
        }

        public final static Date now(Calendar calendar) {
            return calendar.getTime();
        }
    }
}
