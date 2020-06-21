package com.aio.portable.park.config;

import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.impl.console.ConsoleLog;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLog;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLog;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;
import org.springframework.stereotype.Component;

@Component
public class AppLogHubFactory extends LogHubFactory {
    protected static AppLogHubFactory instance = new AppLogHubFactory();

//    public synchronized static AppLogHubFactory singletonInstance() {
//        return instance;
//    }

    protected AppLogHubFactory() {
    }

    public static LogHub logHub(String className) {
        LogHub logger = instance.build(className);
        return logger;
    }

    public static LogHub logHub() {
        LogHub logger = instance.build(1);
        return logger;
    }


    @Override
    public LogHub build(String className) {
        LogHub logger = LogHub.build(KafkaLog.build(className), RabbitMQLog.build(className), Slf4JLog.build(className));
//        LogHub logger = LogHub.build(ConsoleLog.build(className), Slf4JLog.build(className));
        return logger;
    }


}