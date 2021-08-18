package com.aio.portable.swiss.suite.security.authorization.jwt;

import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;

import java.util.Date;

public class JWTConfig {
    private final static int DEFAULT_EXPIRED_MINUTES = 10;
    private final static String SECRET = "secret";

    protected Boolean enabled = true;
    protected String signAlgorithm = "HMAC256";
    //    protected String JWTId;
    protected String secret = SECRET;
    protected String privateKey;
    protected String publicKey;
//    protected List<String> scanBasePackages;
    protected String keyId;
    protected String issuer = "issuer";
    protected String subject;
    protected String[] audience;
    protected Integer expiredMinutes = DEFAULT_EXPIRED_MINUTES;
    protected Date notBefore;


    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getSignAlgorithm() {
        return signAlgorithm;
    }

    public void setSignAlgorithm(String signAlgorithm) {
        this.signAlgorithm = signAlgorithm;
    }

//    public String getJWTId() {
//        return JWTId;
//    }
//
//    public void setJWTId(String JWTId) {
//        this.JWTId = JWTId;
//    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

//    public List<String> getScanBasePackages() {
//        return scanBasePackages;
//    }

//    public void setScanBasePackages(List<String> scanBasePackages) {
//        this.scanBasePackages = scanBasePackages;
//    }

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




    public JWTExpiredDate createValidDate() {
        return new JWTExpiredDate(expiredMinutes);
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
        JWTExpiredDate jwtExpiredDate = this.createValidDate();
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
