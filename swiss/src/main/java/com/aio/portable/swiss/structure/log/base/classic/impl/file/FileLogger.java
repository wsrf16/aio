package com.aio.portable.swiss.structure.log.base.classic.impl.file;

import com.aio.portable.swiss.structure.log.base.AbstractLogger;
import com.aio.portable.swiss.structure.log.base.parts.LevelEnum;
import com.aio.portable.swiss.sugar.StackTraceInfoSugar;

/**
 * Created by York on 2017/11/27.
 */
public class FileLogger extends AbstractLogger {
    public final static FileLogger build() {
        String name = StackTraceInfoSugar.Previous.getClassName();
        return build(name);
    }

    public final static FileLogger build(String name) {
        return new FileLogger(name);
    }

    public final static FileLogger build(Class clazz) {
        String name = clazz.toString();
        return build(name);
    }

    private FileLogger(String name) {
        super(name);
    }


    @Override
    protected void initialPrinter() {
        String name = getName();
        verbosePrinter = FilePrinter.instance(name, LevelEnum.VERBOSE.getName());
        tracePrinter = FilePrinter.instance(name, LevelEnum.TRACE.getName());
        infoPrinter = FilePrinter.instance(name, LevelEnum.INFO.getName());
        debugPrinter = FilePrinter.instance(name, LevelEnum.DEBUG.getName());
        warnPrinter = FilePrinter.instance(name, LevelEnum.WARNING.getName());
        errorPrinter = FilePrinter.instance(name, LevelEnum.ERROR.getName());
        fatalPrinter = FilePrinter.instance(name, LevelEnum.FATAL.getName());
    }

}