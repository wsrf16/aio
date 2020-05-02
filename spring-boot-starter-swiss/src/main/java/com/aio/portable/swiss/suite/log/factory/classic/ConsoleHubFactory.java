package com.aio.portable.swiss.suite.log.factory.classic;

import com.aio.portable.swiss.suite.log.impl.console.ConsoleLog;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;

public class ConsoleHubFactory extends LogHubFactory {
    protected static ConsoleHubFactory instance = new ConsoleHubFactory();

    public synchronized static ConsoleHubFactory singletonInstance() {
        return instance == null ? new ConsoleHubFactory() : instance;
    }

    protected ConsoleHubFactory() {
    }

    @Override
    public LogHub build(String className) {
        LogHub logger = LogHub.build(ConsoleLog.build(className));
        logger.setEnable(this.isEnable());
        logger.setBaseLevel(this.getLevel());
        return logger;
    }
}