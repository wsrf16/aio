package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public class JWTCache {
    private final static String CLASSNAME_SUFFIX = "|class";
    protected JWTTemplate jwtTemplate;

    public JWTCache(JWTTemplate jwtTemplate) {
        this.jwtTemplate = jwtTemplate;
    }

    private static <T> Class<T> loadClass(String className) {
        Class<T> clazz = (Class<T>) ClassLoaderSugar.load(className);
        return clazz;
    }

    private final static String getKeyClass(String key) {
        return key + CLASSNAME_SUFFIX;
    }

    private final Map<String, Object> strengthen(Map<String, Object> addition) {
        Map<String, Object> toMap = new HashMap<>();
        addition.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            String keyClass = getKeyClass(key);
            String valueClass = value.getClass().getName();

            toMap.put(key, value);
            toMap.put(keyClass, valueClass);
        });
        return toMap;
    }



    private final static Map<String, Object> parse(DecodedJWT decodedJWT) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Claim> claims = decodedJWT.getClaims();

        claims.entrySet().forEach(c -> {
            String key = c.getKey();
            String keyClass = getKeyClass(key);
            if (claims.containsKey(keyClass)) {
                String className = claims.get(keyClass).asString();
                Class<?> clazz = loadClass(className);
                Object value = c.getValue().as(clazz);
                map.put(key, value);
            } else if (!key.endsWith(CLASSNAME_SUFFIX)){
                Object value;
                switch (key) {
                    case "kid":
                        value = c.getValue().asString();
                        break;
                    case "iss":
                        value = c.getValue().asString();
                        break;
                    case "sub":
                        value = c.getValue().asString();
                        break;
                    case "aud":
                        value = c.getValue().asArray(String.class);
                        break;
                    case "exp":
                        value = c.getValue().asDate();
                        break;
                    case "nbf":
                        value = c.getValue().asDate();
                        break;
                    case "iat":
                        value = c.getValue().asDate();
                        break;
                    case "jti":
                        value = c.getValue().asString();
                        break;
                    default:
                        value = c.getValue().as(Object.class);
                        break;
                }
                map.put(key, value);
            }
        });
        return map;
    }

    public final String set(Map<String, Object> addition) {
        String token = jwtTemplate.sign(strengthen(addition));
        return token;
    }

    public final String set(Map<String, Object> addition, String issuer) {
        String token = jwtTemplate.sign(issuer, strengthen(addition));
        return token;
    }

    public final String set(Map<String, Object> addition, String issuer, int minutes) {
        String token = jwtTemplate.sign(issuer, minutes, strengthen(addition));
        return token;
    }

    public final Map<String, Object> get(String token) {
        DecodedJWT decodedJWT = jwtTemplate.parse(token);
        return parse(decodedJWT);
    }

    public final Object get(String token, String key) {
        return get(token).get(key);
    }

    public final String getIssuer(String token) {
        DecodedJWT decodedJWT = jwtTemplate.parse(token);
        return decodedJWT.getIssuer();
    }


}