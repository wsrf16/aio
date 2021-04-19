package com.aio.portable.park.aop;

import com.aio.portable.swiss.hamlet.interceptor.HamletJWTAuthenticationInterceptor;
import org.springframework.context.annotation.Configuration;

import java.util.List;

//@Configuration
public class CustomAuthenticationInterceptor extends HamletJWTAuthenticationInterceptor {
    @Override
    public List<String> getScanBasePackages() {
        return null;
    }
}
