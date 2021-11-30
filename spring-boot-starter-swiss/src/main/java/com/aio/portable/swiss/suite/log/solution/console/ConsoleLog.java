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

    public ConsoleLog setProperties(ConsoleLogProperties properties) {
        this.properties = properties;
        return this;
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
        properties = ConsoleLogProperties.singletonInstance();
        printer = ConsolePrinter.instance(this.getName(), "", properties);
    }
}
