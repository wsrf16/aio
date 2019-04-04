package com.york.portable.swiss.assist.log.hub.factory;

import com.york.portable.swiss.assist.log.hub.LoggerHub;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class LoggerHubPool {
    private final static String DEFAULT_LOG_CLASSNAME = LoggerHubPool.class.getName();

    private static LoggerHubPool instance = new LoggerHubPool();

    private HashMap<String, LoggerHub> loggerHubMap = new HashMap<>();

    public static ILoggerHubFactory loggerHubBuilder;

    private LoggerHubPool() {
    }

    public synchronized static LoggerHubPool newInstance(ILoggerHubFactory loggerHubBuilder) {
        if (LoggerHubPool.loggerHubBuilder == null)
            LoggerHubPool.loggerHubBuilder = loggerHubBuilder;
        return instance;
    }

    private static String getClassNameFromException(Exception e) {
        String className = e.getStackTrace().length > 0 ? e.getStackTrace()[0].getClassName() : StringUtils.EMPTY;
        return className;
    }

    private static LoggerHub buildLoggerHubFromClassName(String className) {
        LoggerHub loggerHub = StringUtils.isEmpty(className) ? null : loggerHubBuilder.build(className);
        return loggerHub;
    }

    public LoggerHub putByExceptionIfAbsent(Exception e) {
        String className = getClassNameFromException(e);
        LoggerHub loggerHub = putIfAbsent(className);
        return loggerHub;
    }

    public LoggerHub putIfAbsent(String className) {
        LoggerHub loggerHub;
        if (loggerHubMap.containsKey(className))
            loggerHub = loggerHubMap.get(className);
        else {
            loggerHub = buildLoggerHubFromClassName(className);
            loggerHubMap.putIfAbsent(className, loggerHub);
        }
        return loggerHub;
    }

    private LoggerHub getLoggerHubExisted(Exception e) {
        String className = getClassNameFromException(e);
        LoggerHub loggerHub = getLoggerHubExisted(className);
        return loggerHub;
    }

    private LoggerHub getLoggerHubExisted(String className) {
        LoggerHub loggerHub = this.loggerHubMap.get(className);
        return loggerHub;
    }

    private boolean existLoggerHub(Exception e) {
        String className = getClassNameFromException(e);
        boolean exist = existLoggerHub(className);
        return exist;
    }

    private boolean existLoggerHub(String className) {
        boolean exist = this.loggerHubMap.containsKey(className);
        return exist;
    }
}
