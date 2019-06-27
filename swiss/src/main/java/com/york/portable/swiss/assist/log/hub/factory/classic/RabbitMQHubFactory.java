package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.impl.kibana.rabbit.RabbitLogger;
import com.york.portable.swiss.assist.log.classic.impl.slf4j.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;

public class RabbitMQHubFactory implements LoggerHubFactory {
    protected static RabbitMQHubFactory instance = new RabbitMQHubFactory();

    public synchronized static RabbitMQHubFactory newInstance() {
        return instance == null ? new RabbitMQHubFactory() : instance;
    }

    protected RabbitMQHubFactory() {
    }

    public LoggerHubImp build(String className) {
        LoggerHubImp logger = LoggerHubImp.build(Slf4jLogger.build(className), RabbitLogger.build(className));
        return logger;
    }
}
