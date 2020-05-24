package com.aio.portable.swiss.suite.log;

import com.aio.portable.swiss.suite.log.parts.LevelEnum;

@FunctionalInterface
public interface Printer {
    void println(String line, LevelEnum level);
    default void dispose(){}
}
