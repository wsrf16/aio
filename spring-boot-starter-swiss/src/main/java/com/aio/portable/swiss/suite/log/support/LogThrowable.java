package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.sugar.ThrowableSugar;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LogThrowable {
    private static final int LIMIT_DEEP = 30;
    private String message;
    private List<String> stackTrace;

    public LogThrowable(Throwable e) {
        setException(e);
    }

    public static final LogThrowable build(Throwable e) {
        return e == null ? null : new LogThrowable(e);
    }

    public void setException(Throwable e) {
        Throwable root = ThrowableSugar.getRootCause(e);
        this.message = root.getMessage();
//        int deep = 0;
//        while (root.getCause() != null) {
//            root = root.getCause();
//            deep++;
//            if (deep > LIMIT_DEEP)
//                break;
//        }
        this.stackTrace = Arrays.stream(root.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }
}
