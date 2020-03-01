package com.aio.portable.swiss.suite.log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by York on 2017/11/22.
 */
public abstract class LogHubBody implements LogAction {
    protected List<LogSingle> loggers;

    protected LogHubBody(LogSingle logger) {
        loggers = new ArrayList<>();
        loggers.add(logger);
    }

    protected LogHubBody(List<LogSingle> loggers) {
        this.loggers = loggers;
    }

}
