package com.aio.portable.swiss.suite.security.authorization.jwt.encryption;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface Base64TokenAlgorithm {
    Algorithm getAlgorithm();

    default String sign(JWTCreator.Builder builder) {
        Algorithm algorithm = getAlgorithm();
        String token = JWTSugar.sign(builder, algorithm);
        return JDKBase64Convert.encodeToString(token);
    }

    default boolean validate(String base64Token) {
        String token = JDKBase64Convert.decodeToString(base64Token);
        Algorithm algorithm = getAlgorithm();
        return JWTSugar.validate(token, algorithm);
    }

    default boolean isExpired(String base64Token) {
        String token = JDKBase64Convert.decodeToString(base64Token);
        Algorithm algorithm = getAlgorithm();
        return JWTSugar.isExpired(token, algorithm);
    }

    default DecodedJWT parse(String base64Token) {
        String token = JDKBase64Convert.decodeToString(base64Token);
        Algorithm algorithm = getAlgorithm();
        return JWTSugar.parse(token, algorithm);
    }
}
