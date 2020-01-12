package com.aio.portable.swiss.structure.log.base.classic.impl.log4j;

import com.aio.portable.swiss.structure.log.base.AbstractLogger;
import com.aio.portable.swiss.sugar.StackTraceInfoSugar;
import org.apache.log4j.Logger;

public class Log4jLogger extends AbstractLogger {
    private Logger logger = Logger.getLogger(this.getClass());


    private Log4jLogger(String name) {
        super(name);
    }

    public final static Log4jLogger build() {
        String name = StackTraceInfoSugar.Previous.getClassName();
        return build(name);
    }

    public final static Log4jLogger build(Class clazz) {
        String name = clazz.toString();
        return build(name);
    }

    public final static Log4jLogger build(String name) {
        Log4jLogger log4jLogger = new Log4jLogger(name);
        log4jLogger.logger = Logger.getLogger(name);
        return log4jLogger;
    }

    @Override
    protected void initialPrinter() {
        verbosePrinter = text -> {
            throw new UnsupportedOperationException();
        };
        infoPrinter = text -> logger.info(text);
        debugPrinter = text -> logger.debug(text);
        warnPrinter = text -> logger.warn(text);
        errorPrinter = text -> logger.error(text);
        tracePrinter = text -> logger.trace(text);
    }


}
