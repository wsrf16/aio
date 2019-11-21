package com.aio.portable.swiss.structure.log.base.action;

import com.aio.portable.swiss.sugar.RegexSugar;

/**
 * Service is degraded or endangered.
 */
public interface LogWarning {
    void warn(String warning);

    default void warn(String warning, Object[] arguments) {
        warning = RegexSugar.replace("\\{\\}", warning, arguments);
        warn(warning);
    }

    void warn(Exception e);

    void warn(String summary, Exception e);

    void warn(String summary, String warning);

    default void warn(String summary, String warning, Object[] arguments) {
        warning = RegexSugar.replace("\\{\\}", warning, arguments);
        warn(summary, warning);
    }

    void warn(String summary, String warning, Exception e);

    <T> void warn(String summary, T t);

    <T> void warn(String summary, T t, Exception e);

    default void w(String warning) {
        warn(warning);
    }

    default void w(String warn, Object[] arguments) {
        warn(warn, arguments);
    }

    default void w(Exception e) {
        warn(e);
    }

    default void w(String summary, Exception e) {
        warn(summary, e);
    }

    default void w(String summary, String warning) {
        warn(summary, warning);
    }

    default void w(String summary, String warn, Object[] arguments) {
        warn(summary, warn, arguments);
    }

    default void w(String summary, String warning, Exception e) {
        warn(summary, warning, e);
    }

    default <T> void w(String summary, T t) {
        warn(summary, t);
    }

    default <T> void w(String summary, T t, Exception e) {
        warn(summary, t, e);
    }
}
