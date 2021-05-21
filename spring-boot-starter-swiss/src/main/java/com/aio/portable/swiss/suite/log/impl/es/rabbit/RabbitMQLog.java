package com.aio.portable.swiss.suite.log.impl.es.rabbit;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogNote;

import java.util.function.Function;

/**
 * Created by York on 2017/11/23.
 */
public class RabbitMQLog extends LogSingle {
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
        String name = this.getName();
        properties = RabbitMQLogProperties.singletonInstance();
        printer = RabbitMQPrinter.instance(name, properties);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        super.output(printer, convert(logNote));
    }

    public ESLogNote convert(LogNote logNote) {
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        return new ESLogNote(logNote, esIndex, ip);
    }
}
