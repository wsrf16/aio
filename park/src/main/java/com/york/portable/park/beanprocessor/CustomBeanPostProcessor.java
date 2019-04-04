package com.york.portable.park.beanprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBeanPostProcessor implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof Student) {
//            System.out.println("postProcessBeforeInitialization bean : " + beanName);
//        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        if (bean instanceof Student) {
//            System.out.println("postProcessAfterInitialization bean : " + beanName);
//        }
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
