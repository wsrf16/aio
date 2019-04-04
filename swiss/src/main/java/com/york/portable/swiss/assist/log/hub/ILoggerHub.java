package com.york.portable.swiss.assist.log.hub;

import com.york.portable.swiss.assist.log.base.ILogger;
import com.york.portable.swiss.assist.log.base.action.ILoggerAction;
import com.york.portable.swiss.assist.log.base.action.ILoggerAction;
import com.york.portable.swiss.assist.log.base.ILogger;

/**
 * Created by York on 2017/11/22.
 */
public interface ILoggerHub extends ILoggerAction {
    void addRegister(ILogger logger);
}
