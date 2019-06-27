package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.impl.console.ConsoleLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;

public class ConsoleHubFactory implements LoggerHubFactory {
    protected static ConsoleHubFactory instance = new ConsoleHubFactory();

    public synchronized static ConsoleHubFactory newInstance() {
        return instance == null ? new ConsoleHubFactory() : instance;
    }

    protected ConsoleHubFactory() {
    }

    public LoggerHubImp build(String className) {
        LoggerHubImp logger = LoggerHubImp.build(ConsoleLogger.build(className));
        return logger;
    }
}