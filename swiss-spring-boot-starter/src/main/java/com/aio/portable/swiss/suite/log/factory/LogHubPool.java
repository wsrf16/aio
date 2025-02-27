package com.aio.portable.swiss.suite.log.factory;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.log.facade.LogHub;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogHubPool {
    private static LogHubPool instance = new LogHubPool();

    private Map<String, LogHub> storage = new ConcurrentHashMap<>();

    public LogHubFactory logHubFactory;

    public LogHubFactory getLogHubFactory() {
        return logHubFactory;
    }

    public void setLogHubFactory(LogHubFactory logHubFactory) {
        this.logHubFactory = logHubFactory;
    }

    private LogHubPool() {
    }

    public static final LogHubPool buildLogHubPool() {
        LogHubPool pool = LogHubPool.buildLogHubPool(LogHubFactory.getSingleton());
        return pool;
    }

    public static final LogHubPool buildLogHubPool(LogHubFactory logHubFactory) {
        if (instance.getLogHubFactory() == null)
            synchronized (LogHubPool.class) {
                if (instance.getLogHubFactory() == null) {
                    instance.setLogHubFactory(logHubFactory);
                }
            }
        return instance;
    }

    private static String getClassNameFromException(Exception e) {
        String className = e.getStackTrace().length > 0 ? e.getStackTrace()[0].getClassName() : Constant.EMPTY;
        return className;
    }

    public LogHub get(String className) {
        if (className == null)
            throw new NullPointerException();
        LogHub logHub;
        synchronized (this) {
            if (storage.containsKey(className))
                logHub = storage.get(className);
            else {
                logHub = logHubFactory.build(className);
                storage.putIfAbsent(className, logHub);
            }
        }
        return logHub;
    }

    public LogHub get(Exception e) {
        String className = getClassNameFromException(e);
        LogHub logHub = get(className);
        return logHub;
    }


}
