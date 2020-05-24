package com.aio.portable.swiss.suite.log.impl.slf4j;

import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.Printer;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import com.aio.portable.swiss.suite.log.parts.LogNote;

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
        String name = this.getClass().getName();

        printer = (text, level) -> {
            switch (level)
            {
                case VERBOSE: {
                    Global.unsupportedOperationException(name + ": verbose");
                }
                break;
                case TRACE: {
                    logger.trace(text);
                }
                break;
                case INFORMATION: {
                    logger.info(text);
                }
                break;
                case DEBUG: {
                    logger.debug(text);
                }
                break;
                case WARNING: {
                    logger.warn(text);
                }
                break;
                case ERROR: {
                    logger.error(text);
                }
                break;
                case FATAL: {
                    Global.unsupportedOperationException(name + ": fatal");
                }
                break;
                default:{
                    Global.unsupportedOperationException(name + ": known level");
                }
                break;
            }
        };
    }

}
