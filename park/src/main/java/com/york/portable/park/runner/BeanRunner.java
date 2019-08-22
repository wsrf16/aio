package com.york.portable.park.runner;

//import com.york.portable.park.common.log.InjectedBaseLogger;

import com.york.portable.swiss.assist.log.hub.LogHub;
import com.york.portable.park.common.CustomLogHubFactory;
import com.york.portable.park.schedule.Schedule;
import com.york.portable.swiss.resource.ResourceUtils;
import com.york.portable.swiss.resource.StreamClassLoader;
import com.york.portable.swiss.sugar.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
//@TargetDataSource("slave")
public class BeanRunner implements ApplicationRunner {
    @Autowired
    Schedule schedule;

//    @Autowired
//    MybatisTest mybatisTest;

    //    Object o = SpringContextUtil.getBean("logKafkaProperties");
    static LogHub staticLogger = CustomLogHubFactory.singletonInstance().build();

    LogHub dynamicLogger;

    public BeanRunner(CustomLogHubFactory customLogHubFactory) {
        dynamicLogger = customLogHubFactory.build();
    }

    private void todo() throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ResourceUtils.getResourcesInClassFile("com/york/portable/swiss/sandbox/a中文/AA.class");
        ResourceUtils.getResourcesByClassName("com.york.portable.swiss.sandbox.Wood");
        ResourceUtils.getResourcesByClass(Book.class);


        String jarPath = new File("console-1.0-SNAPSHOT.jar").getAbsolutePath();
        String resourceInJar = "/sandbox/console/Book.class";
        URL url = ResourceUtils.getResourceInJar(jarPath, resourceInJar);
        List<URL> urlList = ResourceUtils.getResourcesInJar(jarPath);

        {
            String className = ResourceUtils.path2FullName(resourceInJar);
            Class clazz = StreamClassLoader.buildByFile("console-1.0-SNAPSHOT.jar").loadClassByBinary(className);
            className = "com.york.portable.swiss.sandbox.Wood";
            Class clazz1 = StreamClassLoader.buildByFile("target/classes/com/york/portable/swiss/sandbox/Wood.class").loadClassByBinary(className);
            Class clazz2 = StreamClassLoader.buildByResource("com/york/portable/swiss/sandbox/Wood.class").loadClassByBinary(className);
            Object obj = clazz.newInstance();
            Object obj1 = clazz.newInstance();
        }
        {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:/" + jarPath)});
            Class clazz = urlClassLoader.loadClass("sandbox.console.Book");
            Object obj = clazz.newInstance();
            Object obj1 = clazz.newInstance();
        }
    }

    @Override
    public void run(ApplicationArguments applicationArguments) {
//        mybatisTest.main();
        try {
            todo();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        dynamicLogger.i("beanrunner.java", "ttttttt");
        staticLogger.i("beanrunner.java", "ttttttt");



        Stream<Integer> stream = Stream.iterate(1, n -> n + 1).limit(1000);

        List<Integer> list = stream.collect(Collectors.toList());
        StreamUtils.split(list, 8);

        schedule.process();

        logCase1();
        logCase2();
//        fff("111");
//        mq();
    }

    private void logCase1() {
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
