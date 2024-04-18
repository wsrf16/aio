package com.aio.portable.park.timer;

import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.park.common.AppLogHubFactory;
import com.aio.portable.park.config.root.ApplicationConfig;
import com.aio.portable.park.endpoint.http.DynamicController;
import com.aio.portable.park.test.MyDatabaseTest;
import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.BizStatus;
import com.aio.portable.swiss.sugar.type.NumberSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.security.authorization.jwt.JWTTemplate;
import com.aio.portable.swiss.suite.timer.TimerCache;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class AutoRunner implements ApplicationRunner {
    @Autowired(required = false)
    MyDatabaseTest myDatabaseTest;

    @Autowired
    ApplicationConfig config;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RestTemplate skipSSLRestTemplate;

//    @Autowired
//    RedisTemplate redisTemplate;

//    @Autowired
//    HttpsProxyBean httpsProxyObject;

    @Autowired
    JWTTemplate jwtTemplate;

//    @Autowired
//    Swagger3Properties swagger3Properties;

    @Autowired
    DynamicController dynamicController;

    @Autowired
    UserInfoEntity userInfoEntity;

    LogHub log = AppLogHubFactory.staticBuild().setAsync(false);

    private static final Log commonLogging = LogFactory.getLog(AutoRunner.class);

    private static final Logger slf4jLogger = LoggerFactory.getLogger(AutoRunner.class);

    @Autowired
    ThreadPoolTaskScheduler scheduler;

    public static final void longTimeWork() {
        try {
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        BizStatus bizStatus = BaseBizStatusEnum.staticInvalid();

//        String cron = "*/30 * * * * ?";
//        boolean runAtOnce = false;
//        ScheduledFuture<?> future = TaskScheduleSugar.schedule(threadPoolTaskScheduler, () -> System.out.println("---->"), cron, runAtOnce);
//        try {
//            Object o = future.get();
//            Object o1 = future.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        String cron = "0/7 * * * * ?";
        Duration duration = Duration.ofSeconds(10);


//        Book book = new Book();
//        scheduler.schedule(() -> {
//            longTimeWork();
//            int x = NumberSugar.randomInt(0, 999);
////            System.out.println(x);
////            return x;
//            book.setId(x);
//        }, new CronTrigger(cron));
//        while (true) {
//            ThreadSugar.sleepSeconds(1);
////            System.out.println(DateTimeSugar.LocalDateTimeUtils.format(LocalDateTime.now(), DateTimeSugar.Format.FORMAT_NORMAL_LONG) + "-----------" + timerCache.getData());
//            System.out.println(DateTimeSugar.LocalDateTimeUtils.format(LocalDateTime.now(), DateTimeSugar.Format.FORMAT_NORMAL_LONG) + "-----------" + book.getId());
//        }



        TimerCache<Integer> timerCache = TimerCache.build(scheduler, () -> {
//            longTimeWork();
            int x = NumberSugar.randomInt(0, 999);
            return x;
        });
        timerCache.start(cron, true);

//        Integer data1 = timerCache.tryGetData(Duration.ofSeconds(2));
//        Integer data2 = timerCache.tryGetData(Duration.ofSeconds(10));

//        while (true) {
//            ThreadSugar.sleepSeconds(1);
//            System.out.println(DateTimeSugar.LocalDateTimeUtils.format(LocalDateTime.now(), DateTimeSugar.Format.FORMAT_NORMAL_LONG) + "-----------" + timerCache.getData());
//        }



//        System.out.println();





        if (myDatabaseTest != null)
            myDatabaseTest.blah();
//
        dynamicController.registerTest();
        System.out.println();
    }


//    @EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
    }


}





