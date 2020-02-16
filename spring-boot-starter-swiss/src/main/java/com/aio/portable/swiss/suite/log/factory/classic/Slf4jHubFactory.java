package com.aio.portable.swiss.suite.log.factory.classic;

import com.aio.portable.swiss.suite.log.classic.impl.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;

public class Slf4jHubFactory implements LogHubFactory {
    protected static Slf4jHubFactory instance = new Slf4jHubFactory();

    public synchronized static Slf4jHubFactory singletonInstance() {
        return instance;
    }

    protected Slf4jHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(Slf4JLog.build(className));
        return logger;
    }
}
