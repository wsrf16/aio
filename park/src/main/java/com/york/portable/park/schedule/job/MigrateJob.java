package com.york.portable.park.schedule.job;

import com.york.portable.park.common.CustomLogHubFactory;
import com.york.portable.swiss.assist.log.hub.LogHub;
import org.springframework.context.annotation.Configuration;

import java.text.MessageFormat;

@Configuration
public class MigrateJob implements Runnable {
    LogHub log = CustomLogHubFactory.singletonInstance().build();


    private static int monthFromNow = -3;
    private static int amount = 10;

    public void run() {
        try {
            log.info("开始转移历史任务", MessageFormat.format("转移内容：当前{0}个月前{1}个月的所有任务", monthFromNow, amount));
//            List<UsedCarTask> usedCarTaskList = usedCarTaskService.selectAfterMonth(monthFromNow, amount);
//            usedCarTaskService.migrateData(usedCarTaskList);
            log.info("结束转移历史任务", MessageFormat.format("转移内容：当前{0}个月前{1}个月的所有任务", monthFromNow, amount));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("转移失败", e);
        }
    }

}
