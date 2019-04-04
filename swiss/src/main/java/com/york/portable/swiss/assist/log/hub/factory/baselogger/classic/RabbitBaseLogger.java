package com.york.portable.swiss.assist.log.hub.factory.baselogger.classic;

import com.york.portable.swiss.assist.log.hub.factory.baselogger.BaseLogger;
import com.york.portable.swiss.assist.log.hub.factory.classic.RabbitHubFactory;

public abstract class RabbitBaseLogger extends BaseLogger {
    public RabbitBaseLogger() {
        super(RabbitHubFactory.newInstance());
    }
}