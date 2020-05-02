package com.aio.portable.swiss.suite.log.factory;

import com.aio.portable.swiss.suite.log.LogHub;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;

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
