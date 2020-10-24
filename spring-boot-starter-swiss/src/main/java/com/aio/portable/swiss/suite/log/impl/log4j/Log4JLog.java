package com.aio.portable.swiss.suite.log.impl.log4j;

import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import org.apache.log4j.Logger;

public class Log4JLog extends LogSingle {
    private Logger logger = Logger.getLogger(this.getClass());


    public Log4JLog(String name) {
        super(name);
    }

    public Log4JLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public Log4JLog() {
        this(StackTraceSugar.Previous.getClassName());
    }

//    public final static Log4JLog build() {
//        String name = StackTraceSugar.Previous.getClassName();
//        return build(name);
//    }
//
//    public final static Log4JLog build(Class clazz) {
//        String name = clazz.toString();
//        return build(name);
//    }
//
//    public final static Log4JLog build(String name) {
//        Log4JLog log4jLogger = new Log4JLog(name);
//        log4jLogger.logger = Logger.getLogger(name);
//        return log4jLogger;
//    }

    @Override
    protected void initialPrinter() {
        String name = this.getName();

        printer = (text, level) -> {
            switch (level)
            {
                case VERBOSE: {
                    Global.unsupportedOperationException(name + ": " + LevelEnum.VERBOSE.getName());
                }
                break;
                case TRACE: {
                    logger.trace(text);
                }
                break;
                case DEBUG: {
                    logger.debug(text);
                }
                break;
                case INFORMATION: {
                    logger.info(text);
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
                    Global.unsupportedOperationException(name + ": " + LevelEnum.FATAL.getName());
                }
                break;
                default:{
                    Global.unsupportedOperationException(name + ": known");
                }
                break;
            }
        };
    }


}
