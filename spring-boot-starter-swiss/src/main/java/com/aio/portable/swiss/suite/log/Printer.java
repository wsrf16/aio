package com.aio.portable.swiss.suite.log;

@FunctionalInterface
public interface Printer {
    void println(String line);
    default void dispose(){}
}
