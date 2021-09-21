package com.aio.portable.swiss.suite.schedule.task;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.text.MessageFormat;

public class TaskScheduleProxy implements Task {

    private ThreadPoolTaskScheduler scheduler;

    private String cron; // = "00 00 00 * * ?";

    private AbstractTask abstractTask;

    private boolean runAtOnce = false;

    public TaskScheduleProxy(AbstractTask abstractTask, String cron, ThreadPoolTaskScheduler scheduler, boolean runAtOnce) {
        this.abstractTask = abstractTask;
        this.cron = cron;
        this.scheduler = scheduler;
        this.runAtOnce = runAtOnce;
    }

    public void run() {
        if (abstractTask == null)
            throw new NullPointerException(MessageFormat.format("{0} is null.Please specify a Entry class."
                    , this));
        if (runAtOnce)
            runAtOnce();
        start();
    }

    private void runAtOnce() {
        abstractTask.run();
    }

    private void start() {
        scheduler.schedule(() -> abstractTask.run(), new CronTrigger(cron));
    }

}
