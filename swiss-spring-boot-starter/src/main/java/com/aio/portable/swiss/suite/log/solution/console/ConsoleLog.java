package com.aio.portable.swiss.suite.log.solution.console;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogPrinter;
import com.aio.portable.swiss.suite.log.facade.LogSingle;

/**
 * Created by York on 2017/11/23.
 */
public class ConsoleLog extends LogSingle {
    protected ConsoleLogProperties properties;

    public ConsoleLogProperties getProperties() {
        return properties;
    }

    public void setProperties(ConsoleLogProperties properties) {
        this.properties = properties;
    }

    public ConsoleLog(String name) {
        super(name);
    }

    public ConsoleLog(String name, ConsoleLogProperties properties) {
        super(name);
        this.properties = properties;
    }

    public ConsoleLog(Class<?> clazz, ConsoleLogProperties properties) {
        this(clazz.toString(), properties);
    }

    public ConsoleLog(ConsoleLogProperties properties) {
        this(StackTraceSugar.Previous.getClassName(), properties);
    }


    @Override
    protected LogPrinter buildPrinter() {
//        this.properties = this.properties == null ? ConsoleLogProperties.getSingleton() : this.properties;
        return ConsoleLogPrinter.instance(this.getName(), this.properties);
    }
}
