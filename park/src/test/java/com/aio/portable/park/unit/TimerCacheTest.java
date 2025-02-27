package com.aio.portable.park.unit;

import com.aio.portable.swiss.sugar.type.NumberSugar;
import com.aio.portable.swiss.suite.timer.TimerCache;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@TestComponent
public class TimerCacheTest {
    class OneHashMap extends HashMap<String, List<Integer>> {
    }
    public HashMap<String, List<Integer>> param = new HashMap<>();

    @Autowired
    private ThreadPoolTaskScheduler scheduler;

    @Test
    public void todo() {
        TimerCache<Integer> timerCache = TimerCache.build(scheduler, () -> {
            longTimeWork();
            int x = NumberSugar.randomInt(0, 999);
            return x;
        });
        String cron = "0/7 * * * * ?";
        timerCache.start(cron, true);
        Integer data1 = timerCache.tryGetData(Duration.ofSeconds(2));
        Integer data2 = timerCache.tryGetData(Duration.ofSeconds(10));
        System.out.println();
    }

    public static final void longTimeWork() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
