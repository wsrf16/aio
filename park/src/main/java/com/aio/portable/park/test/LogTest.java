package com.aio.portable.park.test;

import com.aio.portable.park.config.AppLogHubFactory;
import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.structure.log.base.factory.LogHubFactory;
import org.springframework.stereotype.Component;

@Component
public class LogTest {
    LogHub dynamicLogger;

    public LogTest(LogHubFactory appLogHubFactory) {
        dynamicLogger = appLogHubFactory.build();
    }

    LogHub logger = AppLogHubFactory.singletonInstance().build("随便写哒");

    private void logCase1() {
        logger.i("abcdefghijklmnopqrstuvwxyz1介个是kafka");
        System.out.println("日志执行完成~~~~~~~~~~~~~");
    }


    private void logCase2() {
        LogHub logger = AppLogHubFactory.singletonInstance().buildAsync(getClass().getTypeName());
        logger.i("abcdefghijklmnopqrstuvwxyz11111111");
        logger.i("abcdefghijklmnopqrstuvwxyz22222222222");
    }
}
