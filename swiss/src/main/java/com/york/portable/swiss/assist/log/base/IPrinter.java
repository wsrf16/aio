package com.york.portable.swiss.assist.log.base;

@FunctionalInterface
public interface IPrinter {
    void println(String line);
    default void dispose(){}
}
