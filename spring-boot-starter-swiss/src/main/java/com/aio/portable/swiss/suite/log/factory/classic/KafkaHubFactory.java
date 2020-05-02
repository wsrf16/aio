package com.aio.portable.swiss.suite.log.factory.classic;

import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLog;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;

public class KafkaHubFactory extends LogHubFactory {
    protected static KafkaHubFactory instance = new KafkaHubFactory();

    public synchronized static KafkaHubFactory singletonInstance() {
        return instance;
    }

    protected KafkaHubFactory() {
    }

    @Override
    public LogHub build(String className) {
        LogHub logger = LogHub.build(Slf4JLog.build(className), KafkaLog.build(className));
        logger.setEnable(this.isEnable());
        logger.setBaseLevel(this.getLevel());
        return logger;
    }
}
