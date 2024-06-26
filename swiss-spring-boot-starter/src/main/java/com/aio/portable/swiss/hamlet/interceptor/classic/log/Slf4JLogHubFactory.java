package com.aio.portable.swiss.hamlet.interceptor.classic.log;

import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLog;

public class Slf4JLogHubFactory extends LogHubFactory {
    @Override
    public LogHub build(String name) {
        return LogHub.build(new Slf4JLog(name));
    }
}
