package com.aio.portable.park.test;

import com.aio.portable.park.config.LogFactory;
import com.aio.portable.swiss.assist.log.hub.LogHub;
import com.aio.portable.swiss.assist.log.hub.factory.LogHubFactory;
import org.springframework.stereotype.Component;

@Component
public class LogTest {
    static LogHub staticLogger = LogFactory.singletonInstance().build();

    LogHub dynamicLogger;

    public LogTest(LogHubFactory logFactory) {
        dynamicLogger = logFactory.build();
    }

    private void logCase1() {
        LogHub logger = LogFactory.singletonInstance().build("随便写哒");
        logger.i("abcdefghijklmnopqrstuvwxyz1介个是kafka");
        System.out.println("日志执行完成~~~~~~~~~~~~~");
    }


    private void logCase2() {
        LogHub logger = LogFactory.singletonInstance().buildAsync(getClass().getTypeName());
        logger.i("abcdefghijklmnopqrstuvwxyz11111111");
        logger.i("abcdefghijklmnopqrstuvwxyz22222222222");
    }
}
