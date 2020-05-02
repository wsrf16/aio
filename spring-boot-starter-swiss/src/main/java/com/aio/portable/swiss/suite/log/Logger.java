package com.aio.portable.swiss.suite.log;

import com.aio.portable.swiss.suite.log.parts.LevelEnum;

public interface Logger extends LogAction {
    float getSamplerRate();

    Logger setSamplerRate(float samplerRate);

    boolean isEnable();

    Logger setEnable(boolean enable);

    LevelEnum getBaseLevel();

    Logger setBaseLevel(LevelEnum baseLevel);

    Logger setAsync(boolean async);
}
