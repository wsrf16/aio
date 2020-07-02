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
        printer = ConsolePrinter.instance(name, "");
    }
}
