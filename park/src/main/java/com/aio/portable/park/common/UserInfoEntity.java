package com.aio.portable.park.common;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class UserInfoEntity implements InitializingBean {
    private Integer id;

    private Integer nextId;

    private String name;

    public UserInfoEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String echo(String name) {
        return name;
    }

    public String echo(String name, int i) {
        return name + i;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("userInfoEntity afterPropertiesSet");
    }

    public static UserInfoEntity sample() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId(1);
        userInfoEntity.setName("Jerry");
        userInfoEntity.setNextId(2);
        return userInfoEntity;
    }
}
