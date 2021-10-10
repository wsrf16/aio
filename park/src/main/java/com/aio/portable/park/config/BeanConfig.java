package com.aio.portable.park.config;

import com.aio.portable.swiss.hamlet.bean.HamletBeanConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

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
//    @Lazy
//    public PropertySourceBeanDefinitionRegistryPostProcessor propertySourceBeanDefinitionRegistryPostProcessor(ConfigurableEnvironment environment) {
//        return new PropertySourceBeanDefinitionRegistryPostProcessor(environment) {
//            @Override
//            public Object intercept(String key, Object value) {
//                if (Objects.equals(value, "abc"))
//                    return ("v" + "222222");
//                if (Objects.equals(key, "swagger.api-info.title"))
//                    return (value + "222222");
//                if (Objects.equals(value, "对外接口在线文档"))
//                    return (value + "222222");
//                return value;
//            }
//        };
//    }
}
