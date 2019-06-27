package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.impl.kibana.kafka.KafkaLogger;
import com.york.portable.swiss.assist.log.classic.impl.slf4j.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;

public class KafkaHubFactory implements LoggerHubFactory {
    protected static KafkaHubFactory instance = new KafkaHubFactory();

    public synchronized static KafkaHubFactory newInstance() {
        return instance == null ? new KafkaHubFactory() : instance;
    }

    protected KafkaHubFactory() {
    }

    public LoggerHubImp build(String className) {
        LoggerHubImp logger = LoggerHubImp.build(Slf4jLogger.build(className), KafkaLogger.build(className));
        return logger;
    }
}
