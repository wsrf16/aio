//package com.aio.portable.swiss.suite.log.impl.log4j;
//
//import com.aio.portable.swiss.sugar.StackTraceSugar;
//import com.aio.portable.swiss.suite.log.facade.LogSingle;
//import com.aio.portable.swiss.suite.log.support.LevelEnum;
//import org.apache.log4j.Logger;
//
//public class Log4jLog extends LogSingle {
//    private Logger logger = Logger.getLogger(this.getClass());
//
//    public Log4jLog(String name) {
//        super(name);
//    }
//
//    public Log4jLog(Class<?> clazz) {
//        this(clazz.toString());
//    }
//
//    public Log4jLog() {
//        this(StackTraceSugar.Previous.getClassName());
//    }
//
////    public static final Log4JLog build() {
////        String name = StackTraceSugar.Previous.getClassName();
////        return build(name);
////    }
////
////    public static final Log4JLog build(Class clazz) {
////        String name = clazz.toString();
////        return build(name);
////    }
////
////    public static final Log4JLog build(String name) {
////        Log4JLog log4jLogger = new Log4JLog(name);
////        log4jLogger.logger = Logger.getLogger(name);
////        return log4jLogger;
////    }
//
//    @Override
//    protected void initialPrinter() {
//        String name = this.getName();
//
//        printer = (text, level) -> {
//            switch (level)
//            {
//                case VERBOSE: {
////                    Global.unsupportedOperationException(name + ": " + LevelEnum.VERBOSE.getName());
//                    String msg = name + ": " + LevelEnum.VERBOSE.getName();
//                    logger.warn(msg);
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
////                    Global.unsupportedOperationException(name + ": " + LevelEnum.FATAL.getName());
//                    String msg = name + ": " + LevelEnum.FATAL.getName();
//                    logger.warn(msg);
//                }
//                break;
//                default:{
////                    Global.unsupportedOperationException(name + ": known");
//                    String msg = name + ": known";
//                    logger.warn(msg);
//                }
//                break;
//            }
//        };
//    }
//
//
//}
