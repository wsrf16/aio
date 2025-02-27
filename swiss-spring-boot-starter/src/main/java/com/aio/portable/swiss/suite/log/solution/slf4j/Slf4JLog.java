package com.aio.portable.swiss.suite.log.solution.slf4j;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogPrinter;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.solution.slf4j.origin.CustomizeSlf4JFactory;
import org.slf4j.LoggerFactory;

public class Slf4JLog extends LogSingle {
//    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
//    private org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(this.getClass());
    protected Slf4JLogProperties properties;

    public Slf4JLogProperties getProperties() {
        return properties;
    }

    public Slf4JLog(String name) {
        super(name);
    }

    public Slf4JLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public Slf4JLog() {
        this(StackTraceSugar.Previous.getClassName());
    }

    public static final boolean rewriteLoggerFactory() {
        return CustomizeSlf4JFactory.class.isAssignableFrom(LoggerFactory.class);
    }

    @Override
    protected LogPrinter buildPrinter() {
        this.properties = this.properties == null ? Slf4JLogProperties.getSingleton() : this.properties;
        return new Slf4JLogPrinter(this.getName());
    }



//    private class Slf4jPrinter implements Printer {
//        final String name;
//
//        public Slf4jPrinter(String name) {
//            this.name = name;
//        }
//
//        @Override
//        public void println(String text, LevelEnum level) {
//            switch (level) {
//                case VERBOSE: {
//                    Global.unsupportedOperationException(name + ": " + LevelEnum.VERBOSE.getName());
//                }
//                break;
//                case TRACE: {
//                    logger.trace(text);
//                }
//                break;
//                case DEBUG: {
//                    logger.debug(text);
//                }
//                break;
//                case INFORMATION: {
//                    logger.info(text);
//                }
//                break;
//                case WARNING: {
//                    logger.warn(text);
//                }
//                break;
//                case ERROR: {
//                    logger.error(text);
//                }
//                break;
//                case FATAL: {
//                    Global.unsupportedOperationException(name + ": " + LevelEnum.FATAL.getName());
//                }
//                break;
//                default: {
//                    Global.unsupportedOperationException(name + ": unknown level");
//                }
//                break;
//            }
//        }
//    }
}
