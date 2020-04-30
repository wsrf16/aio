package com.aio.portable.swiss.suite.log.classic.impl.es.rabbit;

import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.suite.log.Printer;
import com.aio.portable.swiss.suite.log.classic.properties.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import com.aio.portable.swiss.suite.log.parts.LogNote;
import com.aio.portable.swiss.suite.log.classic.impl.es.ESLogNote;

/**
 * Created by York on 2017/11/23.
 */
public class RabbitLog extends LogSingle {
    public final static RabbitLog build(String name) {
        return new RabbitLog(name);
    }

    public final static RabbitLog build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private RabbitLog(String name) {
        super(name);
    }

    RabbitMQLogProperties configuration;

    @Override
    protected void initialPrinter() {
        String name = getName();
        configuration = RabbitMQLogProperties.singletonInstance();
        verbosePrinter = RabbitPrinter.instance(name, LevelEnum.VERBOSE.getName(), configuration);
        tracePrinter = RabbitPrinter.instance(name, LevelEnum.TRACE.getName(), configuration);
        infoPrinter = RabbitPrinter.instance(name, LevelEnum.INFO.getName(), configuration);
        debugPrinter = RabbitPrinter.instance(name, LevelEnum.DEBUG.getName(), configuration);
        warnPrinter = RabbitPrinter.instance(name, LevelEnum.WARNING.getName(), configuration);
        errorPrinter = RabbitPrinter.instance(name, LevelEnum.ERROR.getName(), configuration);
        fatalPrinter = RabbitPrinter.instance(name, LevelEnum.FATAL.getName(), configuration);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        String ip = LogSingle.getLocalIp();
        String esIndex = configuration.getEsIndex();
        ESLogNote esLogNote = new ESLogNote(logNote, esIndex, ip);
        String text = serializer.serialize(esLogNote);
        super.output(printer, text);
    }
}
