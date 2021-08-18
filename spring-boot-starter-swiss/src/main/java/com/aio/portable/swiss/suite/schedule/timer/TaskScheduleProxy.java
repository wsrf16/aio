package com.aio.portable.swiss.suite.schedule.timer;

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
            throw new NullPointerException(MessageFormat.format("{0}{1} is null.Please specify a Entry class."
                    , TaskScheduleProxy.class.getTypeName()
                    , abstractTask.getClass().getSimpleName()));
        if (runAtOnce)
            runAtOnce();
        startAlways();
    }

    private void runAtOnce() {
        abstractTask.run();
    }

    private void startAlways() {
        scheduler.schedule(() -> abstractTask.run(), new CronTrigger(cron));
    }

}
