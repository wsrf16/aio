package com.aio.portable.swiss.suite.security.authorization.jwt.encryption;

import com.aio.portable.swiss.suite.security.authorization.jwt.JWTSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenAlgorithm {
    Algorithm getAlgorithm();

    default String encode(JWTCreator.Builder builder, Algorithm algorithm) {
        return JWTSugar.sign(builder, algorithm);
    }

    default boolean verify(String token, Algorithm algorithm) {
        return JWTSugar.validate(token, algorithm);
    }

    default DecodedJWT parse(String token, Algorithm algorithm) {
        return JWTSugar.parse(token, algorithm);
    }
}
