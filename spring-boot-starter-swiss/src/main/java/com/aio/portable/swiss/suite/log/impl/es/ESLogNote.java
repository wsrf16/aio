package com.aio.portable.swiss.suite.log.impl.es;

import com.aio.portable.swiss.sugar.DateTimeSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogNote;

import java.util.Date;

public class ESLogNote {
    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    private String esIndex;

    private String timeStamp;

    private String serverIp;

    public LevelEnum level;

    public String name;

    public String summary;

    public String message;

    public String data;

    public String exception;

    public String outputType;

    public ESLogNote(LogNote logNote, String esIndex, String serverIp) {
        setSummary(logNote.getSummary());
        setLevel(logNote.getLevel());
        setName(logNote.getName());
        setMessage(logNote.getMessage());
        setData(JacksonSugar.obj2Json(logNote.getData()));
        setException(JacksonSugar.obj2Json(logNote.getException()));
        setOutputType(logNote.getOutputType());

        setEsIndex(esIndex);
        setServerIp(serverIp);
        setTimeStamp(DateTimeSugar.Format.convertDate2Text(DateTimeSugar.Format.FORMAT_ISO8601, new Date()));
    }

}