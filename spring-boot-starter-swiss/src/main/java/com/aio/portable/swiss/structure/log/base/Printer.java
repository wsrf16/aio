package com.aio.portable.swiss.structure.log.base;

@FunctionalInterface
public interface Printer {
    void println(String line);
    default void dispose(){}
}
