package com.aio.portable.swiss.suite.log.impl.slf4j;

import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.sugar.StackTraceSugar;

public class Slf4JLog extends LogSingle {
//    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
//    private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
    private org.slf4j.Logger logger;

    public Slf4JLog(String name) {
        super(name);
        logger = org.slf4j.LoggerFactory.getLogger(name);
    }

    public final static Slf4JLog build() {
        String name = StackTraceSugar.Previous.getClassName();
        return build(name);
    }

    public final static Slf4JLog build(Class clazz) {
        String name = clazz.toString();
        return build(name);
    }

    public final static Slf4JLog build(String name) {
        Slf4JLog slf4jLogger = new Slf4JLog(name);
        return slf4jLogger;
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
