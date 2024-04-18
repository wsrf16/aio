package com.aio.portable.swiss.suite.security.authorization.code;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.util.ReflectionUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;

/**
 * InMemoryAuthorizationCodeServices
 * JdbcAuthorizationCodeServices
 * RandomValueAuthorizationCodeServices
 */
public class JdbcLongAuthorizationCodeServices extends JdbcAuthorizationCodeServices {
    private static final int AUTHORIZATION_CODE_LENGTH = 16;

    private int length = AUTHORIZATION_CODE_LENGTH;

    public JdbcLongAuthorizationCodeServices(DataSource dataSource) {
        super(dataSource);
        setLength(AUTHORIZATION_CODE_LENGTH);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        RandomValueStringGenerator generator = this.getGenerator();
        generator.setLength(length);
        this.length = length;
    }

    private RandomValueStringGenerator getGenerator() {
        Field field = ReflectionUtils.findField(RandomValueAuthorizationCodeServices.class,
                "generator");
        ReflectionUtils.makeAccessible(field);
        RandomValueStringGenerator generator = (RandomValueStringGenerator) ReflectionUtils.getField(field, this);
        return generator;
    }

    @Override
    public String createAuthorizationCode(OAuth2Authentication authentication) {
        String code = this.getGenerator().generate();
        this.store(code, authentication);
        return code;
    }

}
