package com.aio.portable.swiss.schedule;

import com.aio.portable.swiss.schedule.task.Task;
import com.aio.portable.swiss.schedule.task.AbstractTask;
import com.aio.portable.swiss.schedule.task.TaskScheduleProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class Launcher {
    private String cron;// = "00 00 00 * * ?";

    private ThreadPoolTaskScheduler scheduler;

    private boolean runAtOnce;

    AbstractTask abstractTask;

    String mode;

    public Launcher(AbstractTask task, String cron, ThreadPoolTaskScheduler scheduler, boolean runAtOnce) {
        this.cron = cron;
        this.scheduler = scheduler;
        this.runAtOnce = runAtOnce;
        this.abstractTask = task;
        this.mode = Mode.SCHEDULE;
    }

    public Launcher(AbstractTask task) {
        this.abstractTask = task;
        this.mode = Mode.ONCE;
    }

    class Mode {
        public static final String SCHEDULE = "schedule";
        public static final String ONCE = "once";
    }

    public void start() {
        startBy(mode);
    }

    private void startBy(String mode) {
        Task task;
        switch (mode) {
            case Mode.SCHEDULE:
                task = new TaskScheduleProxy(abstractTask, scheduler, cron, true);
                ;
                break;
            case Mode.ONCE:
            default:
                task = abstractTask;
                break;
        }
        task.run();
    }

}
