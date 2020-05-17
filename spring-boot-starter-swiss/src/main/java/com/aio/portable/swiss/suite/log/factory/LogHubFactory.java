package com.aio.portable.swiss.suite.log.factory;

import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

//@FunctionalInterface
public abstract class LogHubFactory {
    class Type {
        public final static String CONSOLE_HUB_FACTORY = "consoleHubFactory";
        public final static String LOG4J2_HUB_FACTORY = "log4j2HubFactory";
        public final static String SLF4J_HUB_FACTORY = "slf4jHubFactory";
        public final static String FILE_HUB_FACTORY = "fileHubFactory";
        public final static String RABBITMQ_HUB_FACTORY = "rabbitMQHubFactory";
        public final static String KAFKA_HUB_FACTORY = "kafkaHubFactory";
    }

    boolean enable = true;

    LevelEnum level = LevelEnum.VERBOSE;



    public abstract LogHub build(String className);

    public LogHub build(Class clazz) {
        String className = clazz.getTypeName();
        LogHub logger = build(className);
        return logger;
    }

    public LogHub build() {
        String className = StackTraceSugar.Previous.getClassName();
        return build(className);
    }

    public LogHub build(int previous) {
        String className = StackTraceSugar.Previous.getClassName(previous);
        return build(className);
    }

    public LogHub buildAsync(String className) {
        LogHub logger = build(className);
        logger.setAsync(true);
        return logger;
    }

    public LogHub buildAsync(Class clazz) {
        LogHub logger = build(clazz);
        logger.setAsync(true);
        return logger;
    }

    public LogHub buildAsync() {
        String className = StackTraceSugar.Previous.getClassName();
        return buildAsync(className);
    }

    public LogHub buildAsync(int previous) {
        String className = StackTraceSugar.Previous.getClassName(previous);
        return buildAsync(className);
    }


//    public <T extends LogSingle> LogHub build(List<Class<T>> clazzList, String className) {
//        List<LogSingle> logSingleList = clazzList.stream().map(clazz -> {
//            try {
//                Constructor<T> constructor = clazz.getConstructor(new Class[]{String.class});
//                LogSingle t = constructor.newInstance(className);
//                return t;
//            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        }).collect(Collectors.toList());
//        LogHub logger = LogHub.build(logSingleList);
//        logger.setEnable(this.isEnable());
//        logger.setBaseLevel(this.getLevel());
//        return logger;
//    }
//
//    public LogHub _build(String className) {
//        LogHub logger = LogHub.build(new Slf4JLog(className));
//        logger.setEnable(this.isEnable());
//        logger.setBaseLevel(this.getLevel());
//        return logger;
//    }









    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }
}
