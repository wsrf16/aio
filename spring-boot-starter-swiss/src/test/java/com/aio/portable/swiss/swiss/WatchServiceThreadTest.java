package com.aio.portable.swiss.swiss;

import com.aio.portable.swiss.suite.algorithm.identity.SerialNumber;
import com.aio.portable.swiss.suite.io.listen.WatchServiceThread;
import org.junit.Test;

import java.nio.file.Paths;

public class WatchServiceThreadTest {
    @Test
    public static void todo() {
        WatchServiceThread watchServiceOne = new WatchServiceThread("d:/AAAA/");
        watchServiceOne.setHandler((f) -> System.out.println(f.getPath()));
        watchServiceOne.listen();
    }
}
