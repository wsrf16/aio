package com.aio.portable.swiss.suite.log.solution.console;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.facade.LogPrinter;
import com.aio.portable.swiss.suite.log.facade.LogSingle;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;

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
    protected LogPrinter buildPrinter() {
        this.properties = this.properties == null ? ConsoleLogProperties.getSingleton() : this.properties;
        return ConsoleLogPrinter.instance(this.getName(), this.properties);
    }
}
