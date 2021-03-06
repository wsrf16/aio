package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.suite.log.action.*;

/**
 * LoggerAction
 */
public interface LogAction extends LogVerbose, LogInformation, LogTrace, LogDebug, LogWarning, LogError, LogFatal {
    void dispose();
}


