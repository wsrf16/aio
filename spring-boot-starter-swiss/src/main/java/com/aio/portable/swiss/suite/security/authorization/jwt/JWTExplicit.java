package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.HashMap;
import java.util.Map;

public interface JWTExplicit {
    Boolean getExplicit();

    default Map<String, Object> explicit(Map<String, Object> addition) {
        Map<String, Object> map;
        if (getExplicit() == true) {
            Map<String, Object> explicit = new HashMap<>();
            addition.entrySet().forEach(c -> {
                String key = c.getKey();
                Object value = c.getValue();
                String keyClass = getKeyClass(key);
                String valueClass = value.getClass().getName();

                explicit.put(key, value);
                explicit.put(keyClass, valueClass);
            });
            map = explicit;
        } else {
            map = addition;
        }
        return map;
    }

    default String getKeyClass(String key) {
        return key + CLASSNAME_SUFFIX;
    }

    String CLASSNAME_SUFFIX = "|class";


    default <T> Class<T> loadClass(String className) {
        Class<T> clazz = (Class<T>) ClassLoaderSugar.load(className);
        return clazz;
    }

    default Map<String, Object> forMap(DecodedJWT decodedJWT) {
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
}
