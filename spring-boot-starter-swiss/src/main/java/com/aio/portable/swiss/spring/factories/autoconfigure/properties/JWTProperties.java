package com.aio.portable.swiss.spring.factories.autoconfigure.properties;

import java.util.Date;

public class JWTProperties {
    private static final int DEFAULT_EXPIRED_MINUTES = 10;
    private static final String SECRET = "secret";

    protected Boolean enabled = true;
    protected Boolean explicit = true;
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

    public Boolean getExplicit() {
        return explicit;
    }

    public void setExplicit(Boolean explicit) {
        this.explicit = explicit;
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



//    public JWTProperties clone() {
//        JWTProperties properties = new JWTProperties();
//        BeanUtils.copyProperties(this, properties);
//        properties.setJWTId(IDS.uuid());
//        return properties;
//    }

//    public JWTConfig toConfig() {
//        JWTConfig jwtConfig = new JWTConfig();
//        BeanUtils.copyProperties(this, jwtConfig);
//        return jwtConfig;
//    }
}

