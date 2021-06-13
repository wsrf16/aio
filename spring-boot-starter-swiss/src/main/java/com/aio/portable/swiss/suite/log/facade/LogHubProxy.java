package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.suite.log.support.LevelEnum;

public interface LogHubProxy extends LogAction, LogFacade {
    float getSamplerRate();

    LogHub setSamplerRate(float samplerRate);

    boolean isEnabled();

    LogHub setEnabled(boolean enabled);

    LevelEnum getEnabledLevel();

    LogHub setEnabledLevel(LevelEnum enabledLevel);

    LogHub setAsync(boolean async);
}
