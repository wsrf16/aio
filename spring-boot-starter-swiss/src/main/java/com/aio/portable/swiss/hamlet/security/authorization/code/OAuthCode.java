package com.aio.portable.swiss.hamlet.security.authorization.code;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OAuthCode {

    private String code;

    private OAuth2Authentication oauth2Authentication;

    private Date date;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public OAuth2Authentication getOauth2Authentication() {
        return oauth2Authentication;
    }

    public void setOauth2Authentication(OAuth2Authentication oauth2Authentication) {
        this.oauth2Authentication = oauth2Authentication;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public final static OAuthCode newInstance(String code, OAuth2Authentication oauth2Authentication) {
        OAuthCode oauthCode = new OAuthCode();
        oauthCode.code = code;
        oauthCode.oauth2Authentication = oauth2Authentication;
        oauthCode.date = new Date();
        return oauthCode;
    }
}
