package com.york.portable.swiss.assist.log.hub.factory.baselogger.classic;

import com.york.portable.swiss.assist.log.hub.factory.baselogger.BaseLogger;
import com.york.portable.swiss.assist.log.hub.factory.classic.RabbitMQHubFactory;

public abstract class RabbitBaseLogger extends BaseLogger {
    public RabbitBaseLogger() {
        super(RabbitMQHubFactory.newInstance());
    }
}