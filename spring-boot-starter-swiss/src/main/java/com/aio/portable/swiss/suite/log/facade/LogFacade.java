package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.suite.log.support.LevelEnum;

public interface LogFacade {
    float getSamplerRate();

    LogFacade setSamplerRate(float samplerRate);

    boolean isEnabled();

    LogFacade setEnabled(boolean enabled);

    LevelEnum getLevel();

    LogFacade setLevel(LevelEnum level);

    LogFacade setAsync(boolean async);
}
