package com.aio.portable.swiss.suite.security.authentication.jwt.encryption;

import com.aio.portable.swiss.suite.security.authentication.jwt.JWTSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenAlgorithm {
    void setAlgorithm(Algorithm algorithm);

    default String encode(JWTCreator.Builder builder, Algorithm algorithm) {
        return JWTSugar.sign(builder, algorithm);
    }

    default boolean verify(String token, Algorithm algorithm) {
        return JWTSugar.verify(token, algorithm);
    }

    default DecodedJWT parse(String token, Algorithm algorithm) {
        return JWTSugar.parse(token, algorithm);
    }
}
