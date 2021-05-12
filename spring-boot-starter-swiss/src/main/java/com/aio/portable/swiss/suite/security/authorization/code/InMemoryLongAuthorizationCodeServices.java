package com.aio.portable.swiss.suite.security.authorization.code;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

///**
// * LongRandomValueAuthorizationCodeServices
// * Method "configure(AuthorizationServerEndpointsConfigurer endpoints)" In AuthorizationServerConfigurerAdapter.class
// */
//public class InMemoryLongAuthorizationCodeServices extends LongAuthorizationCodeServices {
//    private AuthorizationCodeStorage authorizationCodeStorage;
//
//    public InMemoryLongAuthorizationCodeServices(AuthorizationCodeStorage authorizationCodeStorage) {
//        this.authorizationCodeStorage = authorizationCodeStorage;
//    }
//
//    public InMemoryLongAuthorizationCodeServices(AuthorizationCodeStorage authorizationCodeStorage, int length) {
//        this.authorizationCodeStorage = authorizationCodeStorage;
//        setLength(length);
//    }
//
//    @Override
//    protected void store(String code, OAuth2Authentication oauth2Authentication) {
//        AuthorizationCodeObject oauthCode = AuthorizationCodeObject.newInstance(code, oauth2Authentication);
//        authorizationCodeStorage.set(code, oauthCode);
//    }
//
//    @Override
//    protected OAuth2Authentication remove(String code) {
//        return authorizationCodeStorage.remove(code).getOauth2Authentication();
//    }
//}


/**
 * LongRandomValueAuthorizationCodeServices
 * Method "configure(AuthorizationServerEndpointsConfigurer endpoints)" In AuthorizationServerConfigurerAdapter.class
 */
public class InMemoryLongAuthorizationCodeServices extends InMemoryAuthorizationCodeServices {
    private final static int AUTHORIZATION_CODE_LENGTH = 16;

    private int length = AUTHORIZATION_CODE_LENGTH;

    public InMemoryLongAuthorizationCodeServices() {
        setLength(AUTHORIZATION_CODE_LENGTH);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        final RandomValueStringGenerator generator = this.getGenerator();
        generator.setLength(length);
        this.length = length;
    }

    private RandomValueStringGenerator getGenerator() {
        final Field field = ReflectionUtils.findField(RandomValueAuthorizationCodeServices.class,
                "generator");
        ReflectionUtils.makeAccessible(field);
        final RandomValueStringGenerator generator = (RandomValueStringGenerator) ReflectionUtils.getField(field, this);
        return generator;
    }

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = this.getGenerator().generate();
        this.store(code, authentication);
        return code;
    }
}
