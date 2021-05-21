package com.aio.portable.park.test;

import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class LogTest {
//    LogHub logger = AppLogHubFactory.singletonInstance()
//            .build("随便写哒")
//            .setBaseLevel(LevelEnum.DEBUG);
    LogHub log = AppLogHubFactory.staticBuild();

    public void logCase1() {
        log.i("abcdefghijklmnopqrstuvwxyz1介个是kafka");
        System.out.println("日志执行完成~~~~~~~~~~~~~");
    }



    public void logStyle() {
//        log.setAsync(false).e("qqqqqqq111111111111{}{}{}", new Object[]{"a","b","c"});
//        log.setAsync(false).i("qqqqqqq111111111111{}{}{}", new Object[]{"a", "b", "c"});
        log.setAsync(false).f("111111111111");
        log.setAsync(false).e("111111111111");
        log.setAsync(false).w("111111111111");
        log.setAsync(false).i("111111111111");
        log.setAsync(false).d("111111111111");
        log.setAsync(false).t("111111111111");
        log.setAsync(false).v("111111111111");
        log.setAsync(false).i("111111111111");


        log.f("fatal日志", "这里是待记录的日志内容");
        log.e("error日志", "这里是待记录的日志内容");
        log.i("info日志", "这里是待记录的日志内容");
        log.info("info日志", "这里是待记录的日志内容，info与i方法两者相同");
        log.d("debug日志", "这里是待记录的日志内容");
        log.debug("debug日志", "这里是待记录的日志内容");
        log.t("trace日志", "这里是待记录的日志内容");
        log.trace("trace日志", "这里是待记录的日志内容");

        log.i("打印时间2", "当前时间{}", null);
        log.i("打印时间3", "当前时间{}", new Object[]{new Date()});
        log.i("打印时间4", "当前时间{}", new Date());
        log.i("打印时间6", "当前时间{}", "1999 04 04 04");
        log.i("打印时间8", "当前时间", new Date());


        List<Integer> list = new ArrayList<>();
        try {
            list.add(0);
            list.add(1);
            int i = list.get(0);
            int result = i / i;
        } catch (Exception e) {
            log.e("error日志", list, e);
            log.error("error日志", list, e);
            log.w("warn日志", list, e);
            log.warn("warn日志", list, e);
            log.f("fatal日志", list, e);
            log.fatal("fatal日志", list, e);
        }
    }
}

