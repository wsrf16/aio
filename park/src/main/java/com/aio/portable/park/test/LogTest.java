package com.aio.portable.park.test;

import com.aio.portable.park.config.AppLogHubFactory;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LogTest {
    LogHub dynamicLogger;

    public LogTest(LogHubFactory appLogHubFactory) {
        dynamicLogger = appLogHubFactory.build();
    }

//    LogHub logger = AppLogHubFactory.singletonInstance()
//            .build("随便写哒")
//            .setBaseLevel(LevelEnum.DEBUG);
    LogHub logger = AppLogHubFactory.logHub();

    public void logCase1() {
        logger.i("abcdefghijklmnopqrstuvwxyz1介个是kafka");
        System.out.println("日志执行完成~~~~~~~~~~~~~");
    }


    public void logCase2() {
        LogHub logger = AppLogHubFactory.singletonInstance().buildAsync(getClass().getTypeName());
        logger.i("abcdefghijklmnopqrstuvwxyz11111111");
        logger.i("abcdefghijklmnopqrstuvwxyz22222222222");
    }

    public void logStyle() {
        logger.i("info日志", "这里是待记录的日志内容");
        logger.info("info日志", "这里是待记录的日志内容，info与i方法两者相同");
        logger.d("debug日志", "这里是待记录的日志内容");
        logger.debug("debug日志", "这里是待记录的日志内容");
        logger.t("trace日志", "这里是待记录的日志内容");
        logger.trace("trace日志", "这里是待记录的日志内容");
        List<Integer> list = new ArrayList<>();
        try {
            list.add(0);
            list.add(1);
            int i = list.get(0);
            int result = i / i;
        } catch (Exception e) {
            logger.e("error日志", list, e);
            logger.error("error日志", list, e);
            logger.w("warn日志", list, e);
            logger.warn("warn日志", list, e);
            logger.f("fatal日志", list, e);
            logger.fatal("fatal日志", list, e);
        }
    }
}

