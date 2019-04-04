package com.york.portable.swiss.assist.log.classic;

import com.york.portable.swiss.assist.log.base.AbsLogger;

public class Slf4jLogger extends AbsLogger {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
//    private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());


    private Slf4jLogger(String name) {
        super(name);
    }

    public static Slf4jLogger build(Class clazz) {
        String name = clazz.toString();
        return build(name);
    }

    public static Slf4jLogger build(String name) {
        return new Slf4jLogger(name);
    }

    @Override
    protected void initialPrinter() {
        verbosePrinter = text -> {
            throw new UnsupportedOperationException();
        };
        infoPrinter = text -> logger.info(text);
        debugPrinter = text -> logger.debug(text);
        warningPrinter = text -> logger.warn(text);
        errorPrinter = text -> logger.error(text);
        tracePrinter = text -> logger.trace(text);
    }


}
