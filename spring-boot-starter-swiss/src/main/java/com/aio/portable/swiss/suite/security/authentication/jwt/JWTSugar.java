package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.cipher.AlgorithmSugar;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Date;

public abstract class JWTSugar {
    public final static String AUTHORIZATION_HEAD = HttpHeaders.AUTHORIZATION;


    /**
     * sign
     * @param builder JWT.create().xx().xx()
     * @param algorithm
     * @return
     */
    public final static String sign(JWTCreator.Builder builder, Algorithm algorithm) {
        return builder.sign(algorithm);
    }


//    /**
//     * parseByHMAC
//     * @param token
//     * @param secret
//     * @return
//     */
//    public final static DecodedJWT parseByHMAC256(String token, String secret) {
//        Algorithm algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC256, secret);
//        DecodedJWT jwt = parse(token, algorithm);
//        return jwt;
//    }
//
//
//
//    /**
//     * parseByHMAC
//     * @param token
//     * @param hmac
//     * @param secret
//     * @return
//     */
//    public final static DecodedJWT parseByHMAC(String token, AlgorithmSugar.HMAC hmac, String secret) {
//        Algorithm algorithm = AlgorithmSugar.newHMAC(hmac, secret);
//        DecodedJWT jwt = parse(token, algorithm);
//        return jwt;
//    }
//
//
//    /**
//     * parseByECDSA
//     * @param ecdsa
//     * @param token
//     * @param publicKey
//     * @param privateKey
//     * @return
//     */
//    public final static DecodedJWT parseByECDSA(AlgorithmSugar.ECDSA ecdsa, String token, ECPublicKey publicKey, ECPrivateKey privateKey) {
//        Algorithm algorithm = AlgorithmSugar.newECDSA(ecdsa, publicKey, privateKey);
//        DecodedJWT jwt = parse(token, algorithm);
//        return jwt;
//    }
//
//
//    /**
//     * parseByRSA
//     * @param token
//     * @param rsa
//     * @param publicKey
//     * @param privateKey
//     * @return
//     */
//    public final static DecodedJWT parseByRSA(String token, AlgorithmSugar.RSA rsa, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
//        Algorithm algorithm = AlgorithmSugar.newRSA(rsa, publicKey, privateKey);
//        DecodedJWT jwt = parse(token, algorithm);
//        return jwt;
//    }


    /**
     * newDecodedJWT
     * @param token
     * @param algorithm
     * @return
     */
    public final static DecodedJWT parse(String token, Algorithm algorithm) {
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



//    public static class Classic {
//        /**
//         * signForTokenByHMAC256
//         * @param builder JWT.create()
//         * @param secret
//         * @return
//         */
//        public final static String signForTokenByHMAC256(JWTCreator.Builder builder, String secret) {
//            Algorithm algorithm = AlgorithmSugar.newHMAC(AlgorithmSugar.HMAC.HMAC256, secret);
//            String token = JWTSugar.sign(builder, algorithm);
//            return token;
//        }
//
//        /**
//         * signForTokenByRSA256
//         * @param builder
//         * @param publicKey
//         * @param privateKey
//         * @return
//         */
//        public final static String signForTokenByRSA256(JWTCreator.Builder builder, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
//            Algorithm algorithm = AlgorithmSugar.newRSA(AlgorithmSugar.RSA.RSA256, publicKey, privateKey);
//            String token = JWTSugar.sign(builder, algorithm);
//            return token;
//        }
//
//        /**
//         * parseTokenByHMAC256
//         * @param token
//         * @param secret
//         * @return
//         */
//        public final static DecodedJWT parseTokenByHMAC256(String token, String secret) {
//            return JWTSugar.parseByHMAC(token, AlgorithmSugar.HMAC.HMAC256, secret);
//        }
//
//        /**
//         * parseTokenByRSA256
//         * @param token
//         * @param publicKey
//         * @param privateKey
//         * @return
//         */
//        public final static DecodedJWT parseTokenByRSA256(String token, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
//            return JWTSugar.parseByRSA(token, AlgorithmSugar.RSA.RSA256, publicKey, privateKey);
//        }
//
//
//        /**
//         * verifyByHMAC256
//         * @param token
//         * @param secret
//         * @return
//         */
//        public static boolean verifyByHMAC256(String token, String secret) {
//            DecodedJWT parse;
//            Boolean verify;
//            try {
//                parse = Classic.parseTokenByHMAC256(token, secret);
//                if (parse != null)
//                    // TokenExpiredException
//                    verify = isExpired(parse.getExpiresAt());
//                else
//                    verify = true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                verify = false;
//            }
//
//            return verify;
//        }
//
//
//    }


    public final static Date getExpiredMinutes(Calendar calendar, int minutes) {
        return DateTimeSugar.CalendarUtils.add(calendar, Calendar.MINUTE, minutes).getTime();
    }

    public final static Date getExpiredMinutes(Calendar calendar, int calendarField, int mount) {
        return DateTimeSugar.CalendarUtils.add(calendar, calendarField, mount).getTime();
    }

    public final static boolean isExpired(Date expiredDate) {
        return isExpired(expiredDate, new Date());
    }

    public final static boolean isExpired(Date expiredDate, Date now) {
        return now.after(expiredDate) ? false : true;
    }

    public final static Date now(Calendar calendar) {
        return calendar.getTime();
    }
}
