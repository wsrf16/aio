package com.aio.portable.swiss.suite.log.solution.elk;

import com.aio.portable.swiss.sugar.naming.NamingStrategySugar;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.bean.DeepCloneSugar;
import com.aio.portable.swiss.suite.log.support.LogRecordItem;
import com.aio.portable.swiss.suite.log.support.StandardLogRecordItem;

import java.util.Date;

public class ESLogRecordItem extends StandardLogRecordItem {
    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getOutputType() {
        return outputType;
    }

    public void setOutputType(String outputType) {
        this.outputType = outputType;
    }

    private String esIndex;

    private String timestamp;

    private String host;

    public ESLogRecordItem() {}

    public ESLogRecordItem(LogRecordItem logRecordItem, String esIndex, String host) {
        DeepCloneSugar.Cglib.clone(logRecordItem, this);

        setEsIndex(esIndex);
        setHost(host);
        setTimestamp(DateTimeSugar.Format.convertDate2Text(DateTimeSugar.Format.FORMAT_ISO8601, new Date()));
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