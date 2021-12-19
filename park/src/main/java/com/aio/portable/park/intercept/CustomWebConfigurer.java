package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.interceptor.HamletWebMvcConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class CustomWebConfigurer extends HamletWebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        super.addCorsMappings(registry);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
//        registry.addInterceptor(new CustomHandlerInterceptor());
//        registry.addInterceptor(new CustomAuthenticationInterceptor());
    }
}