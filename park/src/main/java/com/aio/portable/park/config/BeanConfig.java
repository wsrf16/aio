package com.aio.portable.park.config;

import com.aio.portable.swiss.hamlet.bean.HamletBeanConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
public class BeanConfig extends HamletBeanConfig {
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return super.threadPoolTaskScheduler();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return super.objectMapper();
    }

    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean() {
        ServletListenerRegistrationBean servletListenerRegistrationBean = new ServletListenerRegistrationBean();
        servletListenerRegistrationBean.setListener(new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {
                HttpSessionListener.super.sessionCreated(se);
            }

            @Override
            public void sessionDestroyed(HttpSessionEvent se) {
                HttpSessionListener.super.sessionDestroyed(se);
            }
        });
        return servletListenerRegistrationBean;
    }

//    @Bean
//    public FilterRegistrationBean filterRegistrationBean() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new CustomFilter());
//        registration.addUrlPatterns("/*");
//        registration.setName("streamFilter");
//        return registration;
//    }


//    @Bean
    public FilterRegistrationBean corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
//        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.setFilter(new CorsFilter(source));
        registration.addUrlPatterns("/*");
        registration.setName("streamFilter");
        return registration;
    }

//    @Bean
    public CorsFilter corsFilter1() {
        //创建CorsConfiguration对象后添加配置
        CorsConfiguration config = new CorsConfiguration();
        //是否发送Cookie
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource corsConfigurationSource =
                new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config);
        return new CorsFilter(corsConfigurationSource);
    }
}
