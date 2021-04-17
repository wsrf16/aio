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

    JWTFactory newFactory();

    String sign(JWTCreator.Builder builder);

    DecodedJWT parse(String token);

    Boolean validate(String token);

    default Boolean validate(String token, String issuer) {
        String tokenIssuer = parse(token).getIssuer();
        boolean v = Objects.equals(tokenIssuer, issuer);
        return v;
    }

    default String sign() {
        JWTCreator.Builder builder = newFactory().createJWTBuilder();
        return sign(builder);
    }

    default String sign(String issuer) {
        JWTCreator.Builder builder = newFactory().createJWTBuilderWithNewIssuer(issuer);
        return sign(builder);
    }

    default String sign(String issuer, Map<String, Object> map) {
        JWTCreator.Builder builder = newFactory().createJWTBuilderWithNewIssuer(issuer);
        map.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    default String sign(String issuer, int expireMinutes, Map<String, Object> map) {
        JWTCreator.Builder builder = newFactory().createJWTBuilderWithNewIssuer(issuer);
        Calendar now = DateTimeSugar.CalendarUtils.now();
        Date issuedAt = now.getTime();
        Date expiresAt = JWTSugar.getExpiredMinutes(now, Calendar.SECOND, expireMinutes * 60);
        builder.withExpiresAt(expiresAt);

        map.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }
}
