package com.aio.portable.swiss.autoconfigure.properties;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.structure.net.protocol.http.JWTSugar;
import com.aio.portable.swiss.sugar.DateTimeSugar;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//@Configuration
//@ConfigurationProperties(
//        prefix = "spring.jwt"
//)
public class JWTProperties  {
    private Boolean require = true;
    private String secret;
    private List<String> basePackages;
    private String keyId;
    private String issuer;
    private String subject;
    private String audience;
    private Date issuedAt;
    private Date expiresAt;
    private Integer expiredHours;
    private Date notBefore;
    private String JWTId;

    public Boolean getRequire() {
        return require;
    }

    public void setRequire(Boolean require) {
        this.require = require;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
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

    public JWTProperties toJwtProperties() {
        Calendar now = DateTimeSugar.CalendarUtils.now();
        String JWTId = UUID.randomUUID().toString().replace("-", Constant.EMPTY);
        Date issuedAt = now.getTime();
        Date expiresAt = JWTSugar.Classic.getExpiredDate(now, expiredHours);

        JWTProperties target = new JWTProperties();
        BeanUtils.copyProperties(this, target);
        target.setJWTId(JWTId);
        target.setIssuedAt(issuedAt);
        target.setExpiresAt(expiresAt);
        return target;
    }

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

    public Integer getExpiredHours() {
        return expiredHours;
    }

    public void setExpiredHours(Integer expiredHours) {
        this.expiredHours = expiredHours;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public String getJWTId() {
        return JWTId;
    }

    public void setJWTId(String JWTId) {
        this.JWTId = JWTId;
    }

}

