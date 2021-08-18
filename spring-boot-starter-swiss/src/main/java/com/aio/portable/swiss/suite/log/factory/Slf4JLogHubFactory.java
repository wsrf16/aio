package com.aio.portable.swiss.suite.log.factory;

import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;

public class Slf4JLogHubFactory extends LogHubFactory {
    @Override
    public LogHub build(String className) {
        return LogHub.build(new Slf4JLog(className));
    }
}
