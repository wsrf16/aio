package com.aio.portable.swiss.structure.log.base;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.structure.bean.serializer.ISerializerSelector;
import com.aio.portable.swiss.structure.bean.serializer.SerializerEnum;
import com.aio.portable.swiss.structure.bean.serializer.SerializerSelector;
import com.aio.portable.swiss.structure.log.base.parts.LevelEnum;
import com.aio.portable.swiss.structure.log.base.parts.LogException;
import com.aio.portable.swiss.structure.log.base.parts.LogNote;
import com.aio.portable.swiss.structure.systeminfo.HostInfo;

import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public abstract class AbstractLogger extends LogBody {
    final static String NEWLINE = Constant.LINE_SEPARATOR;
    //    protected final static String INTERVAL_CHAR = " => ";
    protected final static String INTERVAL_CHAR = " ";
    protected final static Supplier<String> EMPTY_PREFIX = () -> Constant.EMPTY;
    protected final static String DEFAULT_NAME = AbstractLogger.class.getTypeName();


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

    public final static ExecutorService executor = Executors.newCachedThreadPool();

    protected Printer verbosePrinter;
    protected Printer infoPrinter;
    protected Printer tracePrinter;
    protected Printer debugPrinter;
    protected Printer warningPrinter;
    protected Printer errorPrinter;
    protected Printer fatalPrinter;

    protected AbstractLogger(String name) {
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
     * @param verbose
     */
    @Override
    public void verbose(String verbose) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.VERBOSE.getName();
            note.message = verbose;
        }
        output(verbosePrinter, note);
    }

    /**
     * verbose
     *
     * @param verbose
     * @param arguments
     */
//    @Override
//    public void verbose(String verbose, Object[] arguments) {
//        verbose = RegexSugar.replace("\\{\\}", verbose, arguments);
//        verbose(verbose);
//    }

    /**
     * verbose
     *
     * @param summary
     * @param verbose
     */
    @Override
    public void verbose(String summary, String verbose) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.VERBOSE.getName();
            note.summary = summary;
            note.message = verbose;
        }
        output(verbosePrinter, note);
    }

    /**
     * verbose
     *
     * @param summary
     * @param verbose
     * @param arguments
     */
//    @Override
//    public void verbose(String summary, String verbose, Object[] arguments) {
//        verbose = RegexSugar.replace("\\{\\}", verbose, arguments);
//        verbose(summary, verbose);
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
     * trace
     *
     * @param trace
     */
    @Override
    public void trace(String trace) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.TRACE.getName();
            note.message = trace;
        }
        output(tracePrinter, note);
    }

    /**
     * trace
     *
     * @param trace
     * @param arguments
     */
//    @Override
//    public void trace(String trace, Object[] arguments) {
//        trace = RegexSugar.replace("\\{\\}", trace, arguments);
//        trace(trace);
//    }

    /**
     * trace
     *
     * @param summary
     * @param trace
     */
    @Override
    public void trace(String summary, String trace) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.TRACE.getName();
            note.summary = summary;
            note.message = trace;
        }
        output(tracePrinter, note);
    }

    /**
     * trace
     *
     * @param summary
     * @param trace
     * @param arguments
     */
//    @Override
//    public void trace(String summary, String trace, Object[] arguments) {
//        trace = RegexSugar.replace("\\{\\}", trace, arguments);
//        trace(summary, trace);
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
     * info
     *
     * @param info
     */
    @Override
    public void info(String info) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.INFO.getName();
            note.message = info;
        }
        output(infoPrinter, note);
    }

    /**
     * info
     *
     * @param info
     * @param arguments
     */
//    @Override
//    public void info(String info, Object[] arguments) {
//        info = RegexSugar.replace("\\{\\}", info, arguments);
//        info(info);
//    }

    /**
     * info
     *
     * @param summary
     * @param info
     */
    @Override
    public void info(String summary, String info) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.INFO.getName();
            note.summary = summary;
            note.message = info;
        }
        output(infoPrinter, note);
    }

    /**
     * info
     *
     * @param summary
     * @param info
     * @param arguments
     */
