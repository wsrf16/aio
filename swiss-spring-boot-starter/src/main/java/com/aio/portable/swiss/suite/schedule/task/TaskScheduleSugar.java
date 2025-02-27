package com.aio.portable.swiss.suite.schedule.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.time.Duration;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;

public class TaskScheduleSugar {

    public static class ScheduleResult<T> {
        Future<T> future;
        ScheduledFuture<T> scheduledFuture;

        public Future<T> getFuture() {
            return future;
        }

        public void setFuture(Future<T> future) {
            this.future = future;
        }

        public ScheduledFuture<T> getScheduledFuture() {
            return scheduledFuture;
        }

        public void setScheduledFuture(ScheduledFuture<T> scheduledFuture) {
            this.scheduledFuture = scheduledFuture;
        }
    }

    // "00 00 00 * * ?"
    public static final ScheduledFuture<?> schedule(ThreadPoolTaskScheduler scheduler, Runnable task, String cron, boolean atOnce) {
        if (atOnce)
            scheduler.submit(task);
        return scheduler.schedule(task, new CronTrigger(cron));
    }

    public static final ScheduledFuture<?> schedule(ThreadPoolTaskScheduler scheduler, Runnable task, String cron, boolean atOnce, Consumer<Future<?>> futureHandler) {
        if (atOnce) {
            Future<?> submit = scheduler.submit(task);
            futureHandler.accept(submit);
        }
        ScheduledFuture<?> scheduledFuture = scheduler.schedule(task, new CronTrigger(cron));
        futureHandler.accept(scheduledFuture);
        return scheduledFuture;
    }

    public static final ScheduledFuture<?> scheduleAtFixedRate(ThreadPoolTaskScheduler scheduler, Runnable task, Duration period, boolean atOnce) {
        if (atOnce)
            scheduler.submit(task);
        return scheduler.scheduleAtFixedRate(task, period);
    }

    public static final void scheduleAtFixedRate(ThreadPoolTaskScheduler scheduler, Runnable task, Duration period, boolean atOnce, Consumer<Future<?>> futureHandler) {
        if (atOnce) {
            Future<?> submit = scheduler.submit(task);
            futureHandler.accept(submit);
        }
        ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(task, period);
        futureHandler.accept(scheduledFuture);
    }

//    public static final ScheduledFuture<?> scheduleWithFixedDelay(ThreadPoolTaskScheduler scheduler, Runnable task, Duration period, boolean atOnce) {
//        if (atOnce)
//            scheduler.submit(task);
//        return scheduler.scheduleWithFixedDelay(task, period);
//    }



}
