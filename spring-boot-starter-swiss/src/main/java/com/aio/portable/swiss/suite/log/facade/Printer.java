package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.suite.log.support.LevelEnum;

@FunctionalInterface
public interface Printer {
    void println(String line, LevelEnum level);
    default void dispose(){}
}
