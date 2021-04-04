package com.aio.portable.swiss.suite.log;

import com.aio.portable.swiss.suite.log.parts.LevelEnum;

public interface Logger extends LogAction {
    float getSamplerRate();

    Logger setSamplerRate(float samplerRate);

//    boolean beEnable();

//    Logger setEnable(boolean enable);

    LevelEnum getEnabledLevel();

    Logger setEnabledLevel(LevelEnum enabledLevel);

    Logger setAsync(boolean async);
}
