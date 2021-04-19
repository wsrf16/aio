package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public interface JWTAction {
    // "Authorization";
    String AUTHORIZATION_HEAD = JWTSugar.AUTHORIZATION_HEAD;
    String BEAR_PREFIX = "Bearer ";

    String sign(JWTCreator.Builder builder);

    DecodedJWT parse(String token);

    Boolean validate(String token);

    default Boolean validate(String token, String issuer) {
        String tokenIssuer = parse(token).getIssuer();
        boolean v = Objects.equals(tokenIssuer, issuer);
        return v;
    }


}
