package com.aio.portable.swiss.suite.log.impl.es.rabbit;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogNote;

/**
 * Created by York on 2017/11/23.
 */
public class RabbitMQLog extends LogSingle {
//    public final static RabbitMQLog build(String name) {
//        return new RabbitMQLog(name);
//    }
//
//    public final static RabbitMQLog build(Class clazz) {
//        String name = clazz.getTypeName();
//        return build(name);
//    }

    public RabbitMQLog(String name) {
        super(name);
    }

    public RabbitMQLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public RabbitMQLog() {
        this(StackTraceSugar.Previous.getClassName());
    }

    RabbitMQLogProperties properties;

    @Override
    protected void initialPrinter() {
        String name = this.getName();
        properties = RabbitMQLogProperties.singletonInstance();
        printer = RabbitMQPrinter.instance(name, properties);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        ESLogNote esLogNote = new ESLogNote(logNote, esIndex, ip);
        super.output(printer, esLogNote);
    }
}
