package com.aio.portable.swiss.structure.log.hub.factory.classic;

import com.aio.portable.swiss.structure.log.classic.impl.console.ConsoleLogger;
import com.aio.portable.swiss.structure.log.hub.LogHub;
import com.aio.portable.swiss.structure.log.hub.factory.LogHubFactory;

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