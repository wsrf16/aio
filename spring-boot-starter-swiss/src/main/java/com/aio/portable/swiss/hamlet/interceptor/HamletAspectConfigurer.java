package com.aio.portable.swiss.hamlet.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

public abstract class HamletAspectConfigurer {
    private final static String[] METHODS = new String[] { "GET", "POST", "PUT", "DELETE" };


    @Bean
    public static CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // domain
        corsConfiguration.addAllowedOrigin("*");
        // header
        corsConfiguration.addAllowedHeader("*");
        // method
        corsConfiguration.addAllowedMethod("*");
        // cookie :false
        corsConfiguration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }
}
