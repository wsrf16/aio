package com.aio.portable.park.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class Schedule {
    @Value("${business.taskMigrate.cron}")
    private String cron;

    @Autowired
    Runnable job;

    @Autowired
    ThreadPoolTaskScheduler scheduler;

    public void process() {
        scheduler.schedule(job, new CronTrigger(cron));
    }
}
