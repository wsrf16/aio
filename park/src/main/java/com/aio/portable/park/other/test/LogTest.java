package com.aio.portable.park.other.test;

import com.aio.portable.park.common.CustomLogHubFactory;
import com.aio.portable.swiss.assist.log.hub.LogHub;
import org.springframework.stereotype.Component;

@Component
public class LogTest {
    static LogHub staticLogger = CustomLogHubFactory.singletonInstance().build();

    LogHub dynamicLogger;

    public LogTest(CustomLogHubFactory customLogHubFactory) {
        dynamicLogger = customLogHubFactory.build();
    }

    private void logCase1() {
        LogHub logger = CustomLogHubFactory.singletonInstance().build("随便写哒");
        logger.i("abcdefghijklmnopqrstuvwxyz1介个是kafka");
        System.out.println("日志执行完成~~~~~~~~~~~~~");
    }


    private void logCase2() {
        LogHub logger = CustomLogHubFactory.singletonInstance().buildAsync(getClass().getTypeName());
        logger.i("abcdefghijklmnopqrstuvwxyz11111111");
        logger.i("abcdefghijklmnopqrstuvwxyz22222222222");
    }
}