//    @Override
//    public void info(String summary, String info, Object[] arguments) {
//        info = RegexSugar.replace("\\{\\}", info, arguments);
//        info(summary, info);
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
     * debug
     *
     * @param debug
     */
    @Override
    public void debug(String debug) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.DEBUG.getName();
            note.message = debug;
        }
        output(debugPrinter, note);
    }

    /**
     * debug
     *
     * @param debug
     * @param arguments
     */
//    @Override
//    public void debug(String debug, Object[] arguments) {
//        debug = RegexSugar.replace("\\{\\}", debug, arguments);
//        debug(debug);
//    }

    /**
     * debug
     *
     * @param summary
     * @param debug
     */
    @Override
    public void debug(String summary, String debug) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.DEBUG.getName();
            note.summary = summary;
            note.message = debug;
        }
        output(debugPrinter, note);
    }

    /**
     * debug
     *
     * @param summary
     * @param debug
     * @param arguments
     */
//    @Override
//    public void debug(String summary, String debug, Object[] arguments) {
//        debug = RegexSugar.replace("\\{\\}", debug, arguments);
//        debug(summary, debug);
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
     * warning
     *
     * @param warning
     */
    @Override
    public void warn(String warning) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.message = warning;
        }
        output(warningPrinter, note);
    }

    /**
     * warn
     * @param warning
     * @param arguments
     */
//    @Override
//    public void warn(String warning, Object[] arguments) {
//        warning = RegexSugar.replace("\\{\\}", warning, arguments);
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
        output(warningPrinter, note);
    }

    /**
     * warning
     *
     * @param summary
     * @param warning
     */
    @Override
    public void warn(String summary, String warning) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.message = warning;
        }
        output(warningPrinter, note);
    }

    /**
     * warn
     * @param summary
     * @param warning
     * @param arguments
     */
//    @Override
//    public void warn(String summary, String warning, Object[] arguments) {
//        warning = RegexSugar.replace("\\{\\}", warning, arguments);
//        warn(summary, warning);
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
        output(warningPrinter, note);
    }

    /**
     * warning
     *
     * @param summary
     * @param warning
     * @param e
     */
    @Override
    public void warn(String summary, String warning, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.message = warning;
            note.exception = LogException.buildLogException(e);
        }
        output(warningPrinter, note);
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
        output(warningPrinter, note);
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
        output(warningPrinter, note);
    }

    /**
     * error
     *
     * @param error
     */
    @Override
    public void error(String error) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.message = error;
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param error
     * @param arguments
     */
//    @Override
//    public void error(String error, Object[] arguments) {
//        error = RegexSugar.replace("\\{\\}", error, arguments);
//        error(error);
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
     * @param error
     */
    @Override
    public void error(String summary, String error) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.message = error;
        }
        output(errorPrinter, note);
    }

    /**
     * error
     * @param summary
     * @param error
     * @param arguments
     */
//    @Override
//    public void error(String summary, String error, Object[] arguments) {
//        error = RegexSugar.replace("\\{\\}", error, arguments);
//        error(summary, error);
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
     * @param error
     * @param e
     */
    @Override
    public void error(String summary, String error, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.ERROR.getName();
            note.summary = summary;
            note.message = error;
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
     * fatal
     *
     * @param fatal
     */
    @Override
    public void fatal(String fatal) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.message = fatal;
        }
        output(fatalPrinter, note);
    }

    /**
     * fatal
     * @param fatal
     * @param arguments
     */
//    @Override
//    public void fatal(String fatal, Object[] arguments) {
//        fatal = RegexSugar.replace("\\{\\}", fatal, arguments);
//        fatal(fatal);
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
     * @param fatal
     */
    @Override
    public void fatal(String summary, String fatal) {
        LogNote note = new LogNote();
        {
            note.name = getName();
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
     * @param arguments
     */
//    @Override
//    public void fatal(String summary, String fatal, Object[] arguments) {
//        fatal = RegexSugar.replace("\\{\\}", fatal, arguments);
//        fatal(summary, fatal);
//    }

    /**
     * fatal
     *
     * @param summary
     * @param fatal
     * @param e
     */
    @Override
    public void fatal(String summary, String fatal, Exception e) {
        LogNote note = new LogNote();
        {
            note.name = getName();
            note.level = LevelEnum.FATAL.getName();
            note.summary = summary;
            note.message = fatal;
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
        if (warningPrinter != null)
            warningPrinter.dispose();
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
