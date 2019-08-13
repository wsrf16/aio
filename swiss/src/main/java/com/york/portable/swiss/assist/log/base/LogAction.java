package com.york.portable.swiss.assist.log.base;

import com.york.portable.swiss.assist.log.base.action.*;

/**
 * LoggerAction
 */
public interface LogAction extends LogVerbose, LogInformation, LogTrace, LogDebug, LogWarning, LogError, LogFatal {
    void dispose();
}


