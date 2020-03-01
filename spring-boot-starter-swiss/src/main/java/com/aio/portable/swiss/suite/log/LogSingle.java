package com.aio.portable.swiss.suite.log;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.bean.serializer.ISerializerSelector;
import com.aio.portable.swiss.suite.bean.serializer.SerializerEnum;
import com.aio.portable.swiss.suite.bean.serializer.SerializerSelector;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import com.aio.portable.swiss.suite.log.parts.LogException;
import com.aio.portable.swiss.suite.log.parts.LogNote;
import com.aio.portable.swiss.suite.systeminfo.HostInfo;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public abstract class LogSingle extends LogSingleBody {
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

    protected ISerializerSelector serializer = new SerializerSelector(SerializerEnum.SERIALIZE_JACKSON_FORCE);
//    public ISerializerSelector getSerializer() {
//        return serializer;
//    }

    protected boolean async = false;

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public final static ExecutorService executor = Executors.newFixedThreadPool(2);

    protected Printer verbosePrinter;
    protected Printer infoPrinter;
    protected Printer tracePrinter;
    protected Printer debugPrinter;
    protected Printer warnPrinter;
    protected Printer errorPrinter;
    protected Printer fatalPrinter;

    protected LogSingle(String name) {
        setName(name);
        clearPrefix();
        initialPrinter();
    }


    protected abstract void initialPrinter();


    protected void output(Printer printer, String text) {
        try {
            if (async)
                executor.execute(() ->
                        printer.println(prefixSupplier.get() + INTERVAL_CHAR + text)
                );
            else
                printer.println(prefixSupplier.get() + INTERVAL_CHAR + text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void output(Printer printer, LogNote logNote) {
        String text = serializer.serialize(logNote);
        output(printer, text);
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
            note.name = getName();
            note.level = LevelEnum.VERBOSE.getName();
            note.message = message;
        }
        output(verbosePrinter, note);
    }

    /**
     * verbose
     *
     * @param message
     * @param arguments
     */
//    @Override
//    public void verbose(String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
            note.name = getName();
            note.level = LevelEnum.VERBOSE.getName();
            note.summary = summary;
            note.message = message;
        }
        output(verbosePrinter, note);
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
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
            note.name = getName();
            note.level = LevelEnum.VERBOSE.getName();
            note.data = t;
        }
        output(verbosePrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.VERBOSE.getName();
            note.summary = summary;
            note.data = t;
        }
        output(verbosePrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.VERBOSE.getName();
            note.summary = summary;
            note.message = message;
            note.data = t;
        }
        output(verbosePrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.TRACE.getName();
            note.message = message;
        }
        output(tracePrinter, note);
    }

    /**
     * trace
     *
     * @param message
     * @param arguments
     */
//    @Override
//    public void trace(String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
            note.name = getName();
            note.level = LevelEnum.TRACE.getName();
            note.summary = summary;
            note.message = message;
        }
        output(tracePrinter, note);
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
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
            note.name = getName();
            note.level = LevelEnum.TRACE.getName();
            note.data = t;
        }
        output(tracePrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.TRACE.getName();
            note.summary = summary;
            note.data = t;
        }
        output(tracePrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.TRACE.getName();
            note.summary = summary;
            note.message = message;
            note.data = t;
        }
        output(tracePrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.INFO.getName();
            note.message = message;
        }
        output(infoPrinter, note);
    }

    /**
     * info
     *
     * @param message
     * @param arguments
     */
//    @Override
//    public void info(String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
            note.name = getName();
            note.level = LevelEnum.INFO.getName();
            note.summary = summary;
            note.message = message;
        }
        output(infoPrinter, note);
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
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
            note.name = getName();
            note.level = LevelEnum.INFO.getName();
            note.data = t;
        }
        output(infoPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.INFO.getName();
            note.summary = summary;
            note.data = t;
        }
        output(infoPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.INFO.getName();
            note.summary = summary;
            note.message = message;
            note.data = t;
        }
        output(infoPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.DEBUG.getName();
            note.message = message;
        }
        output(debugPrinter, note);
    }

    /**
     * debug
     *
     * @param message
     * @param arguments
     */
