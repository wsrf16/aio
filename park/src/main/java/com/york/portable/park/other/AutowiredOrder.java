package com.york.portable.park.other;

import com.york.portable.park.beanprocessor.UserInfoEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class AutowiredOrder implements InitializingBean {

    @Autowired
    UserInfoEntity userInfoEntity4th;

    UserInfoEntity userInfoEntity2nd = new UserInfoEntity();

    public AutowiredOrder(UserInfoEntity userInfoEntity1st) {
        UserInfoEntity userInfoEntity3th = userInfoEntity1st;
    }

    @PostConstruct
    public void init6th() {
        System.out.println("init");
    }

    @PreDestroy
    public void destroy8th() {
        System.out.println("destroy");
    }

    @Autowired
    public UserInfoEntity userInfoEntity(UserInfoEntity userInfoEntity5th) {
        return new UserInfoEntity();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet7");
    }
}
