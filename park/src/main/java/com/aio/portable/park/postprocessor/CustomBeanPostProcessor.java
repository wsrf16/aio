package com.aio.portable.park.postprocessor;

import com.aio.portable.park.common.UserInfoEntity;
import com.aio.portable.swiss.suite.log.impl.elk.rabbit.RabbitMQLogProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserInfoEntity) {
            System.out.println("postProcessBeforeInitialization bean : " + beanName);
        }
        if (bean instanceof RabbitMQLogProperties) {
            System.out.println("postProcessBeforeInitialization bean : " + beanName);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof UserInfoEntity) {
            System.out.println("postProcessAfterInitialization bean : " + beanName);
        }
        if (bean instanceof RabbitMQLogProperties) {
            System.out.println("postProcessAfterInitialization bean : " + beanName);
        }
        return bean;
    }

//    public class Student {
//
////        @Value("${name}")
//        private String className;
//
//        public Student() {
//            System.out.println("constructor loading");
//        }
//
//        public void init() {
//            System.out.println("init loading");
//        }
//    }
}
