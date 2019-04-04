package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.RabbitLogger;
import com.york.portable.swiss.assist.log.classic.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHub;
import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;

public class RabbitHubFactory implements ILoggerHubFactory {
    private static RabbitHubFactory instance = new RabbitHubFactory();

    public synchronized static RabbitHubFactory newInstance() {
        return instance;
    }

    protected RabbitHubFactory() {
    }

    public LoggerHub build(String className) {
        LoggerHub logger = LoggerHub.build(RabbitLogger.build(className), Slf4jLogger.build(className));
        return logger;
    }
}
