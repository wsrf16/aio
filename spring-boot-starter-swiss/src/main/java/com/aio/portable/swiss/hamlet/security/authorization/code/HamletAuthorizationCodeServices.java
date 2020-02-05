package com.aio.portable.swiss.hamlet.security.authorization.code;

//import com.aio.portable.swiss.hamlet.security.authorization.code.OfficialJdbcAuthorizationCodeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public abstract class HamletAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private Map<String, OAuthCode> oauthCodeMap;

    public HamletAuthorizationCodeServices(OAuthCodes oauthCodes) {
        this.oauthCodeMap = oauthCodes.getMap();
    }

    private final static int AUTHORIZATION_CODE_LENGTH = 16;

    private RandomValueStringGenerator generator = new RandomValueStringGenerator(AUTHORIZATION_CODE_LENGTH);

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = this.generator.generate();
        this.store(code, authentication);
        return code;
    }

    @Override
    protected void store(String code, OAuth2Authentication oauth2Authentication) {
        OAuthCode oauthCode = OAuthCode.newInstance(code, oauth2Authentication);
        oauthCodeMap.put(code, oauthCode);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        return oauthCodeMap.remove(code).getOauth2Authentication();
    }
}
