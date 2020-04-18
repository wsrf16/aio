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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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



    public final static JWTCreator.Builder withClaim(JWTCreator.Builder builder, String name, Object value) {
        Class<JWTCreator.Builder> clazz = JWTCreator.Builder.class;
        try {
            Method method = clazz.getDeclaredMethod("addClaim", new Class[]{String.class, Object.class});
            method.setAccessible(true);
            Object invoke = method.invoke(builder, new Object[]{name, value});
            return builder;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    public static class Classic {
        /**
         * signForTokenByHMAC256
         * @param builder JWT.create()
         * @param secret
         * @return
         */
        public final static String signForTokenByHMAC256(JWTCreator.Builder builder, String secret) {
            Algorithm algorithm = AlgorithmSugar.newHMAC(secret, AlgorithmSugar.HMAC.HMAC256);
            String token = JWTSugar.sign(builder, algorithm);
            return token;
        }

        /**
         * signForTokenByRSA256
         * @param userName
         * @param publicKey
         * @param privateKey
         * @param days
         * @return
         */
        public final static String signForTokenByRSA256(String userName, RSAPublicKey publicKey, RSAPrivateKey privateKey, int days) {
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
         * parseTokenByHMAC256
         * @param token
         * @param secret
         * @return
         */
        public final static DecodedJWT parseTokenByHMAC256(String token, String secret) {
            return JWTSugar.parseByHMAC(token, secret, AlgorithmSugar.HMAC.HMAC256);
        }

    public final static DecodedJWT parseTokenByRSA256(String token, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
        return JWTSugar.parseByRSA(token, publicKey, privateKey, AlgorithmSugar.RSA.RSA256);
    }


        /**
         * verifyByHMAC256
         * @param token
         * @param secret
         * @return
         */
        public static boolean verifyByHMAC256(String token, String secret) {
            DecodedJWT parse;
            Boolean verify;
            try {
                parse = Classic.parseTokenByHMAC256(token, secret);
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
