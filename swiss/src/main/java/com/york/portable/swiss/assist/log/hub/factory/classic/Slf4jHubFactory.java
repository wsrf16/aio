package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.impl.slf4j.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;

public class Slf4jHubFactory implements LoggerHubFactory {
    protected static Slf4jHubFactory instance = new Slf4jHubFactory();

    public synchronized static Slf4jHubFactory newInstance() {
        return instance == null ? new Slf4jHubFactory() : instance;
    }

    protected Slf4jHubFactory() {
    }

    public LoggerHubImp build(String className) {
        LoggerHubImp logger = LoggerHubImp.build();
//        logger.addRegister(ConsoleLogger.build(className));
        logger.addRegister(Slf4jLogger.build(className));
        return logger;
    }
}
