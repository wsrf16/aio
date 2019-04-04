package com.york.portable.swiss.assist.log.hub.factory.baselogger.classic;

import com.york.portable.swiss.assist.log.hub.factory.baselogger.BaseLogger;
import com.york.portable.swiss.assist.log.hub.factory.classic.ConsoleHubFactory;

public abstract class ConsoleBaseLogger extends BaseLogger {
    public ConsoleBaseLogger(){
        super(ConsoleHubFactory.newInstance());
    }
}