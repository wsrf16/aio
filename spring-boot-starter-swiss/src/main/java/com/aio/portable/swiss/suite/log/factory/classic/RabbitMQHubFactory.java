package com.aio.portable.swiss.suite.log.factory.classic;

import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLog;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;

public class RabbitMQHubFactory extends LogHubFactory {
    protected static RabbitMQHubFactory instance = new RabbitMQHubFactory();

    public synchronized static RabbitMQHubFactory singletonInstance() {
        return instance;
    }

    protected RabbitMQHubFactory() {
    }

    @Override
    public LogHub build(String className) {
        LogHub logger = LogHub.build(Slf4JLog.build(className), RabbitMQLog.build(className));
        logger.setEnable(this.isEnable());
        logger.setBaseLevel(this.getLevel());
        return logger;
    }
}
