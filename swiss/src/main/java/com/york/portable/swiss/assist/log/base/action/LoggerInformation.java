package com.york.portable.swiss.assist.log.base.action;


/**
 * The lifeblood of operational intelligence - things happen.
 */
public interface LoggerInformation {
    void info(String info);

    <T> void info(T t);

    void info(String summary, String info);

    <T> void info(String summary, T t);

    default void i(String info) {
        info(info);
    }

    default <T> void i(T t) {
        info(t);
    }

    default void i(String summary, String info) {
        info(summary, info);
    }

    default <T> void i(String summary, T t) {
        info(summary, t);
    }
}
