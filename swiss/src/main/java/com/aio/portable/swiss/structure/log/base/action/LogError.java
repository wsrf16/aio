package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * Functionality is unavailable, invariants are broken or data is lost.
 */
public interface LogError {
    void error(String error);

    default void error(String error, Object[] arguments) {
        error = RegexSugar.replace("\\{\\}", error, arguments);
        error(error);
    }

    void error(Exception e);

    void error(String summary, Exception e);

    void error(String summary, String error);

    default void error(String summary, String error, Object[] arguments) {
        error = RegexSugar.replace("\\{\\}", error, arguments);
        error(summary, error);
    }

    void error(String summary, String error, Exception e);

    <T> void error(String summary, T t, Exception e);

    <T> void error(String summary, T t);

    default void e(String error) {
        error(error);
    }

    default void e(String error, Object[] arguments) {
        error(error, arguments);
    }

    default void e(Exception e) {
        error(e);
    }

    default void e(String summary, Exception e) {
        error(summary, e);
    }

    default void e(String summary, String error) {
        error(summary, error);
    }

    default void e(String summary, String error, Object[] arguments) {
        error(summary, error, arguments);
    }

    default void e(String summary, String error, Exception e) {
        error(summary, error, e);
    }

    default <T> void e(String summary, T t, Exception e) {
        error(summary, t, e);
    }

    default <T> void e(String summary, T t) {
        error(summary, t);
    }
}
