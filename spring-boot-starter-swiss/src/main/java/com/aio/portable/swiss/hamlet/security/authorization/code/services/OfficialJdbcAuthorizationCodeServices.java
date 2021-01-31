package com.aio.portable.swiss.hamlet.security.authorization.code.services;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;

import javax.sql.DataSource;

/**
 * InMemoryAuthorizationCodeServices
 * JdbcAuthorizationCodeServices
 * RandomValueAuthorizationCodeServices
 */
public abstract class OfficialJdbcAuthorizationCodeServices extends JdbcAuthorizationCodeServices {
    public OfficialJdbcAuthorizationCodeServices(DataSource dataSource) {
        super(dataSource);
    }

    private final static int AUTHORIZATION_CODE_LENGTH = 12;

    private RandomValueStringGenerator generator = new RandomValueStringGenerator(AUTHORIZATION_CODE_LENGTH);

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
//        return super.createAuthorizationCode(authentication);
        String code = this.generator.generate();
        this.store(code, authentication);
        return code;
    }
}
