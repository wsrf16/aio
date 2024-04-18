package com.aio.portable.swiss.suite.security.authorization.jwt.encryption;

import com.aio.portable.swiss.suite.security.authorization.jwt.JWTSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenAlgorithm {
    Algorithm getAlgorithm();

    default String sign(JWTCreator.Builder builder) {
        return JWTSugar.sign(builder, getAlgorithm());
    }

    default boolean validate(String token) {
        return JWTSugar.validate(token, getAlgorithm());
    }

    default boolean isExpired(String token) {
        return JWTSugar.isExpired(token, getAlgorithm());
    }

    default DecodedJWT parse(String token) {
        return JWTSugar.parse(token, getAlgorithm());
    }
}
