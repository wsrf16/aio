package com.aio.portable.swiss.suite.log.impl.es.kafka;

import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogNote;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;

import java.util.function.Function;

/**
 * Created by York on 2017/11/23.
 */
public class KafkaLog extends LogSingle {
    KafkaLogProperties properties;

    public KafkaLogProperties getProperties() {
        return properties;
    }

    public KafkaLog setProperties(KafkaLogProperties properties) {
        this.properties = properties;
        return this;
    }



    public KafkaLog(String name) {
        super(name);
    }

    public KafkaLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public KafkaLog() {
        this(StackTraceSugar.Previous.getClassName());
    }



    @Override
    protected void initialPrinter() {
        String name = this.getName();
        properties = KafkaLogProperties.singletonInstance();
        printer = KafkaPrinter.instance(name, properties);
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
