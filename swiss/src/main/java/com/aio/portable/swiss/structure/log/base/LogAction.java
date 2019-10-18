package com.aio.portable.swiss.structure.log.base;

import com.aio.portable.swiss.structure.log.base.action.*;

/**
 * LoggerAction
 */
public interface LogAction extends LogVerbose, LogInformation, LogTrace, LogDebug, LogWarning, LogError, LogFatal {
    void dispose();
}


