package com.aio.portable.swiss.suite.log.impl.es.rabbit;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogNote;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;

/**
 * Created by York on 2017/11/23.
 */
public class RabbitMQLog extends LogSingle {
    private final static Log log = LogFactory.getLog(RabbitMQLog.class);

    private RabbitMQLogProperties properties;

    public RabbitMQLogProperties getProperties() {
        return properties;
    }

    public RabbitMQLog setProperties(RabbitMQLogProperties properties) {
        this.properties = properties;
        return this;
    }



    public RabbitMQLog(String name) {
        super(name);
    }

    public RabbitMQLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public RabbitMQLog() {
        this(StackTraceSugar.Previous.getClassName());
    }

    @Override
    protected void initialPrinter() {
        properties = RabbitMQLogProperties.singletonInstance();
        printer = RabbitMQPrinter.instance(this.getName(), properties);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        final ESLogNote converted = convert(logNote);
        final LevelEnum level = converted.getLevel();
        final String esIndex = converted.getEsIndex();
        if (esIndex == null) {
            throw new NullPointerException("esIndex");
        } else if (esIndex.contains(":")) {
            final String key = esIndex.split(":")[0];
            final String val = esIndex.split(":")[1];
            final Map<String, Object> map = BeanSugar.PropertyDescriptors.toNameValueMap(converted);
            map.remove("esIndex");
            map.put(key, val);
            super.output(printer, map, level);
        } else {
            super.output(printer, converted);
        }
    }

    public ESLogNote convert(LogNote logNote) {
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        return new ESLogNote(logNote, esIndex, ip);
    }
}
