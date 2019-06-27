package com.york.portable.swiss.assist.log.hub;

import com.york.portable.swiss.assist.log.base.LoggerRoot;
import com.york.portable.swiss.assist.log.base.LoggerAction;

/**
 * Created by York on 2017/11/22.
 */
public interface LoggerHub extends LoggerAction {
    void addRegister(LoggerRoot logger);
}
