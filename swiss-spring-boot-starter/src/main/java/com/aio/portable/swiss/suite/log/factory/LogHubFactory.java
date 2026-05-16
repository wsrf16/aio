package com.aio.portable.swiss.suite.log.factory;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLog;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLog;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLog;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLog;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

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

    protected static final ConcurrentHashMap<String, LogHub> POOL = new ConcurrentHashMap<>();


    public static LogHubFactory defaultLogHubFactory() {
        return new LogHubFactory(){};
    }

    protected static LogHubFactory singleton; // = defaultLogHubFactory();;

//    static  {
//        if (singleton == null) {
//            Class<LogHubFactory> son = StackTraceSugar.Current.getSonClass();
//            if (son != null) {
//                singleton = ClassSugar.newInstance(son);
//            } else {
//                singleton = defaultLogHubFactory();
//            }
//        }
//    }

    protected static boolean initialized;
    boolean enabled = true;
    protected LevelEnum level = LevelEnum.ALL;

    public static boolean isInitialized() {
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



//    protected LogHubFactory() {
//        setSingleton(this);
//    }

//    public static void setSingleton(LogHubFactory singleton) {
//        synchronized (LogHubFactory.class) {
//            LogHubFactory.singleton = singleton;
//            initialized = true;
//        }
//    }

    public static LogHubFactory getSingleton() {
//        return LogHubFactory.singleton;
        if (singleton == null) {
            synchronized (LogHubFactory.class) {
                if (singleton == null) {
                    Class<LogHubFactory> son = StackTraceSugar.Current.getSonClass(1, 3);
                    if (son != null) {
                        singleton = ClassSugar.newInstance(son);
                        initialized = true;
                    }
                }
            }
        }
        return singleton == null ? defaultLogHubFactory() : singleton;
    }

    public LogHub build(String name) {
        return detectAndBuild(name);
    }

    public LogHub build(Class<?> clazz) {
        String className = clazz.getTypeName();
        return build(className);
    }

    public LogHub build() {
        String className = StackTraceSugar.Previous.getClassName();
        return build(className);
    }

    public LogHub build(int previous) {
        String className = StackTraceSugar.Previous.getClassName(previous);
        return build(className);
    }

    public static LogHub staticBuild(String name) {
        return LogHubFactory.getSingleton().build(name);
    }

    public static LogHub staticBuild(Class<?> clazz) {
        String className = clazz.getTypeName();
        return LogHubFactory.getSingleton().build(className);
    }

    public static LogHub staticBuild() {
        String className = StackTraceSugar.Previous.getClassName();
        return LogHubFactory.getSingleton().build(className);
    }

    public static LogHub staticBuild(int previous) {
        String className = StackTraceSugar.Previous.getClassName(previous);
        return LogHubFactory.getSingleton().build(className);
    }

    protected synchronized LogHub detectAndBuild(String name) {
        LogHub current = POOL.get(name);
        if (current != null) {
            return current;
        }

        ArrayList<LogSingle> list = new ArrayList<>(127);
        LogHub log = LogHub.build(list);

        if (LogHubProperties.exist()) {
            if (LogHubUtils.Spring.existDependency()) {
                if (!ConsoleLogProperties.exist() && !Slf4JLogProperties.exist()) {
                    list.add(new ConsoleLog(name));
                } else {
                    if (ConsoleLogProperties.exist() &&
                            ConsoleLogProperties.getSingleton().getEnabledOrDefault()) {
                        list.add(new ConsoleLog(name, ConsoleLogProperties.getSingleton()));
                    }
                    if (LogHubUtils.SLF4J.existDependency() &&
                            Slf4JLogProperties.exist() &&
                            Slf4JLogProperties.getSingleton().getEnabledOrDefault()
//                            &&
//                            !Slf4JLog.rewriteLoggerFactory()
                    ) {
                        list.add(new Slf4JLog(name, Slf4JLogProperties.getSingleton()));
                    }
                }
                if (LogHubUtils.RabbitMQ.existDependency() &&
                        RabbitMQLogProperties.exist() &&
                        RabbitMQLogProperties.getSingleton().getEnabledOrDefault()) {
                    if (RabbitMQLogProperties.getSingleton().getEsIndex() != null)
                        list.add(new RabbitMQLog(name, RabbitMQLogProperties.getSingleton()));
                }
                if (LogHubUtils.Kafka.existDependency() &&
                        KafkaLogProperties.exist() &&
                        KafkaLogProperties.getSingleton().getEnabledOrDefault()) {
                    if (KafkaLogProperties.getSingleton().getEsIndex() != null)
                        list.add(new KafkaLog(name, KafkaLogProperties.getSingleton()));
                }

            }
            LogHubProperties properties = LogHubProperties.getSingleton();
            log.setEnabled(properties.getEnabledOrDefault());
            log.setSamplerRate(properties.getSamplerRateOrDefault());
            log.setAsync(properties.getAsyncOrDefault());
            log.setLevel(properties.getLevelOrDefault());
        } else {
            list.add(new ConsoleLog(name));
//            if (ConsoleLogProperties.getSingleton().getEnabledOrDefault()) {
//            }
        }
        POOL.put(name, log);
        return log;
    }


}
