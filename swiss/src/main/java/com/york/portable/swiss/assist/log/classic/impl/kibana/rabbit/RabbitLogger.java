package com.york.portable.swiss.assist.log.classic.impl.kibana.rabbit;

import com.york.portable.swiss.assist.log.base.AbsLogger;
import com.york.portable.swiss.assist.log.base.Printer;
import com.york.portable.swiss.assist.log.base.parts.LevelEnum;
import com.york.portable.swiss.assist.log.base.parts.LogNote;
import com.york.portable.swiss.assist.log.classic.impl.kibana.KibanaLogNote;
import com.york.portable.swiss.assist.log.classic.properties.LogRabbitMQProperties;
import com.york.portable.swiss.systeminfo.HostInfo;

import java.net.UnknownHostException;

/**
 * Created by York on 2017/11/23.
 */
public class RabbitLogger extends AbsLogger {
    public static RabbitLogger build(String name) {
        return new RabbitLogger(name);
    }

    public static RabbitLogger build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private RabbitLogger(String name) {
        super(name);
    }

    LogRabbitMQProperties configuration;

    @Override
    protected void initialPrinter() {
        configuration = LogRabbitMQProperties.newInstance();
        verbosePrinter = RabbitPrinter.instance(name, LevelEnum.VERBOSE.getName(), configuration);
        tracePrinter = RabbitPrinter.instance(name, LevelEnum.TRACE.getName(), configuration);
        infoPrinter = RabbitPrinter.instance(name, LevelEnum.INFO.getName(), configuration);
        debugPrinter = RabbitPrinter.instance(name, LevelEnum.DEBUG.getName(), configuration);
        warningPrinter = RabbitPrinter.instance(name, LevelEnum.WARNING.getName(), configuration);
        errorPrinter = RabbitPrinter.instance(name, LevelEnum.ERROR.getName(), configuration);
        fatalPrinter = RabbitPrinter.instance(name, LevelEnum.FATAL.getName(), configuration);
    }

    @Override
    protected void output(Printer printer, LogNote logNote) {
        String ip = getLocalIp();
        String esIndex = configuration.getEsIndex();
        KibanaLogNote kibanaLogNote = new KibanaLogNote(logNote, esIndex, ip);
        String text = serializer.serialize(kibanaLogNote);
        super.output(printer, text);
    }

    private static String getLocalIp() {
        String ip = null;
        try {
            ip = HostInfo.getLocalHostLANAddress().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

}
