package com.york.portable.swiss.assist.log.classic;

import com.york.portable.swiss.assist.log.base.parts.LevelEnum;
import com.york.portable.swiss.assist.log.base.AbsLogger;

/**
 * Created by York on 2017/11/23.
 */
public class ConsoleLogger extends AbsLogger {
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
        verbosePrinter = ConsolePrinter.instance(name, LevelEnum.VERBOSE.getName());
        tracePrinter = ConsolePrinter.instance(name, LevelEnum.TRACE.getName());
        infoPrinter = ConsolePrinter.instance(name, LevelEnum.INFO.getName());
        debugPrinter = ConsolePrinter.instance(name, LevelEnum.DEBUG.getName());
        warningPrinter = ConsolePrinter.instance(name, LevelEnum.WARNING.getName());
        errorPrinter = ConsolePrinter.instance(name, LevelEnum.ERROR.getName());
        fatalPrinter = ConsolePrinter.instance(name, LevelEnum.FATAL.getName());
    }
}
