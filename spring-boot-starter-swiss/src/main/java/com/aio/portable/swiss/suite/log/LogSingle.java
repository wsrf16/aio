package com.aio.portable.swiss.suite.log;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.bean.serializer.ISerializerSelector;
import com.aio.portable.swiss.suite.bean.serializer.SerializerEnum;
import com.aio.portable.swiss.suite.bean.serializer.SerializerSelector;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import com.aio.portable.swiss.suite.log.parts.LogThrowable;
import com.aio.portable.swiss.suite.log.parts.LogNote;
import com.aio.portable.swiss.suite.systeminfo.HostInfo;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public abstract class LogSingle implements LogAction {
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    final static String NEWLINE = Constant.LINE_SEPARATOR;
    //    protected final static String INTERVAL_CHAR = " => ";
    protected final static String INTERVAL_CHAR = " ";
    protected final static Supplier<String> EMPTY_PREFIX = () -> Constant.EMPTY;
    protected final static String DEFAULT_NAME = LogSingle.class.getTypeName();


    protected Supplier<String> prefixSupplier;

    public void setPrefix(Supplier<String> prefix) {
        this.prefixSupplier = prefix;
    }

    private final void clearPrefix() {
        this.prefixSupplier = EMPTY_PREFIX;
    }

    protected ISerializerSelector serializer = new SerializerSelector(SerializerEnum.SERIALIZE_FORCE_JACKSON);
//    public ISerializerSelector getSerializer() {
//        return serializer;
//    }

    protected boolean async = true;

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public final static ExecutorService executor = Executors.newFixedThreadPool(2);

//    protected Printer verbosePrinter;
//    protected Printer infoPrinter;
//    protected Printer tracePrinter;
//    protected Printer debugPrinter;
//    protected Printer warnPrinter;
//    protected Printer errorPrinter;
//    protected Printer fatalPrinter;
    protected Printer printer;

//    LogSingle() {
////        String name = StackTraceSugar.Previous.getClassName();
//        this(StackTraceSugar.Previous.getClassName());
//    }

    public LogSingle(String name) {
        setName(name);
        clearPrefix();
        initialPrinter();
    }

//    public LogSingle(Class clazz) {
//        this(clazz.toString());
//    }


    protected abstract void initialPrinter();

//    public abstract LogSingle build(String name);

//    public LogSingle build() {
//        return build(name);
//    }

//    public LogSingle build(Class clazz) {
//        String name = clazz.toString();
//        return build(name);
//    }


    protected void output(Printer printer, String text, LevelEnum level) {
        try {
            if (async)
                executor.execute(() ->
                        printer.println(prefixSupplier.get() + INTERVAL_CHAR + text, level)
                );
            else
                printer.println(prefixSupplier.get() + INTERVAL_CHAR + text, level);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void output(Printer printer, LogNote logNote) {
        String text = serializer.serialize(logNote);
        output(printer, text, logNote.getLevel());
    }

    /**
     * verbose
     *
     * @param message
     */
    @Override
    public void verbose(String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * verbose
     *
     * @param message
     * @param arguments
     */
//    @Override
//    public void verbose(String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        verbose(message);
//    }

    /**
     * verbose
     *
     * @param summary
     * @param message
     */
    @Override
    public void verbose(String summary, String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE;
            note.summary = summary;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * verbose
     *
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void verbose(String summary, String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        verbose(summary, message);
//    }

    /**
     * verbose
     *
     * @param t
     * @param <T>
     */
    @Override
    public <T> void verbose(T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * verbose
     *
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void verbose(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE;
            note.summary = summary;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * verbose
     *
     * @param summary
     * @param message
     * @param t
     * @param <T>
     */
    @Override
    public <T> void verbose(String summary, String message, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.VERBOSE;
            note.summary = summary;
            note.message = message;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * trace
     *
     * @param message
     */
    @Override
    public void trace(String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * trace
     *
     * @param message
     * @param arguments
     */
//    @Override
//    public void trace(String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        trace(message);
//    }

    /**
     * trace
     *
     * @param summary
     * @param message
     */
    @Override
    public void trace(String summary, String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE;
            note.summary = summary;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * trace
     *
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void trace(String summary, String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        trace(summary, message);
//    }

    /**
     * trace
     *
     * @param t
     * @param <T>
     */
    @Override
    public <T> void trace(T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * trace
     *
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void trace(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE;
            note.summary = summary;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * trace
     * @param summary
     * @param message
     * @param t
     * @param <T>
     */
    @Override
    public <T> void trace(String summary, String message, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.TRACE;
            note.summary = summary;
            note.message = message;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * info
     *
     * @param message
     */
    @Override
    public void info(String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFORMATION;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * info
     *
     * @param message
     * @param arguments
     */
//    @Override
//    public void info(String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        info(message);
//    }

    /**
     * info
     *
     * @param summary
     * @param message
     */
    @Override
    public void info(String summary, String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFORMATION;
            note.summary = summary;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * info
     *
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void info(String summary, String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        info(summary, message);
//    }

    /**
     * info
     *
     * @param t
     * @param <T>
     */
    @Override
    public <T> void info(T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFORMATION;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * info
     *
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void info(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFORMATION;
            note.summary = summary;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * info
     * @param summary
     * @param message
     * @param t
     * @param <T>
     */
    @Override
    public <T> void info(String summary, String message, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.INFORMATION;
            note.summary = summary;
            note.message = message;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * debug
     *
     * @param message
     */
    @Override
    public void debug(String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * debug
     *
     * @param message
     * @param arguments
     */
//    @Override
//    public void debug(String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        debug(message);
//    }

    /**
     * debug
     *
     * @param summary
     * @param message
     */
    @Override
    public void debug(String summary, String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG;
            note.summary = summary;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * debug
     *
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void debug(String summary, String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        debug(summary, message);
//    }

    /**
     * debug
     *
     * @param t
     * @param <T>
     */
    @Override
    public <T> void debug(T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * debug
     *
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void debug(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG;
            note.summary = summary;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * debug
     * @param summary
     * @param message
     * @param t
     * @param <T>
     */
    @Override
    public <T> void debug(String summary, String message, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.DEBUG;
            note.summary = summary;
            note.message = message;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * warning
     *
     * @param message
     */
    @Override
    public void warn(String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * warn
     * @param message
     * @param arguments
     */
//    @Override
//    public void warn(String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        warn(warning);
//    }

    /**
     * warning
     *
     * @param e
     */
    @Override
    public void warn(Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * warning
     *
     * @param summary
     * @param message
     */
    @Override
    public void warn(String summary, String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING;
            note.summary = summary;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * warn
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void warn(String summary, String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        warn(summary, message);
//    }

    /**
     * warning
     *
     * @param summary
     * @param e
     */
    @Override
    public void warn(String summary, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING;
            note.summary = summary;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * warning
     *
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void warn(String summary, String message, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING;
            note.summary = summary;
            note.message = message;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * warning
     *
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void warn(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING;
            note.summary = summary;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * warning
     *
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void warn(String summary, T t, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING;
            note.summary = summary;
            note.data = t;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * warn
     *
     * @param summary
     * @param message
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void warn(String summary, String message, T t, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.WARNING;
            note.summary = summary;
            note.message = message;
            note.data = t;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * error
     *
     * @param message
     */
    @Override
    public void error(String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * error
     * @param message
     * @param arguments
     */
//    @Override
//    public void error(String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        error(message);
//    }

    /**
     * error
     *
     * @param e
     */
    @Override
    public void error(Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * error
     *
     * @param summary
     * @param message
     */
    @Override
    public void error(String summary, String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR;
            note.summary = summary;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * error
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void error(String summary, String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        error(summary, message);
//    }

    /**
     * error
     *
     * @param summary
     * @param e
     */
    @Override
    public void error(String summary, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR;
            note.summary = summary;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * error
     *
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void error(String summary, String message, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR;
            note.summary = summary;
            note.message = message;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * error
     *
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void error(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR;
            note.summary = summary;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * error
     *
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void error(String summary, T t, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR;
            note.summary = summary;
            note.data = t;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * error
     *
     * @param summary
     * @param message
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void error(String summary, String message, T t, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.ERROR;
            note.summary = summary;
            note.message = message;
            note.data = t;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * fatal
     *
     * @param message
     */
    @Override
    public void fatal(String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * fatal
     * @param message
     * @param arguments
     */
//    @Override
//    public void fatal(String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        fatal(message);
//    }

    /**
     * fatal
     *
     * @param e
     */
    @Override
    public void fatal(Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * fatal
     *
     * @param summary
     * @param e
     */
    @Override
    public void fatal(String summary, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL;
            note.summary = summary;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * fatal
     *
     * @param summary
     * @param message
     */
    @Override
    public void fatal(String summary, String message) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL;
            note.summary = summary;
            note.message = message;
        }
        output(printer, note);
    }

    /**
     * fatal
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void fatal(String summary, String message, Object[] arguments) {
//        message = StringSugar.format(message, arguments);
//        fatal(summary, message);
//    }

    /**
     * fatal
     *
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void fatal(String summary, String message, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL;
            note.summary = summary;
            note.message = message;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * fatal
     *
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void fatal(String summary, T t) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL;
            note.summary = summary;
            note.data = t;
        }
        output(printer, note);
    }

    /**
     * fatal
     *
     * @param summary
     * @param message
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void fatal(String summary, String message, T t, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL;
            note.summary = summary;
            note.message = message;
            note.data = t;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    /**
     * fatal
     *
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void fatal(String summary, T t, Throwable e) {
        LogNote note = new LogNote();
        {
            note.name = name;
            note.level = LevelEnum.FATAL;
            note.summary = summary;
            note.data = t;
            note.exception = LogThrowable.build(e);
        }
        output(printer, note);
    }

    @Override
    public void dispose() {
        if (printer != null) {
            printer.dispose();
            printer = null;
        }
    }

    protected static String getLocalIp() {
        String ip = Constant.EMPTY;
        try {
            ip = HostInfo.getLocalHostLANAddress().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
