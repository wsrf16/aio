package com.aio.portable.swiss.assist.log.base;

import com.aio.portable.swiss.assist.log.base.parts.LevelEnum;
import com.aio.portable.swiss.assist.log.base.parts.LogException;
import com.aio.portable.swiss.assist.log.base.parts.LogNote;
import com.aio.portable.swiss.bean.serializer.ISerializerSelector;
import com.aio.portable.swiss.bean.serializer.SerializerSelector;
import com.aio.portable.swiss.systeminfo.HostInfo;
//import com.aio.portable.swiss.assist.log.base.parts.LogThreadPool;
import com.aio.portable.swiss.global.Constant;

import java.net.UnknownHostException;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public abstract class AbstractLogger extends LogBody {
    final static String NEWLINE = Constant.LINE_SEPARATOR;
//    protected final static String INTERVAL_CHAR = " => ";
    protected final static String INTERVAL_CHAR = " ";
    protected final static Supplier<String> EMPTY_PREFIX = () -> StringUtils.EMPTY;
    protected final static String DEFAULT_NAME = AbstractLogger.class.getTypeName();


    protected Supplier<String> prefixSupplier;
    public void setPrefix(Supplier<String> prefix) {
        this.prefixSupplier = prefix;
    }
    private final void clearPrefix() {
        this.prefixSupplier = EMPTY_PREFIX;
    }

    protected ISerializerSelector serializer = new SerializerSelector();
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
     * @param verbose
     */
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
     * @param summary
     * @param verbose
     */
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
     * @param t
     * @param <T>
     */
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
     * @param summary
     * @param t
     * @param <T>
     */
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
     * @param info
     */
    public void trace(String info) {
        LogNote note = new LogNote();
        {
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
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
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.exception = LogException.buildLogException(e);
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
            note.name = getName();
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
            note.name = getName();
            note.level = LevelEnum.WARNING.getName();
            note.summary = summary;
            note.exception = LogException.buildLogException(e);
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
     * @param summary
     * @param t
     * @param <T>
     */
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
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
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
     * @param error
     */
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
     * @param e
     */
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
     * @param summary
     * @param error
     */
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
     * @param e
     */
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
     * @param summary
     * @param error
     * @param e
     */
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
     * @param summary
     * @param t
     * @param <T>
     */
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
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
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
     * @param fatal
     */
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
     * @param e
     */
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
     * @param summary
     * @param e
     */
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
     * @param summary
     * @param fatal
     */
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
     * @param e
     */
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
     * @param summary
     * @param t
     * @param <T>
     */
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
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
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
        String ip = StringUtils.EMPTY;
        try {
            ip = HostInfo.getLocalHostLANAddress().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
