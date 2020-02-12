package com.aio.portable.swiss.structure.log.base.classic.impl.es.kafka;

import com.aio.portable.swiss.structure.log.base.LogSingle;
import com.aio.portable.swiss.structure.log.base.Printer;
import com.aio.portable.swiss.structure.log.base.classic.properties.LogKafkaProperties;
import com.aio.portable.swiss.structure.log.base.parts.LevelEnum;
import com.aio.portable.swiss.structure.log.base.parts.LogNote;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.structure.log.base.classic.impl.es.ESLogNote;

/**
 * Created by York on 2017/11/23.
 */
public class KafkaLog extends LogSingle {
    public final static KafkaLog build() {
        String name = StackTraceSugar.Previous.getClassName();
        return build(name);
    }

    public static KafkaLog build(String name) {
        return new KafkaLog(name);
    }

    public static KafkaLog build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private KafkaLog(String name) {
        super(name);
    }

    LogKafkaProperties properties;

    @Override
    protected void initialPrinter() {
        String name = getName();
        properties = LogKafkaProperties.singletonInstance();
        verbosePrinter = KafkaPrinter.instance(name, LevelEnum.VERBOSE.getName(), properties);
        tracePrinter = KafkaPrinter.instance(name, LevelEnum.TRACE.getName(), properties);
        infoPrinter = KafkaPrinter.instance(name, LevelEnum.INFO.getName(), properties);
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
