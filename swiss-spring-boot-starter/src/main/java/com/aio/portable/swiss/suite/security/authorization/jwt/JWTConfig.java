package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.spring.factories.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import org.springframework.beans.BeanUtils;

public class JWTConfig extends JWTProperties {
    public static final JWTConfig build(JWTProperties properties) {
        JWTConfig config = new JWTConfig();
        BeanUtils.copyProperties(properties, config);
        return config;
    }

    public JWTExpiredDate createExpiredDate() {
        return JWTExpiredDate.getExpiredMinutes(this.getExpiredMinutes());
    }

    public JWTCreator.Builder createBuilder(String jwtId) {
        JWTCreator.Builder builder = JWT.create();
        if (this.getKeyId() != null)
            builder.withKeyId(this.getKeyId());
        if (this.getIssuer() != null)
            builder.withIssuer(this.getIssuer());
        if (this.getSubject() != null)
            builder.withSubject(this.getSubject());
        if (this.getAudience() != null)
            builder.withAudience(this.getAudience());
        if (this.getNotBefore() != null)
            builder.withNotBefore(this.getNotBefore());
        JWTExpiredDate jwtExpiredDate = this.createExpiredDate();
        if (jwtExpiredDate.isValid()) {
            builder.withIssuedAt(jwtExpiredDate.getIssuedAt());
            builder.withExpiresAt(jwtExpiredDate.getExpiresAt());
        }
        builder.withJWTId(jwtId);
        builder.withClaim("r", System.currentTimeMillis());
        return builder;
    }

    public JWTCreator.Builder createBuilder() {
        return createBuilder(IDS.uuid());
    }
}
