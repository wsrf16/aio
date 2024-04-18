package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.interceptor.classic.annotation.jwt.HamletJWTAuthenticationAnnotationInterceptor;
import com.aio.portable.swiss.hamlet.interceptor.classic.annotation.jwt.RequiredJWTAuth;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

@Configuration
public class CustomAuthenticationAnnotationInterceptor extends HamletJWTAuthenticationAnnotationInterceptor {
    @Override
    public List<String> scanBasePackages() {
        return null;
    }

    @Override
    public List<Class<? extends Annotation>> annotations() {
        return Arrays.asList(RequiredJWTAuth.class);
    }


}
