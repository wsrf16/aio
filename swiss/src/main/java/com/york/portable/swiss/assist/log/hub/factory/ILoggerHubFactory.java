package com.york.portable.swiss.assist.log.hub.factory;

import com.york.portable.swiss.assist.log.hub.LoggerHub;

@FunctionalInterface
public interface ILoggerHubFactory {
    class Type {
        public final static String CONSOLE_HUB_FACTORY = "consoleHubFactory";
        public final static String LOG4J2_HUB_FACTORY = "log4j2HubFactory";
        public final static String SLF4J_HUB_FACTORY = "slf4jHubFactory";
        public final static String FILE_HUB_FACTORY = "fileHubFactory";
        public final static String RABBIT_HUB_FACTORY = "rabbitHubFactory";
    }

    LoggerHub build(String className);

    default LoggerHub build(Class clazz) {
        String className = clazz.getTypeName();
        LoggerHub logger = build(className);
        return logger;
    }

    default LoggerHub build() {
        Class clazz = this.getClass();
        return build(clazz);
    }

    default LoggerHub buildAsync(String className) {
        LoggerHub logger = build(className);
        logger.setAsync(true);
        return logger;
    }

    default LoggerHub buildAsync(Class clazz) {
        LoggerHub logger = build(clazz);
        logger.setAsync(true);
        return logger;
    }

}
