package com.aio.portable.swiss.autoconfigure;

import com.aio.portable.swiss.autoconfigure.properties.JWTProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

//@Configuration
@ConditionalOnClass({com.auth0.jwt.JWT.class})
public class JWTAutoConfiguration {

//    @ConditionalOnClass({com.auth0.jwt.JWT.class})
    @ConditionalOnProperty(
            prefix = "spring.jwt",
            name = "issuer"
    )
    @Bean
    @ConfigurationProperties(prefix = "spring.jwt")
    public JWTProperties jwtProperties() {
        return new JWTProperties();
    }


}
