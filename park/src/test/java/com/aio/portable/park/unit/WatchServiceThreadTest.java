package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.io.listen.WatchServiceThread;
import org.junit.Test;

public class WatchServiceThreadTest {
    @Test
    public void foobar() {
        WatchServiceThread watchServiceOne = new WatchServiceThread("d:/AAAA/");
        watchServiceOne.setHandler((f) -> System.out.println(f.getPath()));
        watchServiceOne.listen();
    }
}
