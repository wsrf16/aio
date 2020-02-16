package com.aio.portable.swiss.autoconfigure.properties;

import com.aio.portable.swiss.suite.net.protocol.http.JWTSugar;
import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

//@Configuration
//@ConfigurationProperties(
//        prefix = "spring.jwt"
//)
public class JWTProperties  {
    private Boolean require = true;
    private String JWTId;
    private String secret;
    private List<String> basePackages;
    private String keyId;
    private String issuer;
    private String subject;
    private String[] audience;
//    private Date issuedAt;
//    private Date expiresAt;
    private Integer expiredHours;
    private Date notBefore;

    public Boolean getRequire() {
        return require;
    }

    public void setRequire(Boolean require) {
        this.require = require;
    }

    public String getJWTId() {
        return JWTId;
    }

    public void setJWTId(String JWTId) {
        this.JWTId = JWTId;
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

    public String[] getAudience() {
        return audience;
    }

    public void setAudience(String[] audience) {
        this.audience = audience;
    }

    public JWTClaims toJWTClaims() {
        Calendar now = DateTimeSugar.CalendarUtils.now();
        String JWTId = IDS.uuid();
        Date issuedAt = now.getTime();
        Date expiresAt = JWTSugar.Classic.getExpiredDate(now, expiredHours);

        JWTClaims target = new JWTClaims();
        BeanUtils.copyProperties(this, target);
        target.setJWTId(JWTId);
        target.setIssuedAt(issuedAt);
        target.setExpiresAt(expiresAt);
        return target;
    }

    public JWTClaims toJWTClaims(int calendarField, int mount) {
        Calendar now = DateTimeSugar.CalendarUtils.now();
        String JWTId = IDS.uuid();
        Date issuedAt = now.getTime();
        Date expiresAt = JWTSugar.Classic.getExpiredDate(now, calendarField, mount);

        JWTClaims target = new JWTClaims();
        BeanUtils.copyProperties(this, target);
        target.setJWTId(JWTId);
        target.setIssuedAt(issuedAt);
        target.setExpiresAt(expiresAt);
        return target;
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

}

