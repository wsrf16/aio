package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class JWTCache {
    private static <T> Class<T> reflectForClass(String className) {
        Class<T> clazz;
        try {
            clazz = (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return clazz;
    }

    private final static String getClassKey(String key) {
        return key + "__class";
    }

    public final static String sign(JWTSession jwtSession, String issuer, Map<String, Object> map) {
        Map<String, Object> classMap = remap(map);
        String token = jwtSession.sign(issuer, classMap);
        return token;
    }

    public final static String sign(JWTSession jwtSession, String issuer, int minutes, Map<String, Object> map) {
        Map<String, Object> classMap = remap(map);
        String token = jwtSession.sign(issuer, minutes, classMap);
        return token;
    }

    public final static String sign(JWTSession jwtSession, String issuer, String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return sign(jwtSession, issuer, map);
    }

    public final static String sign(JWTSession jwtSession, String issuer, int minutes, String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return sign(jwtSession, issuer, minutes, map);
    }

    private final static Map<String, Object> remap(Map<String, Object> map) {
        Map<String, Object> toMap = new HashMap<>();
        map.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            String classKey = getClassKey(key);
            String classValue = value.getClass().getName();

            toMap.put(key, value);
            toMap.put(classKey, classValue);
        });
        return toMap;
    }

    public final static Map<String, Object> parseForMap(JWTSession jwtSession, String token) {
        DecodedJWT decodedJWT = jwtSession.parse(token);

        return toMap(decodedJWT);
    }

    private final static Map<String, Object> toMap(DecodedJWT decodedJWT) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Claim> claims = decodedJWT.getClaims();

        claims.entrySet().forEach(c -> {
            String key = c.getKey();
            String classKey = getClassKey(key);
            if (claims.containsKey(classKey)) {
                String clazzName = claims.get(classKey).asString();
                Class<?> clazz = reflectForClass(clazzName);
                Object value = c.getValue().as(clazz);
                map.put(key, value);
            }
        });
        return map;
    }

    public final static <T> T parseKey(JWTSession jwtSession, String token, String key) {
        Map<String, Object> map = parseForMap(jwtSession, token);
        T t = (T) map.get(key);
        return t;
    }

    public final static String parseForIssuer(JWTSession jwtSession, String token) {
        DecodedJWT decodedJWT = jwtSession.parse(token);
        String issuer = decodedJWT.getIssuer();
        return issuer;
    }


}