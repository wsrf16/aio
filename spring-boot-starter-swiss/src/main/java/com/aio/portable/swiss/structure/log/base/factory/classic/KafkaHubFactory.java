package com.aio.portable.swiss.structure.log.base.factory.classic;

import com.aio.portable.swiss.structure.log.base.classic.impl.kibana.kafka.KafkaLog;
import com.aio.portable.swiss.structure.log.base.classic.impl.slf4j.Slf4JLog;
import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.structure.log.base.factory.LogHubFactory;

public class KafkaHubFactory implements LogHubFactory {
    protected static KafkaHubFactory instance = new KafkaHubFactory();

    public synchronized static KafkaHubFactory singletonInstance() {
        return instance;
    }

    protected KafkaHubFactory() {
    }

    public LogHub build(String className) {
        LogHub logger = LogHub.build(Slf4JLog.build(className), KafkaLog.build(className));
        return logger;
    }
}
