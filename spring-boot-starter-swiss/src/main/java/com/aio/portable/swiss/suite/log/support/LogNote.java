package com.aio.portable.swiss.suite.log.support;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by York on 2017/11/22.
 */
public interface LogNote {
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
}

