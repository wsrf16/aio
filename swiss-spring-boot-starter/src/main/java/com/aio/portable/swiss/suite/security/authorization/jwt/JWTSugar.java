package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

public abstract class JWTSugar {
    public static final String HEAD_VALUE_AUTHORIZATION = HttpHeaders.AUTHORIZATION;
    public static final String HEAD_VALUE_BEAR = "Bearer";


    /**
     * sign
     * @param builder JWT.create().xx().xx()
     * @param algorithm
     * @return
     */
    public static final String sign(JWTCreator.Builder builder, Algorithm algorithm) {
        return builder.sign(algorithm);
    }


//    /**
//     * parseByHMAC
//     * @param token
//     * @param secret
//     * @return
//     */
//    public static final DecodedJWT parseByHMAC256(String token, String secret) {
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
//    public static final DecodedJWT parseByHMAC(String token, AlgorithmSugar.HMAC hmac, String secret) {
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
//    public static final DecodedJWT parseByECDSA(AlgorithmSugar.ECDSA ecdsa, String token, ECPublicKey publicKey, ECPrivateKey privateKey) {
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
//    public static final DecodedJWT parseByRSA(String token, AlgorithmSugar.RSA rsa, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
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
    public static final DecodedJWT parse(String token, Algorithm algorithm) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }


    /**
     * validate
     * @param token
     * @param algorithm
     * @return
     */
    public static final boolean validate(String token, Algorithm algorithm) {
        boolean validate;
        try {
            DecodedJWT parse = parse(token, algorithm);
            if (parse == null)
                validate = false;
            else if (parse.getExpiresAt() == null)
                validate = true;
//            else if (isExpired(parse.getExpiresAt()))
//                validate = false;
            else
                validate = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            validate = false;
        }
        return validate;
    }

    public static final Boolean isExpired(String token, Algorithm algorithm) {
        Boolean isExpired;
        try {
            isExpired = parse(token, algorithm) != null ? false : null;
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            isExpired = true;
        } catch (Exception e) {
            e.printStackTrace();
            isExpired = null;
        }
        return isExpired;
    }



    public static final JWTCreator.Builder withClaim(JWTCreator.Builder builder, String name, Object value) {
        Class<JWTCreator.Builder> clazz = JWTCreator.Builder.class;
        try {
            Method method = clazz.getDeclaredMethod("addClaim", new Class[]{String.class, Object.class});
            ReflectionUtils.makeAccessible(method);
            Object invoke = method.invoke(builder, new Object[]{name, value});
            return builder;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
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
//        public static final String signForTokenByHMAC256(JWTCreator.Builder builder, String secret) {
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
//        public static final String signForTokenByRSA256(JWTCreator.Builder builder, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
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
//        public static final DecodedJWT parseTokenByHMAC256(String token, String secret) {
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
//        public static final DecodedJWT parseTokenByRSA256(String token, RSAPublicKey publicKey, RSAPrivateKey privateKey) {
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


//    public static final Date getExpiredSeconds(Calendar calendar, int seconds) {
//        return DateTimeSugar.CalendarUtils.add(calendar, Calendar.SECOND, seconds).getTime();
//    }
//
//    public static final Date getExpiredMinutes(Calendar calendar, int minutes) {
//        return DateTimeSugar.CalendarUtils.add(calendar, Calendar.MINUTE, minutes).getTime();
//    }
//
//    public static final Date getExpiredHours(Calendar calendar, int hours) {
//        return DateTimeSugar.CalendarUtils.add(calendar, Calendar.HOUR_OF_DAY, hours).getTime();
//    }
//
//    public static final Date getExpiredDays(Calendar calendar, int days) {
//        return DateTimeSugar.CalendarUtils.add(calendar, Calendar.DAY_OF_YEAR, days).getTime();
//    }
//
//    public static final Date getExpired(Calendar calendar, int calendarField, int mount) {
//        return DateTimeSugar.CalendarUtils.add(calendar, calendarField, mount).getTime();
//    }

    public static final boolean isExpired(Date expiredDate) {
        return isExpired(expiredDate, new Date());
    }

    public static final boolean isExpired(Date expiredDate, Date now) {
        return now.after(expiredDate);
    }

//    public static final Date now(Calendar calendar) {
//        return calendar.getTime();
//    }
}
