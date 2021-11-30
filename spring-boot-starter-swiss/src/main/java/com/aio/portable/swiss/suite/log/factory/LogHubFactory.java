package com.aio.portable.swiss.suite.log.factory;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLog;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLog;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLog;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;

import java.util.ArrayList;

public abstract class LogHubFactory {
    class Type {
        public final static String CONSOLE_HUB_FACTORY = "consoleHubFactory";
        public final static String LOG4J2_HUB_FACTORY = "log4j2HubFactory";
        public final static String SLF4J_HUB_FACTORY = "slf4jHubFactory";
        public final static String FILE_HUB_FACTORY = "fileHubFactory";
        public final static String RABBITMQ_HUB_FACTORY = "rabbitMQHubFactory";
        public final static String KAFKA_HUB_FACTORY = "kafkaHubFactory";
    }

    protected static LogHubFactory singleton;
    protected static boolean isInitial = false;
    boolean enabled = true;
    protected LevelEnum level = LevelEnum.ALL;

    public static boolean isInitial() {
        return isInitial;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }



    protected LogHubFactory() {
        synchronized (LogHubFactory.class) {
            LogHubFactory.singleton = LogHubFactory.singleton == null ? this : LogHubFactory.singleton;
            isInitial = true;
        }
    }

    public LogHub build(String className) {
        return detectAndBuild(className);
    }

    public final LogHub build(Class clazz) {
        String className = clazz.getTypeName();
        return build(className);
    }

    public final LogHub build() {
        String className = StackTraceSugar.Previous.getClassName();
        return build(className);
    }

    public final LogHub build(int previous) {
        String className = StackTraceSugar.Previous.getClassName(previous);
        return build(className);
    }

    public final static LogHub staticBuild(String className) {
        return singleton.build(className);
    }

    public final static LogHub staticBuild(Class clazz) {
        String className = clazz.getTypeName();
        return singleton.build(className);
    }

    public final static LogHub staticBuild() {
        String className = StackTraceSugar.Previous.getClassName();
        return singleton.build(className);
    }

    public final static LogHub staticBuild(int previous) {
        String className = StackTraceSugar.Previous.getClassName(previous);
        return singleton.build(className);
    }

    private static LogHub detectAndBuild(String className) {
        LogHubProperties properties = LogHubProperties.singletonInstance();

        ArrayList<LogSingle> list = new ArrayList<>(127);

        if (ConsoleLogProperties.singletonInstance().getEnabled()) {
            list.add(new ConsoleLog(className));
        }
        if (Slf4JLogProperties.singletonInstance().getEnabled()) {
            list.add(new Slf4JLog(className));
        }
        if (LogHubUtils.RabbitMQ.existDependency() && RabbitMQLogProperties.singletonInstance().getEnabled()) {
            list.add(new RabbitMQLog(className));
        }
        if (LogHubUtils.Kafka.existDependency() && KafkaLogProperties.singletonInstance().getEnabled()) {
            list.add(new KafkaLog(className));
        }

        LogHub log = LogHub.build(list);
        if (properties.getEnabled() != null)
            log.setEnabled(properties.getEnabled());
        if (properties.getLevel() != null)
            log.setLevel(properties.getLevel());
        if (properties.getSamplerRate() != null)
            log.setSamplerRate(properties.getSamplerRate());
        if (properties.getAsync() != null)
            log.setAsync(properties.getAsync());
        return log;
    }












}
