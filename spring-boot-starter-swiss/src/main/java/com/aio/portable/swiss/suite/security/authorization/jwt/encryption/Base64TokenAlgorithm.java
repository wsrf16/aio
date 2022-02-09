package com.aio.portable.swiss.suite.security.authorization.jwt.encryption;

import com.aio.portable.swiss.suite.algorithm.encode.JDKBase64Convert;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface Base64TokenAlgorithm extends TokenAlgorithm {
    default String sign(JWTCreator.Builder builder) {
        String token = TokenAlgorithm.super.sign(builder);
        return JDKBase64Convert.encodeToString(token);
    }

    default boolean validate(String base64Token) {
        String token = JDKBase64Convert.decodeToString(base64Token);
        return TokenAlgorithm.super.validate(token);
    }

    default boolean isExpired(String base64Token) {
        String token = JDKBase64Convert.decodeToString(base64Token);
        return TokenAlgorithm.super.isExpired(token);
    }

    default DecodedJWT parse(String base64Token) {
        String token = JDKBase64Convert.decodeToString(base64Token);
        return TokenAlgorithm.super.parse(token);
    }
}
