package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.schedule.task.TaskScheduleProxy;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@TestComponent
public class TaskScheduleProxyTest {

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Test
    public void foobar() {
        String cron = "* */30 * * * ?";
        boolean runAtOnce = false;
        TaskScheduleProxy taskScheduleProxy = new TaskScheduleProxy(threadPoolTaskScheduler, () -> System.out.println("---->"), cron, runAtOnce);
        taskScheduleProxy.start();
    }
}
