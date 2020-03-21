package com.aio.portable.park.config;

import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.classic.RabbitMQHubFactory;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import org.springframework.stereotype.Component;

@Component
public class AppLogHubFactory extends RabbitMQHubFactory {
    public static LogHub logHub(String className) {
        LogHub logger = AppLogHubFactory.singletonInstance()
                .buildAsync(className)
                .setBaseLevel(LevelEnum.DEBUG);
        return logger;
    }

    public static LogHub logHub() {
        LogHub logger = AppLogHubFactory.singletonInstance()
                .buildAsync(1)
                .setBaseLevel(LevelEnum.DEBUG);
        return logger;
    }
}