package com.york.portable.swiss.assist.log.hub.factory.baselogger;

import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;

public abstract class BaseLogger {
    protected LoggerHubImp logger;

//    public BaseLogger() {
//    }

    public BaseLogger(LoggerHubFactory loggerHubFactory) {
        this.logger = loggerHubFactory.build(BaseLogger.class);
    }

//    protected static synchronized LoggerHubImp build(LoggerHubFactory loggerHubFactory) {
//        LoggerHubImp logger = loggerHubFactory.build(BaseLogger.class);
//        return logger;
//    }
}
