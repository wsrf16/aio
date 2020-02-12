package com.aio.portable.swiss.structure.log.base.factory;

import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.sugar.StackTraceSugar;

//@FunctionalInterface
public interface LogHubFactory {
    class Type {
        public final static String CONSOLE_HUB_FACTORY = "consoleHubFactory";
        public final static String LOG4J2_HUB_FACTORY = "log4j2HubFactory";
        public final static String SLF4J_HUB_FACTORY = "slf4jHubFactory";
        public final static String FILE_HUB_FACTORY = "fileHubFactory";
        public final static String RABBITMQ_HUB_FACTORY = "rabbitMQHubFactory";
    }

    LogHub build(String className);

    default LogHub build(Class clazz) {
        String className = clazz.getTypeName();
        LogHub logger = build(className);
        return logger;
    }

    default LogHub build() {
        String className = StackTraceSugar.Previous.getClassName();
        return build(className);
    }

    default LogHub buildAsync(String className) {
        LogHub logger = build(className);
        logger.setAsync(true);
        return logger;
    }

    default LogHub buildAsync(Class clazz) {
        LogHub logger = build(clazz);
        logger.setAsync(true);
        return logger;
    }

    default LogHub buildAsync() {
        Throwable throwable = new Throwable();
        int depth = 1;
        String className = throwable.getStackTrace()[depth].getClassName();
        return buildAsync(className);
    }

}
