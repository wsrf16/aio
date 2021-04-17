package com.aio.portable.swiss.suite.security.authentication.jwt;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.auth0.jwt.JWTCreator;

import java.util.Date;

public class JWTFactory {
    private JWTProperties jwtProperties;

    public JWTProperties getJwtProperties() {
        return jwtProperties;
    }

    public JWTFactory(JWTProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private Date issuedAt;
    private Date expiresAt;

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public JWTCreator.Builder createJWTBuilder() {
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        if (jwtProperties.keyId != null)
            builder.withKeyId(jwtProperties.keyId);
        if (jwtProperties.issuer != null)
            builder.withIssuer(jwtProperties.issuer);
        if (jwtProperties.subject != null)
            builder.withSubject(jwtProperties.subject);
        if (jwtProperties.audience != null)
            builder.withAudience(jwtProperties.audience);
        if (expiresAt != null)
            builder.withExpiresAt(expiresAt);
        if (jwtProperties.notBefore != null)
            builder.withNotBefore(jwtProperties.notBefore);
        if (issuedAt != null)
            builder.withIssuedAt(issuedAt);
        if (jwtProperties.JWTId != null)
            builder.withJWTId(jwtProperties.JWTId);
        return builder;
    }

    public JWTCreator.Builder createJWTBuilderWithNewIssuer(String issuer) {
        JWTCreator.Builder builder = com.auth0.jwt.JWT.create();
        if (jwtProperties.keyId != null)
            builder.withKeyId(jwtProperties.keyId);
        if (issuer != null)
            builder.withIssuer(issuer);
        if (jwtProperties.subject != null)
            builder.withSubject(jwtProperties.subject);
        if (jwtProperties.audience != null)
            builder.withAudience(jwtProperties.audience);
        if (expiresAt != null)
            builder.withExpiresAt(expiresAt);
        if (jwtProperties.notBefore != null)
            builder.withNotBefore(jwtProperties.notBefore);
        if (issuedAt != null)
            builder.withIssuedAt(issuedAt);
        if (jwtProperties.JWTId != null)
            builder.withJWTId(jwtProperties.JWTId);
        return builder;
    }
}
