package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.log.action.LogAction;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import com.aio.portable.swiss.suite.log.support.LogRecordItem;
import com.aio.portable.swiss.suite.log.support.LogThrowable;
import com.aio.portable.swiss.suite.log.support.StandardLogRecordItem;
import com.aio.portable.swiss.suite.system.HostInfo;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public abstract class LogSingle implements LogAction {
//    private static final Log log = LogFactory.getLog(LogSingle.class);
    private static final LocalLog log = LocalLog.getLog(LogSingle.class);

    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    protected static final String NEWLINE = Constant.LINE_SEPARATOR;
    protected static final String DELIMITER_CHAR = " ";
    protected static final Supplier<String> EMPTY_PREFIX = () -> Constant.EMPTY;


//    protected Supplier<String> prefixSupplier;
//
//    public void setPrefix(Supplier<String> prefix) {
//        this.prefixSupplier = prefix;
//    }
//
//    private final void clearPrefix() {
//        this.prefixSupplier = EMPTY_PREFIX;
//    }

    protected boolean async = true;

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

//    public static final ExecutorService executor = Executors.newFixedThreadPool(2, new LogSingleThreadFactory());
    public static final ExecutorService executor = new ThreadPoolExecutor(
        LogSingleThreadExecutor.CORE_POOL_SIZE,
        LogSingleThreadExecutor.MAX_POOL_SIZE,
        LogSingleThreadExecutor.KEEP_ALIVE_TIME,
        TimeUnit.MILLISECONDS,
        new ArrayBlockingQueue<>(LogSingleThreadExecutor.QUEUE_CAPACITY),
        new LogSingleThreadFactory(),
//        new ThreadPoolExecutor.AbortPolicy()
        new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    protected Printer printer;

    public LogSingle(String name) {
        setName(name);
//        clearPrefix();
        initialPrinter();
    }

    protected abstract void initialPrinter();

    protected void output(LogRecordItem logRecordItem) {
        try {
            output(logRecordItem, logRecordItem.getLevel());
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn(e);
        }
    }

    protected <T> void output(T record, LevelEnum level) {
        try {
            if (async) {
                executor.execute(() ->
                        printer.println(record, level)
                );
            } else {
                printer.println(record, level);
            }
        } catch (Exception e) {
            log.warn("logSingle output failed.", e);
        }
    }

    public void attachTo(LogRecordItem record) {
        record.setName(name);
        record.setOutputType(this.getClass().getSimpleName());
    }

    /**
     * verbose
     * @param message
     */
    @Override
    public void verb(String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.VERB);
            record.setMessage(message);
        }
        output(record);
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
    public void verb(String summary, String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.VERB);
            record.setSummary(summary);
            record.setMessage(message);
        }
        output(record);
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
    public <T> void verb(T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.VERB);
            record.setData(t);
        }
        output(record);
    }

    /**
     * verbose
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void verb(String summary, T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.VERB);
            record.setSummary(summary);
            record.setData(t);
        }
        output(record);
    }

    /**
     * verbose
     * @param summary
     * @param message
     * @param t
     * @param <T>
     */
    @Override
    public <T> void verb(String summary, String message, T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.VERB);
            record.setSummary(summary);
            record.setMessage(message);
            record.setData(t);
        }
        output(record);
    }

    /**
     * trace
     * @param message
     */
    @Override
    public void trace(String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.TRACE);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.TRACE);
            record.setSummary(summary);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.TRACE);
            record.setData(t);
        }
        output(record);
    }

    /**
     * trace
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void trace(String summary, T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.TRACE);
            record.setSummary(summary);
            record.setData(t);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.TRACE);
            record.setSummary(summary);
            record.setMessage(message);
            record.setData(t);
        }
        output(record);
    }

    /**
     * info
     * @param message
     */
    @Override
    public void info(String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.INFO);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.INFO);
            record.setSummary(summary);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.INFO);
            record.setData(t);
        }
        output(record);
    }

    /**
     * info
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void info(String summary, T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.INFO);
            record.setSummary(summary);
            record.setData(t);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.INFO);
            record.setSummary(summary);
            record.setMessage(message);
            record.setData(t);
        }
        output(record);
    }

    /**
     * debug
     * @param message
     */
    @Override
    public void debug(String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.DEBUG);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.DEBUG);
            record.setSummary(summary);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.DEBUG);
            record.setData(t);
        }
        output(record);
    }

    /**
     * debug
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void debug(String summary, T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.DEBUG);
            record.setSummary(summary);
            record.setData(t);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.DEBUG);
            record.setSummary(summary);
            record.setMessage(message);
            record.setData(t);
        }
        output(record);
    }

    /**
     * warn
     * @param message
     */
    @Override
    public void warn(String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.WARN);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.WARN);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * warn
     * @param summary
     * @param message
     */
    @Override
    public void warn(String summary, String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.WARN);
            record.setSummary(summary);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.WARN);
            record.setSummary(summary);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * warn
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void warn(String summary, String message, Throwable e) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.WARN);
            record.setSummary(summary);
            record.setMessage(message);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * warn
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void warn(String summary, T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.WARN);
            record.setSummary(summary);
            record.setData(t);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.WARN);
            record.setSummary(summary);
            record.setData(t);
            record.setException(LogThrowable.build(e));
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.WARN);
            record.setSummary(summary);
            record.setMessage(message);
            record.setData(t);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * error
     * @param message
     */
    @Override
    public void error(String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.ERROR);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.ERROR);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * error
     * @param summary
     * @param message
     */
    @Override
    public void error(String summary, String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.ERROR);
            record.setSummary(summary);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.ERROR);
            record.setSummary(summary);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * error
     * @param summary
     * @param message
     * @param e
     */
    @Override
    public void error(String summary, String message, Throwable e) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.ERROR);
            record.setSummary(summary);
            record.setMessage(message);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * error
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void error(String summary, T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.ERROR);
            record.setSummary(summary);
            record.setData(t);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.ERROR);
            record.setSummary(summary);
            record.setData(t);
            record.setException(LogThrowable.build(e));
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.ERROR);
            record.setSummary(summary);
            record.setMessage(message);
            record.setData(t);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * fatal
     * @param message
     */
    @Override
    public void fatal(String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.FATAL);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.FATAL);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * fatal
     * @param summary
     * @param e
     */
    @Override
    public void fatal(String summary, Throwable e) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.FATAL);
            record.setSummary(summary);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * fatal
     * @param summary
     * @param message
     */
    @Override
    public void fatal(String summary, String message) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.FATAL);
            record.setSummary(summary);
            record.setMessage(message);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.FATAL);
            record.setSummary(summary);
            record.setMessage(message);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    /**
     * fatal
     * @param summary
     * @param t
     * @param <T>
     */
    @Override
    public <T> void fatal(String summary, T t) {
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.FATAL);
            record.setSummary(summary);
            record.setData(t);
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.FATAL);
            record.setSummary(summary);
            record.setMessage(message);
            record.setData(t);
            record.setException(LogThrowable.build(e));
        }
        output(record);
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
        LogRecordItem record = new StandardLogRecordItem();
        attachTo(record);
        {
            record.setLevel(LevelEnum.FATAL);
            record.setSummary(summary);
            record.setData(t);
            record.setException(LogThrowable.build(e));
        }
        output(record);
    }

    @Override
    public void dispose() {
        if (printer != null) {
            printer.dispose();
            printer = null;
        }
    }

    protected static String getLocalIp() {
        String ip;
        try {
            ip = HostInfo.getLocalHostLANAddress().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            ip = HostInfo.IP_UNKNOWN;
        }
        return ip;
    }





    static class LogSingleThreadExecutor {
        private static final int QUEUE_CAPACITY = 1024 * 128;
        private static final int CORE_POOL_SIZE = 10;
        private static final int MAX_POOL_SIZE = 20;
        private static final long KEEP_ALIVE_TIME = 1000 * 10;
    }

    static class LogSingleThreadFactory implements ThreadFactory {
//        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix = "log-thread-pool-";

        LogSingleThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
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
