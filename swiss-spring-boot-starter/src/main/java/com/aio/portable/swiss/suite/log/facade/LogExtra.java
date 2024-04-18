package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.suite.log.support.LevelEnum;

public interface LogExtra {
    float getSamplerRate();

    LogExtra setSamplerRate(float samplerRate);

    boolean isEnabled();

    LogExtra setEnabled(boolean enabled);

    LevelEnum getLevel();

    LogExtra setLevel(LevelEnum level);

    LogExtra setAsync(boolean async);
}
