package com.york.portable.swiss.assist.log.classic.impl.kibana;

import com.york.portable.swiss.assist.log.base.parts.LogException;
import com.york.portable.swiss.assist.log.base.parts.LogNote;
import com.york.portable.swiss.bean.serializer.json.JacksonUtil;
import com.york.portable.swiss.sugar.DateTimeUtils;

import java.util.Date;

public class KibanaLogNote {
    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    //    public long getAppId() {
    //        return appId;
    //    }
    //
    //    public void setAppId(long appId) {
    //        this.appId = appId;
    //    }
    //
    //    public String getAppEnv() {
    //        return appEnv;
    //    }
    //
    //    public void setAppEnv(String appEnv) {
    //        this.appEnv = appEnv;
    //    }

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

    //    public String getHostName() {
    //        return hostName;
    //    }
    //
    //    public void setHostName(String hostName) {
    //        this.hostName = hostName;
    //    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
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

    private String esIndex;

    //    private long appId;

    //    private String appEnv;

    private String timeStamp;

    private String serverIp;

    //    private String hostName;

    public String level;

    public String name;

    public String summary;

    public String message;

    public String data;

    public String exception;


    public KibanaLogNote(String message, String esIndex, String level, String className, LogException logException, String serverIp, String hostName, long appId, String appEnv) {
        setLevel(level);
        setName(className);
        setMessage(message);
        setException(JacksonUtil.obj2Json(logException));

        setEsIndex(esIndex);
        setTimeStamp(DateTimeUtils.Format.convertDate2String("yyyy-MM-dd'T'HH:mm:ss.SSSZ", new Date()));
        setServerIp(serverIp);
        //        setHostName(hostName);
        //        setAppEnv(appEnv);
        //        setAppId(appId);
    }

    public KibanaLogNote(LogNote logNote, String esIndex, String serverIp) {
        setSummary(logNote.getSummary());
        setLevel(logNote.getLevel());
        setName(logNote.getName());
        setMessage(logNote.getMessage());
        setData(JacksonUtil.obj2Json(logNote.getData()));
        setException(JacksonUtil.obj2Json(logNote.getException()));

        setEsIndex(esIndex);
        setServerIp(serverIp);
        setTimeStamp(DateTimeUtils.Format.convertDate2String("yyyy-MM-dd'T'HH:mm:ss.SSSZ", new Date()));
    }

    //    public KibanaLogNote(LogNote logNote, String esIndex, String serverIp, String hostName, long appId, String appEnv) {
    //        setSummary(logNote.getSummary());
    //        setLevel(logNote.getLevel());
    //        setName(logNote.getName());
    //        setMessage(logNote.getMessage());
    //        setData(JacksonUtil.obj2Json(logNote.getData()));
    //        setException(JacksonUtil.obj2Json(logNote.getException()));
    //
    //        setEsIndex(esIndex);
    //        setTimeStamp(DateTimeUtils.convertDate2String("yyyy-MM-dd'T'HH:mm:ss.SSSZ", new Date()));
    //        setServerIp(serverIp);
    //        setHostName(hostName);
    //        setAppEnv(appEnv);
    //        setAppId(appId);
    //    }

}