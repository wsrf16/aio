package com.aio.portable.swiss.structure.security.authentication.jwt;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpHeaders;

import java.util.Objects;

public interface JWTAction {
    //"Authorization";
    String AUTHORIZATION_HEAD = HttpHeaders.AUTHORIZATION;

    JWTProperties toJwtProperties();

//    String token(String userName);

    String token(JWTCreator.Builder builder);

    DecodedJWT getJWT(String token);

    Boolean verify(String token);

    default Boolean verify(String token, String issuer) {
        String tokenIssuer = getJWT(token).getIssuer();
        boolean v = Objects.equals(tokenIssuer, issuer);
        return v;
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
