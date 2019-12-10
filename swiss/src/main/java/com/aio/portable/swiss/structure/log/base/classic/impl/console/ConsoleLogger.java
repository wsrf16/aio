package com.aio.portable.swiss.structure.log.base.classic.impl.console;

import com.aio.portable.swiss.structure.log.base.AbstractLogger;
import com.aio.portable.swiss.structure.log.base.parts.LevelEnum;
import com.aio.portable.swiss.sugar.StackTraceInfoSugar;

/**
 * Created by York on 2017/11/23.
 */
public class ConsoleLogger extends AbstractLogger {
    public final static ConsoleLogger build() {
        String name = StackTraceInfoSugar.Previous.getClassName();
        return build(name);
    }

    public static ConsoleLogger build(String name) {
        return new ConsoleLogger(name);
    }

    public static ConsoleLogger build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private ConsoleLogger(String name) {
        super(name);
    }

    @Override
    protected void initialPrinter() {
        String name = getName();
        verbosePrinter = ConsolePrinter.instance(name, LevelEnum.VERBOSE.getName());
        tracePrinter = ConsolePrinter.instance(name, LevelEnum.TRACE.getName());
        infoPrinter = ConsolePrinter.instance(name, LevelEnum.INFO.getName());
        debugPrinter = ConsolePrinter.instance(name, LevelEnum.DEBUG.getName());
        warnPrinter = ConsolePrinter.instance(name, LevelEnum.WARNING.getName());
        errorPrinter = ConsolePrinter.instance(name, LevelEnum.ERROR.getName());
        fatalPrinter = ConsolePrinter.instance(name, LevelEnum.FATAL.getName());
    }
}
