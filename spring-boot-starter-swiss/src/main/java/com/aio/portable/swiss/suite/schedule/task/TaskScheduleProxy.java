package com.aio.portable.swiss.suite.schedule.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.text.MessageFormat;

public class TaskScheduleProxy {

    private ThreadPoolTaskScheduler scheduler;

    private String cron; // = "00 00 00 * * ?";

    private Runnable abstractTask;

    private boolean runAtOnce = false;

    public TaskScheduleProxy(ThreadPoolTaskScheduler scheduler, Runnable abstractTask, String cron, boolean runAtOnce) {
        if (abstractTask == null)
            throw new NullPointerException(MessageFormat.format("{0} is null.Please specify a Entry class.", this));

        this.abstractTask = abstractTask;
        this.cron = cron;
        this.scheduler = scheduler;
        this.runAtOnce = runAtOnce;
    }

    public void start() {
        if (runAtOnce)
            abstractTask.run();
        scheduler.schedule(abstractTask, new CronTrigger(cron));
    }

}
