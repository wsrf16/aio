package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.spring.factories.autoconfigure.properties.JWTProperties;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTConfig;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

//@Configuration
@ConditionalOnClass({com.auth0.jwt.JWT.class})
public class JWTAutoConfiguration {

//    @ConditionalOnProperty(
//            prefix = "spring.jwt",
//            name = "enabled"
//    )
    @ConfigurationProperties("spring.jwt")
    @Bean
    public JWTProperties jwtProperties() {
        return new JWTProperties();
    }

    @Bean
    @ConditionalOnBean(JWTProperties.class)
    public JWTTemplate jwtTemplate(JWTProperties jwtProperties) {
        JWTConfig jwtConfig = JWTConfig.build(jwtProperties);
        return new JWTTemplate(jwtConfig);
    }
}
