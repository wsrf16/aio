package com.aio.portable.swiss.suite.log.solution.elk;

import com.aio.portable.swiss.sugar.naming.NamingStrategySugar;
import com.aio.portable.swiss.sugar.type.DateTimeSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.log.support.LogBean;
import com.aio.portable.swiss.suite.log.support.StandardLogBean;

import java.util.Date;

public class ESLogBean extends StandardLogBean {
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

    public ESLogBean() {}

    public ESLogBean(LogBean logBean, String esIndex, String serverIp) {
        BeanSugar.Cloneable.deepCloneByCglib(logBean, this);

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