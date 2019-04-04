package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.KafkaLogger;
import com.york.portable.swiss.assist.log.classic.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHub;
import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;

public class KafkaHubFactory implements ILoggerHubFactory {
    private static KafkaHubFactory instance = new KafkaHubFactory();

    public synchronized static KafkaHubFactory newInstance() {
        return instance;
    }

    protected KafkaHubFactory() {
    }

    public LoggerHub build(String className) {
        LoggerHub logger = LoggerHub.build(KafkaLogger.build(className), Slf4jLogger.build(className));
        return logger;
    }
}
