package com.york.portable.swiss.assist.log.hub.factory.baselogger.classic;

import com.york.portable.swiss.assist.log.hub.factory.baselogger.BaseLogger;
import com.york.portable.swiss.assist.log.hub.factory.classic.Slf4jHubFactory;

public abstract class Slf4jBaseLogger extends BaseLogger {
    public Slf4jBaseLogger(){
        super(Slf4jHubFactory.newInstance());
    }
}