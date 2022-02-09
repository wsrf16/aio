package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.schedule.task.AbstractTask;
import com.aio.portable.swiss.suite.schedule.task.ThreadPoolTimer;
import org.junit.Test;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class ThreadPoolTimerTest {
    @Test
    public void foobar() {
        ThreadPoolTimer threadPoolTimer = new ThreadPoolTimer(new AbstractTask() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }, "0 0 1 1 * ?", null, false);

        threadPoolTimer.start();
    }
}
