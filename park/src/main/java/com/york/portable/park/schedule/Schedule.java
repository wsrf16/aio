package com.york.portable.park.schedule;

import com.york.portable.park.schedule.job.migrate.MigrateJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
public class Schedule {
    @Value("${business.taskMigrate.cron}")
    private String migrateCron;

    @Autowired
    MigrateJob migrateJob;

    @Autowired
    ThreadPoolTaskScheduler migrateThreadPoolTaskScheduler;

    public void process() {
        migrateThreadPoolTaskScheduler.schedule(migrateJob, new CronTrigger(migrateCron));
    }
}
