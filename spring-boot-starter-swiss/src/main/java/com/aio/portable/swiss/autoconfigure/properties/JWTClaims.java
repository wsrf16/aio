package com.aio.portable.swiss.autoconfigure.properties;

import java.util.Date;

public class JWTClaims extends JWTProperties {
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

}