//    @Override
//    public void debug(String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
            note.name = getName();
            note.level = LevelEnum.DEBUG.getName();
            note.summary = summary;
            note.message = message;
        }
        output(debugPrinter, note);
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
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
            note.name = getName();
            note.level = LevelEnum.DEBUG.getName();
            note.data = t;
        }
        output(debugPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.DEBUG.getName();
            note.summary = summary;
            note.data = t;
        }
        output(debugPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.DEBUG.getName();
            note.summary = summary;
            note.message = message;
            note.data = t;
        }
        output(debugPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.message = message;
        }
        output(warnPrinter, note);
    }

    /**
     * warn
     * @param message
     * @param arguments
     */
//    @Override
//    public void warn(String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
//        warn(warning);
//    }

    /**
     * warning
     *
     * @param e
     */
    @Override
    public void warn(Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.exception = LogException.buildLogException(e);
        }
        output(warnPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.message = message;
        }
        output(warnPrinter, note);
    }

    /**
     * warn
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void warn(String summary, String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
//        warn(summary, message);
//    }

    /**
     * warning
     *
     * @param summary
     * @param e
     */
    @Override
    public void warn(String summary, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.exception = LogException.buildLogException(e);
        }
        output(warnPrinter, note);
    }

    /**
     * warning
     *
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void warn(String summary, String message, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.message = message;
            note.exception = LogException.buildLogException(e);
        }
        output(warnPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.data = t;
        }
        output(warnPrinter, note);
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
    public <T> void warn(String summary, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.data = t;
            note.exception = LogException.buildLogException(e);
        }
        output(warnPrinter, note);
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
    public <T> void warn(String summary, String message, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.message = message;
            note.data = t;
            note.exception = LogException.buildLogException(e);
        }
        output(errorPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.message = message;
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param message
     * @param arguments
     */
//    @Override
//    public void error(String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
//        error(message);
//    }

    /**
     * error
     *
     * @param e
     */
    @Override
    public void error(Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.exception = LogException.buildLogException(e);
        }
        output(errorPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.message = message;
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void error(String summary, String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
//        error(summary, message);
//    }

    /**
     * error
     *
     * @param summary
     * @param e
     */
    @Override
    public void error(String summary, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.exception = LogException.buildLogException(e);
        }
        output(errorPrinter, note);
    }

    /**
     * error
     *
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void error(String summary, String message, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.message = message;
            note.exception = LogException.buildLogException(e);
        }
        output(errorPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.data = t;
        }
        output(errorPrinter, note);
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
    public <T> void error(String summary, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.data = t;
            note.exception = LogException.buildLogException(e);
        }
        output(errorPrinter, note);
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
    public <T> void error(String summary, String message, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.message = message;
            note.data = t;
            note.exception = LogException.buildLogException(e);
        }
        output(errorPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.message = message;
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param message
     * @param arguments
     */
//    @Override
//    public void fatal(String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
//        fatal(message);
//    }

    /**
     * fatal
     *
     * @param e
     */
    @Override
    public void fatal(Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.exception = LogException.buildLogException(e);
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     *
     * @param summary
     * @param e
     */
    @Override
    public void fatal(String summary, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.exception = LogException.buildLogException(e);
        }
        output(fatalPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.message = message;
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param summary
     * @param message
     * @param arguments
     */
//    @Override
//    public void fatal(String summary, String message, Object[] arguments) {
//        message = RegexSugar.replace("\\{\\}", message, arguments);
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
    public void fatal(String summary, String message, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.message = message;
            note.exception = LogException.buildLogException(e);
        }
        output(fatalPrinter, note);
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
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.data = t;
        }
        output(fatalPrinter, note);
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
    public <T> void fatal(String summary, String message, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.message = message;
            note.data = t;
            note.exception = LogException.buildLogException(e);
        }
        output(errorPrinter, note);
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
    public <T> void fatal(String summary, T t, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.data = t;
            note.exception = LogException.buildLogException(e);
        }
        output(fatalPrinter, note);
    }

    @Override
    public void dispose() {
        if (verbosePrinter != null)
            verbosePrinter.dispose();
        if (infoPrinter != null)
            infoPrinter.dispose();
        if (debugPrinter != null)
            debugPrinter.dispose();
        if (warnPrinter != null)
            warnPrinter.dispose();
        if (errorPrinter != null)
            errorPrinter.dispose();
        if (fatalPrinter != null)
            fatalPrinter.dispose();
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
