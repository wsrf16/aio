package com.aio.portable.swiss.suite.log.impl.console;

import com.aio.portable.swiss.suite.bean.CloneableSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.support.LogNote;

/**
 * Created by York on 2017/11/23.
 */
public class ConsoleLog extends LogSingle {
//    public final static ConsoleLog build() {
//        String name = StackTraceSugar.Previous.getClassName();
//        return build(name);
//    }
//
//    public static ConsoleLog build(String name) {
//        return new ConsoleLog(name);
//    }
//
//    public static ConsoleLog build(Class clazz) {
//        String name = clazz.getTypeName();
//        return build(name);
//    }

    public ConsoleLog(String name) {
        super(name);
    }

    public ConsoleLog(Class<?> clazz) {
        this(clazz.toString());
    }

    public ConsoleLog() {
        this(StackTraceSugar.Previous.getClassName());
    }

    @Override
    protected void initialPrinter() {
        String name = this.getName();
        printer = ConsolePrinter.instance(name, "");
    }
}
