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
        verbosePrinter = KafkaPrinter.instance(name, LevelEnum.VERBOSE.getName(), properties);
        tracePrinter = KafkaPrinter.instance(name, LevelEnum.TRACE.getName(), properties);
        infoPrinter = KafkaPrinter.instance(name, LevelEnum.INFORMATION.getName(), properties);
        debugPrinter = KafkaPrinter.instance(name, LevelEnum.DEBUG.getName(), properties);
        warnPrinter = KafkaPrinter.instance(name, LevelEnum.WARNING.getName(), properties);
        errorPrinter = KafkaPrinter.instance(name, LevelEnum.ERROR.getName(), properties);
        fatalPrinter = KafkaPrinter.instance(name, LevelEnum.FATAL.getName(), properties);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        String ip = LogSingle.getLocalIp();
        String esIndex = properties.getEsIndex();
        ESLogNote kibanaLogNote = new ESLogNote(logNote, esIndex, ip);
        String text = serializer.serialize(kibanaLogNote);
        super.output(printer, text);
    }


}
