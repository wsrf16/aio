package com.aio.portable.swiss.suite.log.solution.console;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogSingle;

/**
 * Created by York on 2017/11/23.
 */
public class ConsoleLog extends LogSingle {
    protected ConsoleLogProperties properties;

    public ConsoleLogProperties getProperties() {
        return properties;
    }

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
        refreshPrinter(null);
    }

    public void refreshPrinter(ConsoleLogProperties properties) {
        this.properties = properties == null ? ConsoleLogProperties.getSingleton() : properties;
        this.printer = ConsolePrinter.instance(this.getName(), "", this.properties);
    }
}
