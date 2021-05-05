package com.aio.portable.swiss.suite.log.impl.es.kafka;

import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogNote;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;

/**
 * Created by York on 2017/11/23.
 */
public class KafkaLog extends LogSingle {
//    public final static KafkaLog build() {
//        String name = StackTraceSugar.Previous.getClassName();
//        return build(name);
//    }
//
//    public final static KafkaLog build(String name) {
//        return new KafkaLog(name);
//    }
//
//    public final static KafkaLog build(Class clazz) {
//        String name = clazz.getTypeName();
//        return build(name);
//    }

    public KafkaLog(String name) {
        super(name);
    }

    public KafkaLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public KafkaLog() {
        this(StackTraceSugar.Previous.getClassName());
    }

    KafkaLogProperties properties;

    @Override
    protected void initialPrinter() {
        String name = this.getName();
        properties = KafkaLogProperties.singletonInstance();
        printer = KafkaPrinter.instance(name, properties);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        logNote.setOutputType(this.getClass().getSimpleName());
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        LevelEnum level = logNote.getLevel();
        ESLogNote esLogNote = new ESLogNote(logNote, esIndex, ip);
        String text = serializer.serialize(esLogNote);
        super.output(printer, text, level);
    }


}
