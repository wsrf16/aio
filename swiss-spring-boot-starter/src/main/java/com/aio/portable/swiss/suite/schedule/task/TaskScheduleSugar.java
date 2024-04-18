package com.aio.portable.swiss.suite.schedule.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.time.Duration;
import java.util.concurrent.ScheduledFuture;

public class TaskScheduleSugar {

//    private ThreadPoolTaskScheduler scheduler;
//
//    private Runnable task;

//    public TaskScheduleProxy(ThreadPoolTaskScheduler scheduler, Runnable task) {
//        if (task == null)
//            throw new NullPointerException("Task is null.Please specify a Entry class.");
//
//        this.task = task;
//        this.scheduler = scheduler;
//
////        ExecutorService executorService = Executors.newex();
////        ThreadPoolTaskExecutors.
//    }

    // "00 00 00 * * ?"
    public static final ScheduledFuture<?> schedule(ThreadPoolTaskScheduler scheduler, Runnable task, String cron, boolean runAtOnce) {
        if (runAtOnce)
            scheduler.submit(task);
        return scheduler.schedule(task, new CronTrigger(cron));
    }

    public static final ScheduledFuture<?> scheduleAtFixedRate(ThreadPoolTaskScheduler scheduler, Runnable task, Duration period, boolean runAtOnce) {
        if (runAtOnce)
            scheduler.submit(task);
        return scheduler.scheduleAtFixedRate(task, period);
    }

//    public static final ScheduledFuture<?> scheduleWithFixedDelay(ThreadPoolTaskScheduler scheduler, Runnable task, Duration period, boolean runAtOnce) {
//        if (runAtOnce)
//            scheduler.submit(task);
//        return scheduler.scheduleWithFixedDelay(task, period);
//    }



}
