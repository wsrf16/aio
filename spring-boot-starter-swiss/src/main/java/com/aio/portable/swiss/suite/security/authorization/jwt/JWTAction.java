package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Objects;

public interface JWTAction {
    String sign(JWTCreator.Builder builder);

    DecodedJWT parse(String token);

    boolean validate(String token);

    default boolean validate(String token, String issuer) {
        String tokenIssuer = parse(token).getIssuer();
        return Objects.equals(tokenIssuer, issuer);
    }


}
