package com.aio.portable.swiss.autoconfigure.properties;

import com.aio.portable.swiss.suite.security.authentication.jwt.JWTFactory;
import com.aio.portable.swiss.suite.security.authentication.jwt.JWTSugar;
import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import org.springframework.beans.BeanUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JWTProperties {
    private final static int EXPIRED_MINUTES = 10;
    private final static String SECRET = "secret";

    protected Boolean require = true;
    protected String algorithm = "HMAC256";
    protected String JWTId;
    protected String secret = SECRET;
    protected List<String> scanBasePackages;
    protected String keyId;
    protected String issuer = "issuer";
    protected String subject;
    protected String[] audience;
//    protected Date issuedAt;
//    protected Date expiresAt;
    protected Integer expiredMinutes = EXPIRED_MINUTES;
    protected Date notBefore;

    public Boolean getRequire() {
        return require;
    }

    public void setRequire(Boolean require) {
        this.require = require;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
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

    public List<String> getScanBasePackages() {
        return scanBasePackages;
    }

    public void setScanBasePackages(List<String> scanBasePackages) {
        this.scanBasePackages = scanBasePackages;
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

    public JWTFactory newFactory() {
        Calendar now = DateTimeSugar.CalendarUtils.now();
        String JWTId = IDS.uuid();
        Date issuedAt = now.getTime();
        Date expiresAt = JWTSugar.getExpiredMinutes(now, expiredMinutes);

        JWTProperties properties = new JWTProperties();
        BeanUtils.copyProperties(this, properties);
        properties.setJWTId(JWTId);
        JWTFactory factory = new JWTFactory(properties);
        factory.setIssuedAt(issuedAt);
        factory.setExpiresAt(expiresAt);
        return factory;
    }

    public JWTFactory newFactory(int calendarField, int mount) {
        JWTProperties properties = new JWTProperties();
        BeanUtils.copyProperties(this, properties);
        properties.setJWTId(IDS.uuid());

        Calendar now = DateTimeSugar.CalendarUtils.now();
        Date issuedAt = now.getTime();
        Date expiresAt = JWTSugar.getExpiredMinutes(now, calendarField, mount);
        JWTFactory factory = new JWTFactory(properties);
        factory.setIssuedAt(issuedAt);
        factory.setExpiresAt(expiresAt);
        return factory;
    }

    public Integer getExpiredMinutes() {
        return expiredMinutes;
    }

    public void setExpiredMinutes(Integer expiredMinutes) {
        this.expiredMinutes = expiredMinutes;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

}

