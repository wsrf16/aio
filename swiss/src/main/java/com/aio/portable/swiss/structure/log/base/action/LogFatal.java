package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * If you have a pager, it goes off when one of these occurs.
 */
public interface LogFatal {
    void fatal(String fatal);

    default void fatal(String fatal, Object[] arguments) {
        fatal = RegexSugar.replace("\\{\\}", fatal, arguments);
        fatal(fatal);
    }

    void fatal(Exception e);

    void fatal(String summary, Exception e);

    void fatal(String summary, String fatal);

    default void fatal(String summary, String fatal, Object[] arguments) {
        fatal = RegexSugar.replace("\\{\\}", fatal, arguments);
        fatal(summary, fatal);
    }

    void fatal(String summary, String fatal, Exception e);

    <T> void fatal(String summary, T t);

    <T> void fatal(String summary, T t, Exception e);

    default void f(String fatal) {
        fatal(fatal);
    }

    default void f(String fatal, Object[] arguments) {
        fatal(fatal, arguments);
    }

    default void f(Exception e) {
        fatal(e);
    }

    default void f(String summary, Exception e) {
        fatal(summary, e);
    }

    default void f(String summary, String fatal) {
        fatal(summary, fatal);
    }

    default void f(String summary, String fatal, Object[] arguments) {
        fatal(summary, fatal, arguments);
    }

    default void f(String summary, String fatal, Exception e) {
        fatal(summary, fatal, e);
    }

    default <T> void f(String summary, T t) {
        fatal(summary, t);
    }

    default <T> void f(String summary, T t, Exception e) {
        fatal(summary, t, e);
    }
}
