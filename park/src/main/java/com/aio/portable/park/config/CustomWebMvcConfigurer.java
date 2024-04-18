package com.aio.portable.park.config;

import com.aio.portable.swiss.hamlet.interceptor.classic.HamletWebMvcConfigurer;
import com.aio.portable.swiss.spring.SpringContextHolder;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.Map;

@Configuration
public class CustomWebMvcConfigurer extends HamletWebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        super.addCorsMappings(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        Map<String, HandlerInterceptor> interceptors = SpringContextHolder.getApplicationContext().getBeansOfType(HandlerInterceptor.class);
        interceptors.values().forEach(c -> registry.addInterceptor(c));
    }
}