package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.impl.console.ConsoleLogger;
import com.york.portable.swiss.assist.log.hub.LogHub;
import com.york.portable.swiss.assist.log.hub.factory.LogHubFactory;

public class ConsoleHubFactory implements LogHubFactory {
    protected static ConsoleHubFactory instance = new ConsoleHubFactory();

    public synchronized static ConsoleHubFactory newInstance() {
        return instance == null ? new ConsoleHubFactory() : instance;
    }

    protected ConsoleHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(ConsoleLogger.build(className));
        return logger;
    }
}