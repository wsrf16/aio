package com.aio.portable.swiss.autoconfigure.properties;

import com.aio.portable.swiss.structure.net.protocol.http.JWTSugar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

//@Configuration
//@ConfigurationProperties(
//        prefix = "spring.jwt"
//)
public class JWTProperties  {
//    private Boolean enable = true;
    private String secret;
    private Integer expiredHours;
    private Boolean require = true;
    private List<String> basePackages;
    private String keyId;
    private String issuer;
    private String subject;
    private String audience;
//    private Date expiresAt;
    private Date notBefore;
    private Date issuedAt;
    private String JWTId;

//    public Boolean getEnable() {
//        return enable;
//    }
//
//    public void setEnable(Boolean enable) {
//        this.enable = enable;
//    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getExpiredHours() {
        return expiredHours;
    }

    public void setExpiredHours(Integer expiredHours) {
        this.expiredHours = expiredHours;
    }

    public Boolean getRequire() {
        return require;
    }

    public void setRequire(Boolean require) {
        this.require = require;
    }

    public List<String> getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(List<String> basePackages) {
        this.basePackages = basePackages;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public Date getExpiresAt() {
        Calendar calendar = Calendar.getInstance();
        Date now = JWTSugar.Classic.now(calendar);
        Date expiresAt = JWTSugar.Classic.getExpiredDate(calendar, expiredHours);
        return expiresAt;
    }

//    public void setExpiresAt(Date expiresAt) {
//        this.expiresAt = expiresAt;
//    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Date issuedAt) {
        this.issuedAt = issuedAt;
    }

    public String getJWTId() {
        return JWTId;
    }

    public void setJWTId(String JWTId) {
        this.JWTId = JWTId;
    }

}

