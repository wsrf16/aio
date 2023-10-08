package com.aio.portable.park.config;

import com.aio.portable.park.intercept.CustomHandlerInterceptor;
import com.aio.portable.swiss.hamlet.interceptor.HamletWebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class CustomWebMvcConfigurer extends HamletWebMvcConfigurer {
    @Autowired
    CustomHandlerInterceptor customHandlerInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        super.addCorsMappings(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(customHandlerInterceptor);
    }
}