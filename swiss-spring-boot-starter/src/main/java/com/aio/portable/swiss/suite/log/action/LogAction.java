package com.aio.portable.swiss.suite.log.action;

/**
 * LogAction
 */
public interface LogAction extends LogVerbose, LogInformation, LogTrace, LogDebug, LogWarning, LogError, LogFatal {
    void dispose();
}


