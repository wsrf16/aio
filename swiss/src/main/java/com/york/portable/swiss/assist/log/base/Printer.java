package com.york.portable.swiss.assist.log.base;

@FunctionalInterface
public interface Printer {
    void println(String line);
    default void dispose(){}
}
