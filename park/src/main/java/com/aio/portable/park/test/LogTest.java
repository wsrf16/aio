package com.aio.portable.park.test;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LogTest {
    LogHub dynamicLogger;

    public LogTest(LogHubFactory logHubFactory) {
        dynamicLogger = logHubFactory.build();
    }

//    LogHub logger = AppLogHubFactory.singletonInstance()
//            .build("随便写哒")
//            .setBaseLevel(LevelEnum.DEBUG);
    LogHub logger = AppLogHubFactory.staticBuild();

    public void logCase1() {
        logger.i("abcdefghijklmnopqrstuvwxyz1介个是kafka");
        System.out.println("日志执行完成~~~~~~~~~~~~~");
    }



    public void logStyle() {
        logger.f("fatal日志", "这里是待记录的日志内容");
        logger.e("error日志", "这里是待记录的日志内容");
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

