package com.aio.portable.swiss.suite.log.solution.slf4j;

import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.support.LevelEnum;

public class Slf4JPrinter implements Printer {
    final String name;
    private org.slf4j.Logger logger;

    public Slf4JPrinter(String name) {
        this.name = name;
        this.logger = org.slf4j.LoggerFactory.getLogger(name);
//        LoggerContext factory = new LoggerContext();
//        this.logger = factory.getLogger(name);
    }

    @Override
    public void println(String text, LevelEnum level) {
        switch (level) {
            case VERB: {
                Global.unsupportedOperationException(name + ": " + LevelEnum.VERB.getName());
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
            case INFO: {
                logger.info(text);
            }
            break;
            case WARN: {
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
