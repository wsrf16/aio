package com.aio.portable.swiss.suite.log.impl.slf4j;

import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.support.LevelEnum;

public class Slf4jLog extends LogSingle {
//    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
//    private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
    private org.slf4j.Logger logger;

    public Slf4jLog(String name) {
        super(name);
        this.logger = org.slf4j.LoggerFactory.getLogger(name);
    }

    public Slf4jLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public Slf4jLog() {
        this(StackTraceSugar.Previous.getClassName());
    }

    @Override
    protected void initialPrinter() {
        this.printer = new Slf4jPrinter(this.getName());
    }

    private class Slf4jPrinter implements Printer {
        final String name;

        public Slf4jPrinter(String name) {
            this.name = name;
        }

        @Override
        public void println(String text, LevelEnum level) {
            switch (level) {
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
                default: {
                    Global.unsupportedOperationException(name + ": unknown level");
                }
                break;
            }
        }
    }
}
