package com.aio.portable.swiss.suite.log.slfj4jadaptor;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import org.slf4j.ILoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;

public abstract class LogHubFactoryAdaptorBinder implements LoggerFactoryBinder {
    protected static LoggerFactoryBinder singleton;
//    public static final String REQUESTED_API_VERSION = "1.7.36"; // 兼容版本

    public static ILoggerFactory defaultLoggerFactory() {
        return new Slf4JAdaptorLoggerFactory(){};
    }

    private final ILoggerFactory loggerFactory = defaultLoggerFactory();

//    protected LogHubFactoryAdaptorBinder() {
//        singleton = this;
//    }

    public static synchronized <IMPL> IMPL getSingleton() {
        if (singleton == null) {
            Class<LoggerFactoryBinder> son = StackTraceSugar.Current.getSonClass();
            if (son != null) {
                singleton = ClassSugar.newInstance(son);
            }
        }
        return (IMPL) singleton;
    }

    @Override
    public ILoggerFactory getLoggerFactory() {
        return loggerFactory;
    }

    @Override
    public String getLoggerFactoryClassStr() {
        return loggerFactory.getClass().getName();
    }

}
