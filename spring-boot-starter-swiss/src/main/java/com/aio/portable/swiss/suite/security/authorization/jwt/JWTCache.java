package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.suite.resource.ClassLoaderSugar;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class JWTCache {
    private static <T> Class<T> reflectForClass(String className) {
        Class<T> clazz = (Class<T>) ClassLoaderSugar.load(className);
        return clazz;
    }

    private final static String getClassKey(String key) {
        return key + "__class";
    }

    public final static String sign(JWTTemplate jwtTemplate, String issuer, Map<String, Object> addition) {
        Map<String, Object> classMap = remap(addition);
        String token = jwtTemplate.sign(issuer, classMap);
        return token;
    }

    public final static String sign(JWTTemplate jwtTemplate, String issuer, int minutes, Map<String, Object> addition) {
        Map<String, Object> classMap = remap(addition);
        String token = jwtTemplate.sign(issuer, minutes, classMap);
        return token;
    }

    public final static String sign(JWTTemplate jwtTemplate, String issuer, String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return sign(jwtTemplate, issuer, map);
    }

    public final static String sign(JWTTemplate jwtTemplate, String issuer, int minutes, String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return sign(jwtTemplate, issuer, minutes, map);
    }

    private final static Map<String, Object> remap(Map<String, Object> addition) {
        Map<String, Object> toMap = new HashMap<>();
        addition.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            String classKey = getClassKey(key);
            String classValue = value.getClass().getName();

            toMap.put(key, value);
            toMap.put(classKey, classValue);
        });
        return toMap;
    }

    public final static Map<String, Object> parseForMap(JWTTemplate jwtTemplate, String token) {
        DecodedJWT decodedJWT = jwtTemplate.parse(token);

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

    public final static <T> T parseKey(JWTTemplate jwtTemplate, String token, String key) {
        Map<String, Object> map = parseForMap(jwtTemplate, token);
        T t = (T) map.get(key);
        return t;
    }

    public final static String parseForIssuer(JWTTemplate jwtTemplate, String token) {
        DecodedJWT decodedJWT = jwtTemplate.parse(token);
        String issuer = decodedJWT.getIssuer();
        return issuer;
    }


}