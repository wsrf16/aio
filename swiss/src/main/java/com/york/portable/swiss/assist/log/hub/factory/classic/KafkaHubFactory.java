package com.york.portable.swiss.assist.log.hub.factory.classic;

import com.york.portable.swiss.assist.log.classic.impl.kibana.kafka.KafkaLogger;
import com.york.portable.swiss.assist.log.classic.impl.slf4j.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LogHub;
import com.york.portable.swiss.assist.log.hub.factory.LogHubFactory;

public class KafkaHubFactory implements LogHubFactory {
    protected static KafkaHubFactory instance = new KafkaHubFactory();

    public synchronized static KafkaHubFactory singletonInstance() {
        return instance;
    }

    protected KafkaHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(Slf4jLogger.build(className), KafkaLogger.build(className));
        return logger;
    }
}
