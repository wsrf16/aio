package com.york.portable.swiss.assist.log.hub.factory;

import com.york.portable.swiss.assist.log.hub.LogHub;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

public class LogHubPool {
    private final static String CLASSNAME = LogHubPool.class.getName();

    private static LogHubPool instance = new LogHubPool();

    private HashMap<String, LogHub> storage = new HashMap<>();

    public LogHubFactory logHubFactory;

    private LogHubPool() {
    }

    public static LogHubPool getSingleton(LogHubFactory logHubFactory) {
        if (instance.logHubFactory == null)
            synchronized (LogHubPool.class) {
                if (instance.logHubFactory == null)
                    instance.logHubFactory = logHubFactory;
            }
        return instance;
    }

    private static String getClassNameFromException(Exception e) {
        String className = e.getStackTrace().length > 0 ? e.getStackTrace()[0].getClassName() : StringUtils.EMPTY;
        return className;
    }

    public LogHub putIfAbsent(String className) {
        if (className == null)
            throw new NullPointerException();
        LogHub logHub;
        synchronized (this) {
            if (storage.containsKey(className))
                logHub = storage.get(className);
            else {
                logHub = logHubFactory.buildAsync(className);
                storage.putIfAbsent(className, logHub);
            }
        }
        return logHub;
    }

    public LogHub putIfAbsent(Exception e) {
        String className = getClassNameFromException(e);
        LogHub logHub = putIfAbsent(className);
        return logHub;
    }


}
