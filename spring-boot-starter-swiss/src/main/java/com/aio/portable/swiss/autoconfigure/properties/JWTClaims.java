package com.aio.portable.swiss.autoconfigure.properties;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;

import java.util.Date;

public class JWTClaims extends JWTClaimsProperties {
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
        JWTCreator.Builder builder = JWT.create();
        if (keyId != null)
            builder.withKeyId(keyId);
        if (issuer != null)
            builder.withIssuer(issuer);
        if (subject != null)
            builder.withSubject(subject);
        if (audience != null)
            builder.withAudience(audience);
        if (expiresAt != null)
            builder.withExpiresAt(expiresAt);
        if (notBefore != null)
            builder.withNotBefore(notBefore);
        if (issuedAt != null)
            builder.withIssuedAt(issuedAt);
        if (JWTId != null)
            builder.withJWTId(JWTId);
        return builder;
    }

    public JWTCreator.Builder createJWTBuilderWithNewIssuer(String issuer) {
        JWTCreator.Builder builder = JWT.create();
        if (keyId != null)
            builder.withKeyId(keyId);
        if (issuer != null)
            builder.withIssuer(issuer);
        if (subject != null)
            builder.withSubject(subject);
        if (audience != null)
            builder.withAudience(audience);
        if (expiresAt != null)
            builder.withExpiresAt(expiresAt);
        if (notBefore != null)
            builder.withNotBefore(notBefore);
        if (issuedAt != null)
            builder.withIssuedAt(issuedAt);
        if (JWTId != null)
            builder.withJWTId(JWTId);
        return builder;
    }
}

