package com.aio.portable.swiss.suite.log.factory;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.LogHub;
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

    protected static LogHubFactory singleton;

//    static {
//        if (SpringContextHolder.hasLoad()) {
//            try {
//                SpringContextHolder.getApplicationContext().getBeansOfType(RabbitMQLogProperties.class);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                SpringContextHolder.getApplicationContext().getBeansOfType(KafkaLogProperties.class);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            try {
//                throw new BeanInitializationException(SpringContextHolder.class.toString());
//            } catch (Exception e) {
//                if (e.getStackTrace()[0] != null){
//                    System.err.println(MessageFormat.format("{0}: {1}", NullPointerException.class.getTypeName(), SpringContextHolder.class.getTypeName()));
//                    System.err.println("\t\t" + e.getStackTrace()[0]);
//                }
////                e.printStackTrace();
//            }
//        }
//    }

    protected LogHubFactory() {
        synchronized (LogHubFactory.class) {
            LogHubFactory.singleton = LogHubFactory.singleton == null ? this : LogHubFactory.singleton;
        }
    }

    boolean enable = true;

    LevelEnum level = LevelEnum.VERBOSE;

    public abstract LogHub build(String className);

    public LogHub build(Class clazz) {
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

    public LogHub buildSync(String className) {
        LogHub logHub = build(className);
        logHub.setAsync(false);
        return logHub;
    }

    public LogHub buildSync(Class clazz) {
        LogHub logHub = build(clazz);
        logHub.setAsync(false);
        return logHub;
    }

    public LogHub buildSync() {
        LogHub logHub = build();
        logHub.setAsync(false);
        return logHub;
    }

    public LogHub buildSync(int previous) {
        LogHub logHub = build(previous);
        logHub.setAsync(false);
        return logHub;
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

    public final static LogHub staticBuildSync(String className) {
        LogHub logHub = singleton.build(className);
        logHub.setAsync(false);
        return logHub;
    }

    public final static LogHub staticBuildSync(Class clazz) {
        LogHub logHub = singleton.build(clazz);
        logHub.setAsync(false);
        return logHub;
    }

    public final static LogHub staticBuildSync() {
        LogHub logHub = singleton.build();
        logHub.setAsync(false);
        return logHub;
    }

    public final static LogHub staticBuildSync(int previous) {
        LogHub logHub = singleton.build(previous);
        logHub.setAsync(false);
        return logHub;
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
