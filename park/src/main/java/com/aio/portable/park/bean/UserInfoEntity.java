package com.aio.portable.park.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class UserInfoEntity implements InitializingBean {
    static {
        System.out.println("--> " + UserInfoEntity.class);
    }

    private Integer id;

    private Integer nextId;

    private String name;

    public UserInfoEntity() {
        System.out.println("--> userInfoEntity Constructor");
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

    public static Integer getNextId1() {
        return 1;
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
        System.out.println("--> userInfoEntity afterPropertiesSet");
    }

    public static UserInfoEntity sample() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId(1);
        userInfoEntity.setName("Jerry");
        userInfoEntity.setNextId(2);
        return userInfoEntity;
    }

    public UserInfoEntity one() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId(1);
        userInfoEntity.setName("Jerry");
        userInfoEntity.setNextId(2);
        return userInfoEntity;
    }
}
