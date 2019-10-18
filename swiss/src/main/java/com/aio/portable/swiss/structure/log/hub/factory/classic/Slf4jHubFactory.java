package com.aio.portable.swiss.structure.log.hub.factory.classic;

import com.aio.portable.swiss.structure.log.classic.impl.slf4j.Slf4jLogger;
import com.aio.portable.swiss.structure.log.hub.LogHub;
import com.aio.portable.swiss.structure.log.hub.factory.LogHubFactory;

public class Slf4jHubFactory implements LogHubFactory {
    protected static Slf4jHubFactory instance = new Slf4jHubFactory();

    public synchronized static Slf4jHubFactory singletonInstance() {
        return instance;
    }

    protected Slf4jHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(Slf4jLogger.build(className));
        return logger;
    }
}
