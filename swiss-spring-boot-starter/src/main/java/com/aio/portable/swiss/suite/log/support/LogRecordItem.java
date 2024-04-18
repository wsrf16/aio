package com.aio.portable.swiss.suite.log.support;

/**
 * Created by York on 2017/11/22.
 */
public interface LogRecordItem {
    LevelEnum getLevel();

    void setLevel(LevelEnum level);

    String getName();

    void setName(String name);

    String getSummary();

    void setSummary(String summary);

    String getMessage();

    void setMessage(String message);

    Object getData();

    void setData(Object data);

    LogThrowable getException();

    void setException(LogThrowable exception);

    String getOutputType();

    void setOutputType(String outputType);

    String getThreadName();

    void setThreadName(String threadName);

    Long getThreadId();

    void setThreadId(Long threadId);
}

