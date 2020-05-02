package com.aio.portable.swiss.suite.log.impl.log4j;

import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import org.apache.log4j.Logger;

public class Log4JLog extends LogSingle {
    private Logger logger = Logger.getLogger(this.getClass());


    private Log4JLog(String name) {
        super(name);
    }

    public final static Log4JLog build() {
        String name = StackTraceSugar.Previous.getClassName();
        return build(name);
    }

    public final static Log4JLog build(Class clazz) {
        String name = clazz.toString();
        return build(name);
    }

    public final static Log4JLog build(String name) {
        Log4JLog log4jLogger = new Log4JLog(name);
        log4jLogger.logger = Logger.getLogger(name);
        return log4jLogger;
    }

    @Override
    protected void initialPrinter() {
        verbosePrinter = text -> {
            Global.unsupportedOperationException(this.getClass().getName() + ": verbose");
        };
        infoPrinter = text -> logger.info(text);
        debugPrinter = text -> logger.debug(text);
        warnPrinter = text -> logger.warn(text);
        errorPrinter = text -> logger.error(text);
        tracePrinter = text -> logger.trace(text);
        fatalPrinter = text -> {
            Global.unsupportedOperationException(this.getClass().getName() + ": fatal");
        };
    }


}
