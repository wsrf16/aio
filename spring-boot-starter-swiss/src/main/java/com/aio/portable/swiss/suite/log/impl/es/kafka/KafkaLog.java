package com.aio.portable.swiss.suite.log.impl.es.kafka;

import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.suite.log.Printer;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import com.aio.portable.swiss.suite.log.parts.LogNote;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;

/**
 * Created by York on 2017/11/23.
 */
public class KafkaLog extends LogSingle {
    public final static KafkaLog build() {
        String name = StackTraceSugar.Previous.getClassName();
        return build(name);
    }

    public final static KafkaLog build(String name) {
        return new KafkaLog(name);
    }

    public final static KafkaLog build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private KafkaLog(String name) {
        super(name);
    }

    KafkaLogProperties properties;

    @Override
    protected void initialPrinter() {
        String name = getName();
        properties = KafkaLogProperties.singletonInstance();
        printer = KafkaPrinter.instance(name, properties);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        LevelEnum level = logNote.getLevel();
        ESLogNote esLogNote = new ESLogNote(logNote, esIndex, ip);
        String text = serializer.serialize(esLogNote);
        super.output(printer, text, level);
    }


}
