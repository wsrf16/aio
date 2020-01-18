package com.aio.portable.swiss.structure.log.base.factory.classic;

import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.structure.log.base.classic.impl.log4j.Log4JLog;
import com.aio.portable.swiss.structure.log.base.factory.LogHubFactory;

public class Log4jHubFactory implements LogHubFactory {
    protected static Log4jHubFactory instance = new Log4jHubFactory();

    public synchronized static Log4jHubFactory singletonInstance() {
        return instance;
    }

    protected Log4jHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(Log4JLog.build(className));
        return logger;
    }
}
