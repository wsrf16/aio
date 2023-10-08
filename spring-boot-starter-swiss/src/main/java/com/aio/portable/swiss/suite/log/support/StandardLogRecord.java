package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by York on 2017/11/22.
 */
public class StandardLogRecord implements LogRecord {
    @JsonProperty("level")
    protected LevelEnum level;
    @JsonProperty("name")
    protected String name;
    @JsonProperty("summary")
    protected String summary;
    @JsonProperty("message")
    protected String message;
    @JsonProperty("data")
    protected Object data;
    @JsonProperty("exception")
    protected LogThrowable exception;
    @JsonProperty("outputType")
    protected String outputType;
    @JsonProperty("threadId")
    protected Long threadId = Thread.currentThread().getId();
    @JsonProperty("threadName")
    protected String threadName = Thread.currentThread().getName();


    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public LogThrowable getException() {
        return exception;
    }

    public void setException(LogThrowable exception) {
        this.exception = exception;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

//    public static final synchronized String[] getPropertyNameArray() {
//        if (propertyNameArray == null) {
//            propertyNameArray = BeanSugar.PropertyDescriptors.getAllPropertyNames(StandardLogRecord.class);
//        }
//        return propertyNameArray;
//    }
//
//    private static String[] propertyNameArray;
}

