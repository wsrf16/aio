package com.aio.portable.swiss.suite.log.factory;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLog;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLog;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLog;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;

import java.util.ArrayList;

public abstract class LogHubFactory {
    class Type {
        public static final String CONSOLE_HUB_FACTORY = "consoleHubFactory";
        public static final String LOG4J2_HUB_FACTORY = "log4j2HubFactory";
        public static final String SLF4J_HUB_FACTORY = "slf4jHubFactory";
        public static final String FILE_HUB_FACTORY = "fileHubFactory";
        public static final String RABBITMQ_HUB_FACTORY = "rabbitMQHubFactory";
        public static final String KAFKA_HUB_FACTORY = "kafkaHubFactory";
    }

//    public static class DefaultLogHubFactory extends LogHubFactory {
////        @Override
////        public LogHub build(String className) {
////            return super.build(className).setAsync(false);
////        }
//
//    }

    public static final LogHubFactory defaultLogHubFactory() {
        return new LogHubFactory(){};
    }

    static  {
        singleton = defaultLogHubFactory();
    }

    protected static LogHubFactory singleton; // = defaultLogHubFactory();;
    protected static boolean initialized;
    boolean enabled = true;
    protected LevelEnum level = LevelEnum.ALL;

    public static boolean isInitial() {
        return initialized;
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
        setSingleton(this);
    }

    public static final void setSingleton(LogHubFactory singleton) {
        synchronized (LogHubFactory.class) {
            LogHubFactory.singleton = singleton;
            initialized = true;
        }
    }

    public static final LogHubFactory getSingleton() {
        return LogHubFactory.singleton;
    }

    public LogHub build(String name) {
        return detectAndBuild(name);
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

    public static final LogHub staticBuild(String name) {
        return singleton.build(name);
    }

    public static final LogHub staticBuild(Class clazz) {
        String className = clazz.getTypeName();
        return singleton.build(className);
    }

    public static final LogHub staticBuild() {
        String className = StackTraceSugar.Previous.getClassName();
        return singleton.build(className);
    }

    public static final LogHub staticBuild(int previous) {
        String className = StackTraceSugar.Previous.getClassName(previous);
        return singleton.build(className);
    }

    private static LogHub detectAndBuild(String name) {
        ArrayList<LogSingle> list = new ArrayList<>(127);
        LogHub log = LogHub.build(list);

        if (LogHubProperties.initialized()) {
            if (LogHubUtils.Spring.existDependency()) {
                if (ConsoleLogProperties.getSingleton().getDefaultEnabledIfAbsent()) {
                    list.add(new ConsoleLog(name));
                }
                if (LogHubUtils.SLF4J.existDependency() && Slf4JLogProperties.getSingleton().getDefaultEnabledIfAbsent() && !Slf4JLog.rewriteLoggerFactory()) {
                    list.add(new Slf4JLog(name));
                }
                if (LogHubUtils.RabbitMQ.existDependency() && RabbitMQLogProperties.getSingleton().getDefaultEnabledIfAbsent()) {
                    if (RabbitMQLogProperties.getSingleton().getEsIndex() != null)
                        list.add(new RabbitMQLog(name));
                }
                if (LogHubUtils.Kafka.existDependency() && KafkaLogProperties.getSingleton().getDefaultEnabledIfAbsent()) {
                    if (KafkaLogProperties.getSingleton().getEsIndex() != null)
                        list.add(new KafkaLog(name));
                }

                LogHubProperties properties = LogHubProperties.getSingleton();
                log.setEnabled(properties.getDefaultEnabledIfAbsent());
                log.setSamplerRate(properties.getDefaultSamplerRateIfAbsent());
                log.setAsync(properties.getDefaultAsyncIfAbsent());
                log.setLevel(properties.getDefaultLevelIfAbsent());
            }
        } else {
            if (ConsoleLogProperties.getSingleton().getDefaultEnabledIfAbsent()) {
                list.add(new ConsoleLog(name));
            }
        }
        return log;
    }


}
