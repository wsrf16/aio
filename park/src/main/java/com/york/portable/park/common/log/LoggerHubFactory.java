package com.york.portable.park.common.log;

import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.ConsoleHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.KafkaHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.RabbitHubFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerHubFactory extends KafkaHubFactory {

    public final static String NAME = "loggerHubFactory";

//    public static ILoggerHubFactory instance = ConsoleHubFactory.newInstance();
//    public static ILoggerHubFactory instance = KafkaHubFactory.newInstance();

}
