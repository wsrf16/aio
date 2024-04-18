package com.aio.portable.park.unit;

import com.aio.portable.swiss.suite.schedule.task.TaskScheduleSugar;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;

@TestComponent
public class TaskScheduleSugarTest {

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler;

    @Test
    public void foobar() {
        String cron = "*/30 * * * * ?";
        boolean runAtOnce = false;
        ScheduledFuture<?> future = TaskScheduleSugar.schedule(threadPoolTaskScheduler, () -> System.out.println("---->"), cron, runAtOnce);
        try {
            Object o = future.get();
            Object o1 = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
