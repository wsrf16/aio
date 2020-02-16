package com.aio.portable.swiss.suite.log;

/**
 * Created by York on 2017/11/22.
 */
public abstract class LogSingleBody implements LogAction {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
