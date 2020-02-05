package com.aio.portable.swiss.hamlet.security.authorization.code;

import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.HashMap;
import java.util.Map;

public class OAuthCodes {

    private Map<String, OAuthCode> map = new HashMap<>();

    public Map<String, OAuthCode> getMap() {
        return map;
    }

    public void setMap(Map<String, OAuthCode> map) {
        this.map = map;
    }
}
