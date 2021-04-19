package com.aio.portable.swiss.autoconfigure;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.suite.security.authentication.jwt.JWTSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

//@Configuration
//@ConditionalOnClass({com.auth0.jwt.JWT.class})
public class JWTAutoConfiguration {

    @ConditionalOnProperty(
            prefix = "spring.jwt",
            name = "issuer"
    )
    @Bean
    public JWTProperties jwtProperties() {
        return new JWTProperties();
    }

    @Bean
    @ConditionalOnBean(JWTProperties.class)
    public JWTSession jwtSession(JWTProperties jwtProperties) {
        return new JWTSession(jwtProperties);
    }
}
