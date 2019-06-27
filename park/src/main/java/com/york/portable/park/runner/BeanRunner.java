package com.york.portable.park.runner;

//import com.york.portable.park.common.log.InjectedBaseLogger;
import com.york.portable.swiss.assist.log.hub.LoggerHub;
import com.york.portable.park.common.log.CustomLoggerHubFactory;
import com.york.portable.park.parkdb.dao.master.mapper.BookMapper;
import com.york.portable.park.schedule.Schedule;
import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
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

@Component
//@TargetDataSource("slave")
public class BeanRunner implements ApplicationRunner {
    @Autowired
    BookMapper bookMapper;

    @Autowired
    Schedule schedule;

//    Object o = SpringContextUtil.getBean("logKafkaProperties");
    static LoggerHub staticLogger = CustomLoggerHubFactory.newInstance().build(BeanRunner.class);

    LoggerHub dynamicLogger;

    public BeanRunner(CustomLoggerHubFactory customLoggerHubFactory) {
        dynamicLogger = customLoggerHubFactory.build(this.getClass());
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
        dynamicLogger.i("beanrunner.java", "ttttttt");
        staticLogger.i("beanrunner.java", "ttttttt");
//        List<Book> bookList = bookMapper.getAll();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -3);
        Date first = DateTimeUtils.CalendarUtils.getFirstDayOfMonth(calendar);
        Date last = DateTimeUtils.CalendarUtils.getLastDayOfMonth(calendar);

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
        LoggerHubImp logger = CustomLoggerHubFactory.newInstance().build("随便写哒");
        logger.i("abcdefghijklmnopqrstuvwxyz1介个是kafka");
        System.out.println("日志执行完成~~~~~~~~~~~~~");
    }



    private void logCase2() {
        LoggerHubImp logger = CustomLoggerHubFactory.newInstance().buildAsync(getClass().getTypeName());
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
