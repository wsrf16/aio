package com.aio.portable.swiss.suite.log.factory.classic;

import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.classic.impl.log4j.Log4JLog;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;

public class Log4jHubFactory extends LogHubFactory {
    protected static Log4jHubFactory instance = new Log4jHubFactory();

    public synchronized static Log4jHubFactory singletonInstance() {
        return instance;
    }

    protected Log4jHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(Log4JLog.build(className));
        logger.setEnable(this.isEnable());
        logger.setLevel(this.getLevel());
        return logger;
    }
}
