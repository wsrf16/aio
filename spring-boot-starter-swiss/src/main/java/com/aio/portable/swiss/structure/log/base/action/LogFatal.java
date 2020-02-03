package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * If you have a pager, it goes off when one of these occurs.
 */
public interface LogFatal {
    void fatal(String message);

    default void fatal(String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        fatal(message);
    }

    void fatal(Exception e);

    void fatal(String summary, Exception e);

    void fatal(String summary, String message);

    default void fatal(String summary, String message, Object[] arguments) {
        message = RegexSugar.replace("\\{\\}", message, arguments);
        fatal(summary, message);
    }

    void fatal(String summary, String message, Exception e);

    <T> void fatal(String summary, T t);

    <T> void fatal(String summary, T t, Exception e);

    <T> void fatal(String summary, String message, T t, Exception e);

    default void f(String message) {
        fatal(message);
    }

    default void f(String message, Object[] arguments) {
        fatal(message, arguments);
    }

    default void f(Exception e) {
        fatal(e);
    }

    default void f(String summary, Exception e) {
        fatal(summary, e);
    }

    default void f(String summary, String message) {
        fatal(summary, message);
    }

    default void f(String summary, String message, Object[] arguments) {
        fatal(summary, message, arguments);
    }

    default void f(String summary, String message, Exception e) {
        fatal(summary, message, e);
    }

    default <T> void f(String summary, T t) {
        fatal(summary, t);
    }

    default <T> void f(String summary, T t, Exception e) {
        fatal(summary, t, e);
    }

    default <T> void f(String summary, String message, T t, Exception e) {
        fatal(summary, message, t, e);
    }
}
