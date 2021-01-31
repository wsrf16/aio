package com.aio.portable.swiss.hamlet.security.authorization.code.services;

//import com.aio.portable.swiss.hamlet.security.authorization.code.OfficialJdbcAuthorizationCodeServices;
import com.aio.portable.swiss.hamlet.security.authorization.code.object.AuthorizationCodeObject;
import com.aio.portable.swiss.hamlet.security.authorization.code.storage.AuthorizationCodeStorage;
import com.aio.portable.swiss.hamlet.security.authorization.code.storage.MemoryAuthorizationCodeStorage;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * LongRandomValueAuthorizationCodeServices
 * Method "configure(AuthorizationServerEndpointsConfigurer endpoints)" In AuthorizationServerConfigurerAdapter.class
 */
public abstract class LongRandomValueAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private AuthorizationCodeStorage authorizationCodeStorage;

    public LongRandomValueAuthorizationCodeServices(MemoryAuthorizationCodeStorage authorizationCodeStorage) {
        this.authorizationCodeStorage = authorizationCodeStorage;
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
        AuthorizationCodeObject oauthCode = AuthorizationCodeObject.newInstance(code, oauth2Authentication);
        authorizationCodeStorage.set(code, oauthCode);
    }

    @Override
    protected OAuth2Authentication remove(String code) {
        return authorizationCodeStorage.remove(code).getOauth2Authentication();
    }
}
