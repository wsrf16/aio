package com.aio.portable.swiss.structure.log.base.action;

/**
 * If you have a pager, it goes off when one of these occurs.
 */
public interface LogFatal {
    void fatal(String fatal);

    void fatal(Exception e);

    void fatal(String summary, Exception e);

    void fatal(String summary, String fatal);

    void fatal(String summary, String fatal, Exception e);

    <T> void fatal(String summary, T t);

    <T> void fatal(String summary, T t, Exception e);

    default void f(String fatal) {
        fatal(fatal);
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
