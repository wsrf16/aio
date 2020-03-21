package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.autoconfigure.properties.JWTClaims;
import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.suite.net.protocol.http.JWTSugar;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public interface JWTAction {
    //"Authorization";
    String AUTHORIZATION_HEAD = HttpHeaders.AUTHORIZATION;

    JWTClaims toJWTClaims();

//    String token(String issuer);

    String sign(JWTCreator.Builder builder);

    DecodedJWT parse(String token);

    Boolean verify(String token);

    default Boolean verify(String token, String issuer) {
        String tokenIssuer = parse(token).getIssuer();
        boolean v = Objects.equals(tokenIssuer, issuer);
        return v;
    }

    default String sign(String issuer) {
        JWTCreator.Builder builder = JWTSugar.createJWTBuilderWithNewIssuer(toJWTClaims(), issuer);
        return sign(builder);
    }

    default String sign(String issuer, Map<String, Object> map) {
        JWTCreator.Builder builder = JWTSugar.createJWTBuilderWithNewIssuer(toJWTClaims(), issuer);
        map.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

    default String sign(String issuer, int expireMinutes, Map<String, Object> map) {
        JWTCreator.Builder builder = JWTSugar.createJWTBuilderWithNewIssuer(toJWTClaims(), issuer);
        Calendar now = DateTimeSugar.CalendarUtils.now();
        Date issuedAt = now.getTime();
        Date expiresAt = JWTSugar.Classic.getExpiredDate(now, Calendar.SECOND, expireMinutes * 60);
        builder.withExpiresAt(expiresAt);

        map.entrySet().forEach(c -> {
            String key = c.getKey();
            Object value = c.getValue();
            JWTSugar.withClaim(builder, key, value);
        });
        return sign(builder);
    }

//    Boolean getEnable();
//    Boolean getRequire();
//    String getSecret();
//    List<String> getBasePackages();
//    Integer getExpiredHours();
//    String getKeyId();
//    String getIssuer();
//    String getSubject();
//    String getAudience();
//    Date getExpiresAt();
//    Date getNotBefore();
//    Date getIssuedAt();
//    String getJWTId();

}
