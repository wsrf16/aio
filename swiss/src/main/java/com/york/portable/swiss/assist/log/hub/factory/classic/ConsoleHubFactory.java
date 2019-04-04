package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.ConsoleLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHub;
import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

public class ConsoleHubFactory implements ILoggerHubFactory {
    private static ConsoleHubFactory instance = new ConsoleHubFactory();

    public synchronized static ConsoleHubFactory newInstance() {
        return instance;
    }

    protected ConsoleHubFactory() {
    }

    public LoggerHub build(String className) {
        LoggerHub logger = LoggerHub.build(ConsoleLogger.build(className));
        return logger;
    }
}
