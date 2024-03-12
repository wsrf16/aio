package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.interceptor.HamletJWTAuthenticationInterceptor;
import com.aio.portable.swiss.suite.security.authorization.jwt.annotation.JWTAuth;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CustomAuthenticationInterceptor extends HamletJWTAuthenticationInterceptor {
    @Override
    public List<String> scanBasePackages() {
        return null;
    }

    @Override
    public List<Class<? extends Annotation>> annotations() {
        return Arrays.asList(JWTAuth.class);
    }


}
