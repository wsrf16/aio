package com.aio.portable.swiss.suite.log.factory.classic;

import com.aio.portable.swiss.suite.log.classic.impl.es.rabbit.RabbitLog;
import com.aio.portable.swiss.suite.log.classic.impl.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;

public class RabbitMQHubFactory implements LogHubFactory {
    protected static RabbitMQHubFactory instance = new RabbitMQHubFactory();

    public synchronized static RabbitMQHubFactory singletonInstance() {
        return instance;
    }

    protected RabbitMQHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(Slf4JLog.build(className), RabbitLog.build(className));
        return logger;
    }
}
