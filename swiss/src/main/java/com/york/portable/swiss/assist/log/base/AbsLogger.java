package com.york.portable.swiss.assist.log.base;

import com.york.portable.swiss.assist.log.base.parts.LevelEnum;
import com.york.portable.swiss.assist.log.base.parts.LogException;
import com.york.portable.swiss.assist.log.base.parts.LogNote;
import com.york.portable.swiss.assist.log.base.parts.LogThreadPool;
import com.york.portable.swiss.bean.serializer.ISerializerSelector;
import com.york.portable.swiss.bean.serializer.SerializerSelector;
import com.york.portable.swiss.global.Constant;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbsLogger extends LoggerRoot {
    static final String NEWLINE = Constant.LINE_SEPARATOR;
//    protected static final String INTERVAL_CHAR = " => ";
    protected static final String INTERVAL_CHAR = " ";
    protected static final Supplier<String> cleanPrefix = () -> StringUtils.EMPTY;
    protected static final String DEFAULT_NAME = AbsLogger.class.getTypeName();


    protected Supplier<String> prefixSupplier;
    public void setPrefix(Supplier<String> prefix) {
        this.prefixSupplier = prefix;
    }
    private void resetPrefix() {
        this.prefixSupplier = cleanPrefix;
    }

    protected ISerializerSelector serializer = new SerializerSelector();
    public ISerializerSelector getSerializer() {
        return serializer;
    }

    protected boolean async = false;
    public boolean isAsync() {
        return async;
    }
    public void setAsync(boolean async) {
        this.async = async;
    }

    protected Printer verbosePrinter;
    protected Printer infoPrinter;
    protected Printer tracePrinter;
    protected Printer debugPrinter;
    protected Printer warningPrinter;
    protected Printer errorPrinter;
    protected Printer fatalPrinter;


    protected AbsLogger(String name) {
        this.name = name;
        resetPrefix();
        initialPrinter();
    }

    protected AbsLogger() {
        this.name = DEFAULT_NAME;
        resetPrefix();
        initialPrinter();
    }

//    private static String spellShortExceptionText_1(Exception e) {
//        String errText = MessageFormat.format("Message : {0} || StackTrace : {1}", e.getMessage(), e.getStackTrace());
//        return errText;
//    }
//
//    protected static String spellLongExceptionText_2(Exception e) {
//        StringBuffer sb = new StringBuffer();
//
//        Exception currentException = e;
//        int layer = 0;
//
//        String text = MessageFormat.format("{0}:{1}--{2}:{3}{4}{5}--{6}:{7}{8}{9}",
//                AbsLogger.class.getName(), NEWLINE,
//                "message", NEWLINE,
//                currentException.toString(), NEWLINE,
//                "stackTrace", NEWLINE,
//                concatStackTraceElement(currentException.getStackTrace()), NEWLINE);
//        return text;
//    }
//
//    protected static String concatStackTraceElement(StackTraceElement[] stackTraceElements) {
//        String newLine = Constant.LINE_SEPARATOR;
//        return Arrays.stream(stackTraceElements).map(StackTraceElement::toString).reduce((sum, ele) -> sum + newLine + ele).get();
//    }

    protected static LogException buildLogException(Exception e) {
        LogException logEx = new LogException();
        logEx.setMessage(e.toString());
        logEx.setStackTrace(Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList()));
        return logEx;
    }

    protected abstract void initialPrinter();


    protected void output(Printer printer, String text) {
        try {
            if (async)
                LogThreadPool.newInstance().executor.execute(() ->
                        printer.println(prefixSupplier.get() + INTERVAL_CHAR + text)
                );
            else
                printer.println(prefixSupplier.get() + INTERVAL_CHAR + text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void output(Printer printer, LogNote note) {
        String text = serializer.serialize(note);
        output(printer, text);
    }

    /**
     * verbose
     * @param verbose
     */
    public void verbose(String verbose) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE.getName();
            note.message = verbose;
        }
        output(verbosePrinter, note);
    }

    /**
     * verbose
     * @param summary
     * @param verbose
     */
    public void verbose(String summary, String verbose) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE.getName();
            note.summary = summary;
            note.message = verbose;
        }
        output(verbosePrinter, note);
    }

    /**
     * verbose
     * @param t
     * @param <T>
     */
    public <T> void verbose(T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE.getName();
            note.data = t;
        }
        output(verbosePrinter, note);
    }

    /**
     * verbose
     * @param summary
     * @param t
     * @param <T>
     */
    public <T> void verbose(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE.getName();
            note.summary = summary;
            note.data = t;
        }
        output(verbosePrinter, note);
    }

    /**
     * trace
     * @param info
     */
    public void trace(String info) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE.getName();
            note.message = info;
        }
        output(tracePrinter, note);
    }

    /**
     * trace
     * @param summary
     * @param info
     */
    public void trace(String summary, String info) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE.getName();
            note.summary = summary;
            note.message = info;
        }
        output(tracePrinter, note);
    }

    /**
     * trace
     * @param t
     * @param <T>
     */
    public <T> void trace(T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE.getName();
            note.data = t;
        }
        output(tracePrinter, note);
    }

    /**
     * trace
     * @param summary
     * @param t
     * @param <T>
     */
    public <T> void trace(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE.getName();
            note.summary = summary;
            note.data = t;
        }
        output(tracePrinter, note);
    }

    /**
     * info
     * @param info
     */
    public void info(String info) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFO.getName();
            note.message = info;
        }
        output(infoPrinter, note);
    }

    /**
     * info
     * @param summary
     * @param info
     */
    public void info(String summary, String info) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFO.getName();
            note.summary = summary;
            note.message = info;
        }
        output(infoPrinter, note);
    }

    /**
     * info
     * @param t
     * @param <T>
     */
    public <T> void info(T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFO.getName();
            note.data = t;
        }
        output(infoPrinter, note);
    }

    /**
     * info
     * @param summary
     * @param t
     * @param <T>
     */
    public <T> void info(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFO.getName();
            note.summary = summary;
            note.data = t;
        }
        output(infoPrinter, note);
    }

    /**
     * debug
     * @param debug
     */
    public void debug(String debug) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG.getName();
            note.message = debug;
        }
        output(debugPrinter, note);
    }

    /**
     * debug
     * @param summary
     * @param debug
     */
    public void debug(String summary, String debug) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG.getName();
            note.summary = summary;
            note.message = debug;
        }
        output(debugPrinter, note);
    }

    /**
     * debug
     * @param t
     * @param <T>
     */
    public <T> void debug(T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG.getName();
            note.data = t;
        }
        output(debugPrinter, note);
    }

    /**
     * debug
     * @param summary
     * @param t
     * @param <T>
     */
    public <T> void debug(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG.getName();
            note.summary = summary;
            note.data = t;
        }
        output(debugPrinter, note);
    }

    /**
     * warning
     * @param warning
     */
    public void warn(String warning) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING.getName();
            note.message = warning;
        }
        output(warningPrinter, note);
    }

    /**
     * warning
     * @param e
     */
    public void warn(Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING.getName();
            note.exception = buildLogException(e);
        }
        output(warningPrinter, note);
    }

    /**
     * warning
     * @param summary
     * @param warning
     */
    public void warn(String summary, String warning) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.message = warning;
        }
        output(warningPrinter, note);
    }

    /**
     * warning
     * @param summary
     * @param e
     */
    public void warn(String summary, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.exception = buildLogException(e);
        }
        output(warningPrinter, note);
    }

    /**
     * warning
     * @param summary
     * @param warning
     * @param e
     */
    public void warn(String summary, String warning, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.message = warning;
            note.exception = buildLogException(e);
        }
        output(warningPrinter, note);
    }

    /**
     * warning
     * @param summary
     * @param t
     * @param <T>
     */
    public <T> void warn(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.data = t;
        }
        output(warningPrinter, note);
    }

    /**
     * warning
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    public <T> void warn(String summary, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.data = t;
            note.exception = buildLogException(e);
        }
        output(warningPrinter, note);
    }

    /**
     * error
     * @param error
     */
    public void error(String error) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR.getName();
            note.message = error;
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param e
     */
    public void error(Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR.getName();
            note.exception = buildLogException(e);
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param summary
     * @param error
     */
    public void error(String summary, String error) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.message = error;
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param summary
     * @param e
     */
    public void error(String summary, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.exception = buildLogException(e);
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param summary
     * @param error
     * @param e
     */
    public void error(String summary, String error, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.message = error;
            note.exception = buildLogException(e);
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param summary
     * @param t
     * @param <T>
     */
    public <T> void error(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.data = t;
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    public <T> void error(String summary, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.data = t;
            note.exception = buildLogException(e);
        }
        output(errorPrinter, note);
    }

    /**
     * fatal
     * @param fatal
     */
    public void fatal(String fatal) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL.getName();
            note.message = fatal;
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param e
     */
    public void fatal(Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL.getName();
            note.exception = buildLogException(e);
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param summary
     * @param e
     */
    public void fatal(String summary, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.exception = buildLogException(e);
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param summary
     * @param fatal
     */
    public void fatal(String summary, String fatal) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.message = fatal;
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param summary
     * @param fatal
     * @param e
     */
    public void fatal(String summary, String fatal, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.message = fatal;
            note.exception = buildLogException(e);
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param summary
     * @param t
     * @param <T>
     */
    public <T> void fatal(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.data = t;
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    public <T> void fatal(String summary, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.data = t;
            note.exception = buildLogException(e);
        }
        output(fatalPrinter, note);
    }

    public void dispose() {
        if (verbosePrinter != null)
            verbosePrinter.dispose();
        if (infoPrinter != null)
            infoPrinter.dispose();
        if (debugPrinter != null)
            debugPrinter.dispose();
        if (warningPrinter != null)
            warningPrinter.dispose();
        if (errorPrinter != null)
            errorPrinter.dispose();
        if (fatalPrinter != null)
            fatalPrinter.dispose();
    }
}
