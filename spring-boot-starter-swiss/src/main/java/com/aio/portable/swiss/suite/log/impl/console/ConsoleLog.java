package com.aio.portable.swiss.suite.log.impl.console;

import com.aio.portable.swiss.suite.log.LogSingle;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import com.aio.portable.swiss.sugar.StackTraceSugar;

/**
 * Created by York on 2017/11/23.
 */
public class ConsoleLog extends LogSingle {
    public final static ConsoleLog build() {
        String name = StackTraceSugar.Previous.getClassName();
        return build(name);
    }

    public static ConsoleLog build(String name) {
        return new ConsoleLog(name);
    }

    public static ConsoleLog build(Class clazz) {
        String name = clazz.getTypeName();
        return build(name);
    }

    private ConsoleLog(String name) {
        super(name);
    }

    @Override
    protected void initialPrinter() {
        String name = getName();
//        verbosePrinter = ConsolePrinter.instance(name, LevelEnum.VERBOSE.getName());
//        tracePrinter = ConsolePrinter.instance(name, LevelEnum.TRACE.getName());
//        infoPrinter = ConsolePrinter.instance(name, LevelEnum.INFORMATION.getName());
//        debugPrinter = ConsolePrinter.instance(name, LevelEnum.DEBUG.getName());
//        warnPrinter = ConsolePrinter.instance(name, LevelEnum.WARNING.getName());
//        errorPrinter = ConsolePrinter.instance(name, LevelEnum.ERROR.getName());
//        fatalPrinter = ConsolePrinter.instance(name, LevelEnum.FATAL.getName());

        printer = ConsolePrinter.instance(name, "");
    }
}
