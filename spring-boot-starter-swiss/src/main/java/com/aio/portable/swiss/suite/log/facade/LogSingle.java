package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.bean.serializer.SerializerConverter;
import com.aio.portable.swiss.suite.bean.serializer.SerializerConverters;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogNote;
import com.aio.portable.swiss.suite.log.support.LogThrowable;
import com.aio.portable.swiss.suite.log.support.StandardLogNote;
import com.aio.portable.swiss.suite.systeminfo.HostInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public abstract class LogSingle implements LogAction {
    private static final String IP_UNKNOWN = "UNKNOWN";
    private static final Log log = LogFactory.getLog(LogSingle.class);

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    protected final static String NEWLINE = Constant.LINE_SEPARATOR;
    protected final static String DELIMITER_CHAR = " ";
    protected final static Supplier<String> EMPTY_PREFIX = () -> Constant.EMPTY;


    protected Supplier<String> prefixSupplier;

    public void setPrefix(Supplier<String> prefix) {
        this.prefixSupplier = prefix;
    }

    private final void clearPrefix() {
        this.prefixSupplier = EMPTY_PREFIX;
    }

    protected SerializerConverter serializer = new SerializerConverters.JacksonConverter();

    protected boolean async = true;

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

//    public final static ExecutorService executor = Executors.newFixedThreadPool(2, new LogSingleThreadFactory());
    public final static ExecutorService executor = new ThreadPoolExecutor(
        LogSingleThreadExecutor.CORE_POOL_SIZE,
        LogSingleThreadExecutor.MAX_POOL_SIZE,
        0L,
        TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<Runnable>(LogSingleThreadExecutor.QUEUE_CAPACITY),
        new LogSingleThreadFactory());

    protected Printer printer;

    public LogSingle(String name) {
        setName(name);
        clearPrefix();
        initialPrinter();
    }

    protected abstract void initialPrinter();

    protected void output(Printer printer, String text, LevelEnum level) {
        try {
            if (async)
                executor.execute(() ->
                        printer.println(prefixSupplier.get() + DELIMITER_CHAR + text, level)
                );
            else
                printer.println(prefixSupplier.get() + DELIMITER_CHAR + text, level);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("logSingle output failed.", e);
        }
    }

    protected void output(Printer printer, LogNote logNote) {
        String text = serializer.serialize(logNote);
        output(printer, text, logNote.getLevel());
    }

    protected void output(Printer printer, Map logNote, LevelEnum level) {
        String text = serializer.serialize(logNote);
        output(printer, text, level);
    }

    public void wrap(LogNote note) {
        note.setOutputType(this.getClass().getSimpleName());
    }

    /**
     * verbose
     * @param message
     */
    @Override
    public void verbose(String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.VERBOSE);
            note.setMessage(message);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * verbose
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
     * @param summary
     * @param message
     */
    @Override
    public void verbose(String summary, String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.VERBOSE);
            note.setSummary(summary);
            note.setMessage(message);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * verbose
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
     * @param t
     * @param <T>
     */
    @Override
    public <T> void verbose(T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.VERBOSE);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * verbose
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void verbose(String summary, T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.VERBOSE);
            note.setSummary(summary);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * verbose
     * @param summary
     * @param message
     * @param t
     * @param <T>
     */
    @Override
    public <T> void verbose(String summary, String message, T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.VERBOSE);
            note.setSummary(summary);
            note.setMessage(message);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * trace
     * @param message
     */
    @Override
    public void trace(String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.TRACE);
            note.setMessage(message);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * trace
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
     * @param summary
     * @param message
     */
    @Override
    public void trace(String summary, String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.TRACE);
            note.setSummary(summary);
            note.setMessage(message);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * trace
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
     * @param t
     * @param <T>
     */
    @Override
    public <T> void trace(T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.TRACE);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * trace
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void trace(String summary, T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.TRACE);
            note.setSummary(summary);
            note.setData(t);
        }
        wrap(note);
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
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.TRACE);
            note.setSummary(summary);
            note.setMessage(message);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * info
     * @param message
     */
    @Override
    public void info(String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.INFORMATION);
            note.setMessage(message);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * info
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
     * @param summary
     * @param message
     */
    @Override
    public void info(String summary, String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.INFORMATION);
            note.setSummary(summary);
            note.setMessage(message);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * info
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
     * @param t
     * @param <T>
     */
    @Override
    public <T> void info(T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.INFORMATION);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * info
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void info(String summary, T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.INFORMATION);
            note.setSummary(summary);
            note.setData(t);
        }
        wrap(note);
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
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.INFORMATION);
            note.setSummary(summary);
            note.setMessage(message);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * debug
     * @param message
     */
    @Override
    public void debug(String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.DEBUG);
            note.setMessage(message);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * debug
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
     * @param summary
     * @param message
     */
    @Override
    public void debug(String summary, String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.DEBUG);
            note.setSummary(summary);
            note.setMessage(message);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * debug
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
     * @param t
     * @param <T>
     */
    @Override
    public <T> void debug(T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.DEBUG);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * debug
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void debug(String summary, T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.DEBUG);
            note.setSummary(summary);
            note.setData(t);
        }
        wrap(note);
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
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.DEBUG);
            note.setSummary(summary);
            note.setMessage(message);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * warn
     * @param message
     */
    @Override
    public void warn(String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.WARNING);
            note.setMessage(message);
        }
        wrap(note);
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
     * warn
     * @param e
     */
    @Override
    public void warn(Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.WARNING);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * warn
     * @param summary
     * @param message
     */
    @Override
    public void warn(String summary, String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.WARNING);
            note.setSummary(summary);
            note.setMessage(message);
        }
        wrap(note);
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
     * warn
     * @param summary
     * @param e
     */
    @Override
    public void warn(String summary, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.WARNING);
            note.setSummary(summary);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * warn
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void warn(String summary, String message, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.WARNING);
            note.setSummary(summary);
            note.setMessage(message);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * warn
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void warn(String summary, T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.WARNING);
            note.setSummary(summary);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * warn
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void warn(String summary, T t, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.WARNING);
            note.setSummary(summary);
            note.setData(t);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * warn
     * @param summary
     * @param message
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void warn(String summary, String message, T t, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.WARNING);
            note.setSummary(summary);
            note.setMessage(message);
            note.setData(t);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * error
     * @param message
     */
    @Override
    public void error(String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.ERROR);
            note.setMessage(message);
        }
        wrap(note);
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
     * @param e
     */
    @Override
    public void error(Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.ERROR);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * error
     * @param summary
     * @param message
     */
    @Override
    public void error(String summary, String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.ERROR);
            note.setSummary(summary);
            note.setMessage(message);
        }
        wrap(note);
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
     * @param summary
     * @param e
     */
    @Override
    public void error(String summary, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.ERROR);
            note.setSummary(summary);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * error
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void error(String summary, String message, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.ERROR);
            note.setSummary(summary);
            note.setMessage(message);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * error
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void error(String summary, T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.ERROR);
            note.setSummary(summary);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * error
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void error(String summary, T t, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.ERROR);
            note.setSummary(summary);
            note.setData(t);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * error
     * @param summary
     * @param message
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void error(String summary, String message, T t, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.ERROR);
            note.setSummary(summary);
            note.setMessage(message);
            note.setData(t);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * fatal
     * @param message
     */
    @Override
    public void fatal(String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.FATAL);
            note.setMessage(message);
        }
        wrap(note);
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
     * @param e
     */
    @Override
    public void fatal(Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.FATAL);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * fatal
     * @param summary
     * @param e
     */
    @Override
    public void fatal(String summary, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.FATAL);
            note.setSummary(summary);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * fatal
     * @param summary
     * @param message
     */
    @Override
    public void fatal(String summary, String message) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.FATAL);
            note.setSummary(summary);
            note.setMessage(message);
        }
        wrap(note);
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
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void fatal(String summary, String message, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.FATAL);
            note.setSummary(summary);
            note.setMessage(message);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * fatal
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void fatal(String summary, T t) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.FATAL);
            note.setSummary(summary);
            note.setData(t);
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * fatal
     * @param summary
     * @param message
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void fatal(String summary, String message, T t, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.FATAL);
            note.setSummary(summary);
            note.setMessage(message);
            note.setData(t);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
        output(printer, note);
    }

    /**
     * fatal
     * @param summary
     * @param t
     * @param e
     * @param <T>
     */
    @Override
    public <T> void fatal(String summary, T t, Throwable e) {
        LogNote note = new StandardLogNote();
        {
            note.setName(name);
            note.setLevel(LevelEnum.FATAL);
            note.setSummary(summary);
            note.setData(t);
            note.setException(LogThrowable.build(e));
        }
        wrap(note);
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
        String ip = HostInfo.getLocalHostLANAddress().getHostAddress();
        return ip;
    }





    static class LogSingleThreadExecutor {
        private final static int QUEUE_CAPACITY = 1024 * 128;
        private final static int CORE_POOL_SIZE = 4;
        private final static int MAX_POOL_SIZE = 1024 * 128;
    }

    static class LogSingleThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        LogSingleThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "log-" + "pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
