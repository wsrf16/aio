package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHub;
import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;
import org.springframework.stereotype.Service;

public class Slf4jHubFactory implements ILoggerHubFactory {
    private static Slf4jHubFactory instance = new Slf4jHubFactory();

    public synchronized static Slf4jHubFactory newInstance() {
        return instance;
    }

    protected Slf4jHubFactory() {
    }

    public LoggerHub build(String className) {
        LoggerHub logger = LoggerHub.build();
//        logger.addRegister(ConsoleLogger.build(className));
        logger.addRegister(Slf4jLogger.build(className));
        return logger;
    }
}
