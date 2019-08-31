package com.aio.portable.park.beanprocessor;

import org.springframework.stereotype.Component;

@Component
public class UserInfoEntity {
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
}
