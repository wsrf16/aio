package com.aio.portable.swiss.structure.log.base.classic.impl.slf4j;

import com.aio.portable.swiss.structure.log.base.AbstractLogger;
import com.aio.portable.swiss.sugar.StackTraceInfoSugar;

public class Slf4jLogger extends AbstractLogger {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
//    private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());


    private Slf4jLogger(String name) {
        super(name);
    }

    public final static Slf4jLogger build() {
        String name = StackTraceInfoSugar.Previous.getClassName();
        return build(name);
    }

    public final static Slf4jLogger build(Class clazz) {
        String name = clazz.toString();
        return build(name);
    }

    public final static Slf4jLogger build(String name) {
        Slf4jLogger slf4jLogger = new Slf4jLogger(name);
        slf4jLogger.logger = org.slf4j.LoggerFactory.getLogger(name);
        return slf4jLogger;
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
