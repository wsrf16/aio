package com.aio.portable.swiss.structure.log.classic.impl.kibana.kafka;

import com.aio.portable.swiss.structure.log.base.AbstractLogger;
import com.aio.portable.swiss.structure.log.base.Printer;
import com.aio.portable.swiss.structure.log.base.parts.LevelEnum;
import com.aio.portable.swiss.structure.log.base.parts.LogNote;
import com.aio.portable.swiss.structure.log.classic.properties.LogKafkaProperties;
import com.aio.portable.swiss.sugar.StackTraceInfos;
import com.aio.portable.swiss.structure.log.classic.impl.kibana.KibanaLogNote;

/**
 * Created by York on 2017/11/23.
 */
public class KafkaLogger extends AbstractLogger {
    public final static KafkaLogger build() {
        String name = StackTraceInfos.Previous.getClassName();
        return build(name);
    }

    public static KafkaLogger build(String name) {
        return new KafkaLogger(name);
    }

    public static KafkaLogger build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private KafkaLogger(String name) {
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
        warningPrinter = KafkaPrinter.instance(name, LevelEnum.WARNING.getName(), properties);
        errorPrinter = KafkaPrinter.instance(name, LevelEnum.ERROR.getName(), properties);
        fatalPrinter = KafkaPrinter.instance(name, LevelEnum.FATAL.getName(), properties);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        String ip = AbstractLogger.getLocalIp();
        String esIndex = properties.getEsIndex();
        KibanaLogNote kibanaLogNote = new KibanaLogNote(logNote, esIndex, ip);
        String text = serializer.serialize(kibanaLogNote);
        super.output(printer, text);
    }


}
