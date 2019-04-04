package com.york.portable.swiss.assist.log.hub.factory.baselogger;

import com.york.portable.swiss.assist.log.hub.LoggerHub;
import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;

public abstract class BaseLogger {
    protected LoggerHub logger;

    public BaseLogger() {
    }

    public BaseLogger(ILoggerHubFactory loggerHubFactory) {
        build(loggerHubFactory);
    }

    protected synchronized void build(ILoggerHubFactory loggerHubFactory) {
        logger = (loggerHubFactory != null && logger == null) ? loggerHubFactory.build(this.getClass()) : logger;
    }
}
