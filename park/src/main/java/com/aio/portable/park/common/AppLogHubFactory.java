package com.aio.portable.park.common;

import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.impl.console.ConsoleLog;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLog;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLog;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import org.springframework.stereotype.Component;

@Component
public class AppLogHubFactory extends LogHubFactory {

    @Override
    public LogHub build(String className) {
        LogHub logger = LogHub.build(
                new KafkaLog(className),
                new RabbitMQLog(className),
                new ConsoleLog(className),
                new Slf4JLog(className)
                )
                .setEnabledLevel(LevelEnum.INFORMATION);
                ;

        return logger;
    }
}








//@Component
//public class AppLogHubFactory extends BaseSingletonInstance {
//
//    protected AppLogHubFactory() {
//        LogHubFactory logHubFactory = new LogHubFactory() {
//            @Override
//            public LogHub build(String className) {
////        LogHub logger = LogHub.build(KafkaLog.build(className), RabbitMQLog.build(className), Slf4JLog.build(className));
////        LogHub logger = LogHub.build(ConsoleLog.build(className), Slf4JLog.build(className));
//                LogHub logger = LogHub.build(Slf4JLog.build(className));
//                return logger;
//            }
//        };
//        BaseSingletonInstance.importSingletonInstance(logHubFactory);
//    }
//
//    public static LogHub logHub(String className) {
//        return BaseSingletonInstance.singletonInstance(LogHubFactory.class).build(className);
//    }
//
//    public static LogHub logHub() {
//        return  BaseSingletonInstance.singletonInstance(LogHubFactory.class).build(1);
//    }
//
//
//}