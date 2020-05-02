package com.aio.portable.swiss.suite.log.impl.es.rabbit;

import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.suite.log.Printer;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import com.aio.portable.swiss.suite.log.parts.LogNote;
import com.aio.portable.swiss.suite.log.impl.es.ESLogNote;

/**
 * Created by York on 2017/11/23.
 */
public class RabbitMQLog extends LogSingle {
    public final static RabbitMQLog build(String name) {
        return new RabbitMQLog(name);
    }

    public final static RabbitMQLog build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private RabbitMQLog(String name) {
        super(name);
    }

    RabbitMQLogProperties configuration;

    @Override
    protected void initialPrinter() {
        String name = getName();
        configuration = RabbitMQLogProperties.singletonInstance();
        verbosePrinter = RabbitMQPrinter.instance(name, LevelEnum.VERBOSE.getName(), configuration);
        tracePrinter = RabbitMQPrinter.instance(name, LevelEnum.TRACE.getName(), configuration);
        infoPrinter = RabbitMQPrinter.instance(name, LevelEnum.INFORMATION.getName(), configuration);
        debugPrinter = RabbitMQPrinter.instance(name, LevelEnum.DEBUG.getName(), configuration);
        warnPrinter = RabbitMQPrinter.instance(name, LevelEnum.WARNING.getName(), configuration);
        errorPrinter = RabbitMQPrinter.instance(name, LevelEnum.ERROR.getName(), configuration);
        fatalPrinter = RabbitMQPrinter.instance(name, LevelEnum.FATAL.getName(), configuration);
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
