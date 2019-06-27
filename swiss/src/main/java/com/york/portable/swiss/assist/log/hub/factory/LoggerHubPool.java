package com.york.portable.swiss.assist.log.hub.factory;

import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class LoggerHubPool {
    private final static String DEFAULT_LOG_CLASSNAME = LoggerHubPool.class.getName();

    private static LoggerHubPool instance = new LoggerHubPool();

    private HashMap<String, LoggerHubImp> loggerHubMap = new HashMap<>();
    public LoggerHubImp putIfAbsent(String className) {
        LoggerHubImp loggerHub;
//        loggerHub= loggerHubMap.containsKey(className) ?
//                loggerHubMap.get(className) : loggerHubMap.putIfAbsent(className, buildLoggerHubFromClassName(className));
        if (loggerHubMap.containsKey(className))
            loggerHub = loggerHubMap.get(className);
        else {
            loggerHub = buildLoggerHubFromClassName(className);
            loggerHubMap.putIfAbsent(className, loggerHub);
        }
        return loggerHub;
    }

    public LoggerHubFactory loggerHubBuilder;

    private LoggerHubPool() {
    }

    public synchronized static LoggerHubPool newInstance(LoggerHubFactory loggerHubBuilder) {
        if (instance.loggerHubBuilder == null)
            instance.loggerHubBuilder = loggerHubBuilder;
        return instance;
    }

    private static String getClassNameFromException(Exception e) {
        String className = e.getStackTrace().length > 0 ? e.getStackTrace()[0].getClassName() : StringUtils.EMPTY;
        return className;
    }

    private LoggerHubImp buildLoggerHubFromClassName(String className) {
        // async
        LoggerHubImp loggerHub = StringUtils.isBlank(className) ? null : loggerHubBuilder.buildAsync(className);
        return loggerHub;
    }

    public LoggerHubImp putByExceptionIfAbsent(Exception e) {
        String className = getClassNameFromException(e);
        LoggerHubImp loggerHub = putIfAbsent(className);
        return loggerHub;
    }



//    private LoggerHubImp getLoggerHubExisted(Exception e) {
//        String className = getClassNameFromException(e);
//        LoggerHubImp loggerHub = getLoggerHubExisted(className);
//        return loggerHub;
//    }

//    private LoggerHubImp getLoggerHubExisted(String className) {
//        LoggerHubImp loggerHub = loggerHubMap.get(className);
//        return loggerHub;
//    }

//    private boolean existLoggerHub(Exception e) {
//        String className = getClassNameFromException(e);
//        boolean exist = existLoggerHub(className);
//        return exist;
//    }

//    private boolean existLoggerHub(String className) {
//        boolean exist = this.loggerHubMap.containsKey(className);
//        return exist;
//    }
}
