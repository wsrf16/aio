package com.aio.portable.swiss.suite.log.solution.slf4j;

import com.aio.portable.swiss.global.Global;
import com.aio.portable.swiss.suite.log.facade.LogPrinter;
import com.aio.portable.swiss.suite.log.support.LevelEnum;

public class Slf4JLogPrinter implements LogPrinter {
    final String name;
    private org.slf4j.Logger logger;

    public Slf4JLogPrinter(String name) {
        this.name = name;
        this.logger = org.slf4j.LoggerFactory.getLogger(name);
    }

    @Override
    public void println(Object record, LevelEnum level) {
        String line = getSmartSerializerAdapter(level).serialize(record);
        switch (level) {
            case VERB: {
                Global.unsupportedOperationException(name + ": " + LevelEnum.VERB.getName());
            }
            break;
            case TRACE: {
                logger.trace(line);
            }
            break;
            case DEBUG: {
                logger.debug(line);
            }
            break;
            case INFO: {
                logger.info(line);
            }
            break;
            case WARN: {
                logger.warn(line);
            }
            break;
            case ERROR: {
                logger.error(line);
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
