package com.york.portable.swiss.assist.log.hub.factory;

import com.york.portable.swiss.assist.log.hub.LoggerHubImp;

@FunctionalInterface
public interface LoggerHubFactory {
    class Type {
        public final static String CONSOLE_HUB_FACTORY = "consoleHubFactory";
        public final static String LOG4J2_HUB_FACTORY = "log4j2HubFactory";
        public final static String SLF4J_HUB_FACTORY = "slf4jHubFactory";
        public final static String FILE_HUB_FACTORY = "fileHubFactory";
        public final static String RABBIT_HUB_FACTORY = "rabbitHubFactory";
    }

    LoggerHubImp build(String className);

    default LoggerHubImp build(Class clazz) {
        String className = clazz.getTypeName();
        LoggerHubImp logger = build(className);
        return logger;
    }

    default LoggerHubImp build() {
        Class clazz = this.getClass();
        return build(clazz);
    }

    default LoggerHubImp buildAsync(String className) {
        LoggerHubImp logger = build(className);
        logger.setAsync(true);
        return logger;
    }

    default LoggerHubImp buildAsync(Class clazz) {
        LoggerHubImp logger = build(clazz);
        logger.setAsync(true);
        return logger;
    }

}
