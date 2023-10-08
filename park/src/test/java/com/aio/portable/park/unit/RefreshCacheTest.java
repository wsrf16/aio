package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.timer.RefreshCache;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;

@TestComponent
public class RefreshCacheTest {
    class OneHashMap extends HashMap<String, List<Integer>> {
    }
    public HashMap<String, List<Integer>> param = new HashMap<>();

    @Test
    public void todo() {
        RefreshCache<Integer> refreshCache = RefreshCache.build(Duration.ofMinutes(30), () -> {
            longTimeWork();
            return 8888;
        });
        refreshCache.start();
        Integer data1 = refreshCache.tryGetData(Duration.ofSeconds(2));
        Integer data2 = refreshCache.tryGetData(Duration.ofSeconds(10));
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
