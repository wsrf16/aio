package com.aio.portable.park.config;

import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.classic.RabbitMQHubFactory;
import com.aio.portable.swiss.suite.log.factory.classic.Slf4jHubFactory;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import org.springframework.stereotype.Component;

@Component
public class AppLogHubFactory extends Slf4jHubFactory {
    public static LogHub logHub(String className) {
        LogHub logger = AppLogHubFactory.singletonInstance()
                .buildAsync(className);
        logger.setBaseLevel(LevelEnum.DEBUG);
        return logger;
    }

    public static LogHub logHub() {
        LogHub logger = AppLogHubFactory.singletonInstance()
                .buildAsync(1);
        logger.setBaseLevel(LevelEnum.DEBUG);
        return logger;
    }
}