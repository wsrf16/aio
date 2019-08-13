package com.york.portable.park.runner;

//import com.york.portable.park.common.log.InjectedBaseLogger;
import com.york.portable.park.other.MybatisTest;
import com.york.portable.swiss.assist.log.hub.LogHub;
import com.york.portable.park.common.CustomLogHubFactory;
import com.york.portable.park.schedule.Schedule;
import com.york.portable.swiss.sugar.DateTimeUtils;
import com.york.portable.swiss.sugar.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//@Component
//@TargetDataSource("slave")
public class BeanRunner implements ApplicationRunner {
    @Autowired
    Schedule schedule;

    @Autowired
    MybatisTest mybatisTest;

//    Object o = SpringContextUtil.getBean("logKafkaProperties");
    static LogHub staticLogger = CustomLogHubFactory.singletonInstance().build(BeanRunner.class);

    LogHub dynamicLogger;

    public BeanRunner(CustomLogHubFactory customLoggerHubFactory) {
        dynamicLogger = customLoggerHubFactory.build(this.getClass());
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        mybatisTest.main();

        dynamicLogger.i("beanrunner.java", "ttttttt");
        staticLogger.i("beanrunner.java", "ttttttt");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date first = DateTimeUtils.CalendarUtils.getFirstDayOfMonth(calendar).getTime();
        Date last = DateTimeUtils.CalendarUtils.getLastDayOfMonth(calendar).getTime();

        DateTimeUtils.Format.convertDate2String("yyyy-MM-dd 00:00:00", first);
        DateTimeUtils.Format.convertDate2String("yyyy-MM-dd 23:59:59", last);

        Stream<Integer> stream = Stream.iterate(1, n -> n + 1).limit(1000);

        List<Integer> list = stream.collect(Collectors.toList());
        StreamUtils.split(list, 8);

        schedule.process();

        logCase1();
        logCase2();
//        fff("111");
//        mq();
    }

    private void logCase1(){
        LogHub logger = CustomLogHubFactory.singletonInstance().build("随便写哒");
        logger.i("abcdefghijklmnopqrstuvwxyz1介个是kafka");
        System.out.println("日志执行完成~~~~~~~~~~~~~");
    }



    private void logCase2() {
        LogHub logger = CustomLogHubFactory.singletonInstance().buildAsync(getClass().getTypeName());
        logger.i("abcdefghijklmnopqrstuvwxyz11111111");
        logger.i("abcdefghijklmnopqrstuvwxyz22222222222");
    }



//    @Autowired
//    private AmqpTemplate rabbitTemplate;
//    public String mq() {
//        String msg = MessageFormat.format("现在的时间是{0}", DateTimeUtils.convertUnix2DateTime(DateTimeUtils.nowUnix()));
//        rabbitTemplate.convertAndSend("application-log-queue", msg);
//        return msg;
//    }
}
