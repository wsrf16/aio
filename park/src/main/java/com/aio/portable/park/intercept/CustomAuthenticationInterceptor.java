package com.aio.portable.park.intercept;

import com.aio.portable.swiss.hamlet.interceptor.HamletJWTAuthenticationInterceptor;

import java.util.List;

//@Configuration
public class CustomAuthenticationInterceptor extends HamletJWTAuthenticationInterceptor {
    @Override
    public List<String> getScanBasePackages() {
        return null;
    }
}
