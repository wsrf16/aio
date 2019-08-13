package com.york.portable.park.beanprocessor;

import org.springframework.stereotype.Component;

@Component
public class UserInfoEntity {
    private String name;

    public UserInfoEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
