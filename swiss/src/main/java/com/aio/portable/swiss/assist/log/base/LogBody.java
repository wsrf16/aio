package com.aio.portable.swiss.assist.log.base;

/**
 * Created by York on 2017/11/22.
 */
public abstract class LogBody implements LogAction {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
