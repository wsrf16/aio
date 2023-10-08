package com.aio.portable.swiss.suite.log.solution.elk;

import com.aio.portable.swiss.sugar.naming.NamingStrategySugar;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import com.aio.portable.swiss.suite.log.support.LogRecord;
import com.aio.portable.swiss.suite.log.support.StandardLogRecord;

import java.util.Date;

public class ESLogRecord extends StandardLogRecord {
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

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    private String esIndex;

    private String timeStamp;

    private String serverIp;

    public ESLogRecord() {}

    public ESLogRecord(LogRecord logRecord, String esIndex, String serverIp) {
        DeepCloneSugar.Cglib.clone(logRecord, this);

        setEsIndex(esIndex);
        setServerIp(serverIp);
        setTimeStamp(DateTimeSugar.Format.convertDate2Text(DateTimeSugar.Format.FORMAT_ISO8601, new Date()));
    }

    public static final String formatIndex(String name) {
        String result = name;
        if (StringSugar.containUpperCase(result))
            result = NamingStrategySugar.kebab(name);
        if (StringSugar.containUpperCase(result))
            result = result.toLowerCase();
        return result;
    }


}