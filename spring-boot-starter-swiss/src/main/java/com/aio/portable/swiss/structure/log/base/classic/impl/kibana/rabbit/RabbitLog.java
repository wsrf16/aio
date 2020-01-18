package com.aio.portable.swiss.structure.log.base.classic.impl.kibana.rabbit;

import com.aio.portable.swiss.structure.log.base.LogSingle;
import com.aio.portable.swiss.structure.log.base.Printer;
import com.aio.portable.swiss.structure.log.base.classic.properties.LogRabbitMQProperties;
import com.aio.portable.swiss.structure.log.base.parts.LevelEnum;
import com.aio.portable.swiss.structure.log.base.parts.LogNote;
import com.aio.portable.swiss.structure.log.base.classic.impl.kibana.KibanaLogNote;

/**
 * Created by York on 2017/11/23.
 */
public class RabbitLog extends LogSingle {
    public static RabbitLog build(String name) {
        return new RabbitLog(name);
    }

    public static RabbitLog build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private RabbitLog(String name) {
        super(name);
    }

    LogRabbitMQProperties configuration;

    @Override
    protected void initialPrinter() {
        String name = getName();
        configuration = LogRabbitMQProperties.singletonInstance();
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
        KibanaLogNote kibanaLogNote = new KibanaLogNote(logNote, esIndex, ip);
        String text = serializer.serialize(kibanaLogNote);
        super.output(printer, text);
    }
}
