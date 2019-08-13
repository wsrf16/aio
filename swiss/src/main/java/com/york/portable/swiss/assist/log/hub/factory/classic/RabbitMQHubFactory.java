package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.impl.kibana.rabbit.RabbitLogger;
import com.york.portable.swiss.assist.log.classic.impl.slf4j.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LogHub;
import com.york.portable.swiss.assist.log.hub.factory.LogHubFactory;

public class RabbitMQHubFactory implements LogHubFactory {
    protected static RabbitMQHubFactory instance = new RabbitMQHubFactory();

    public synchronized static RabbitMQHubFactory singletonInstance() {
        return instance;
    }

    protected RabbitMQHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(Slf4jLogger.build(className), RabbitLogger.build(className));
        return logger;
    }
}
