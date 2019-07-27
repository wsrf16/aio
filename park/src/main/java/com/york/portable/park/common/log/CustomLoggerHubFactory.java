package com.york.portable.park.common.log;

import com.york.portable.swiss.assist.log.classic.impl.slf4j.Slf4jLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.ConsoleHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.KafkaHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.Slf4jHubFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomLoggerHubFactory extends Slf4jHubFactory { //KafkaHubFactory {
}

//@Configuration
//public class MyLoggerHubFactory implements LoggerHubFactory {
//
//    public final static String NAME = "myLoggerHubFactory";
//
//    protected static LoggerHubFactory instance;
//
//    public MyLoggerHubFactory(LoggerHubFactory consoleHubFactory) {
//        instance = consoleHubFactory;
//    }
//
//    public synchronized static LoggerHubFactory newInstance() {
//        return instance;
//    }
//
//    public LoggerHubImp build(String className) {
//        LoggerHubImp logger = instance.build(className);
//        return logger;
//    }
//}