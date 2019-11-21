package com.aio.portable.swiss.structure.log.base.action;


import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * The lifeblood of operational intelligence - things happen.
 */
public interface LogInformation {
    void info(String info);

    default void info(String info, Object[] arguments) {
        info = RegexSugar.replace("\\{\\}", info, arguments);
        info(info);
    }

    <T> void info(T t);

    void info(String summary, String info);

    default void info(String summary, String info, Object[] arguments) {
        info = RegexSugar.replace("\\{\\}", info, arguments);
        info(summary, info);
    }

    <T> void info(String summary, T t);

    default void i(String info) {
        info(info);
    }

    default void i(String info, Object[] arguments) {
        info(info, arguments);
    }

    default <T> void i(T t) {
        info(t);
    }

    default void i(String summary, String info) {
        info(summary, info);
    }

    default void i(String summary, String info, Object[] arguments) {
        info(summary, info, arguments);
    }

    default <T> void i(String summary, T t) {
        info(summary, t);
    }
}
