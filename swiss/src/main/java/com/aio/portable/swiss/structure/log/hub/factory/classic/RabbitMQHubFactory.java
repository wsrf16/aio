package com.aio.portable.swiss.structure.log.hub.factory.classic;

import com.aio.portable.swiss.structure.log.classic.impl.kibana.rabbit.RabbitLogger;
import com.aio.portable.swiss.structure.log.classic.impl.slf4j.Slf4jLogger;
import com.aio.portable.swiss.structure.log.hub.LogHub;
import com.aio.portable.swiss.structure.log.hub.factory.LogHubFactory;

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
