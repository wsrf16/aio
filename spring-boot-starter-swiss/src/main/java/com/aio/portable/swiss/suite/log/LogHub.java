package com.aio.portable.swiss.suite.log;

import com.aio.portable.swiss.sugar.DynamicProxy;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by York on 2017/11/23.
 */
public class LogHub extends LogBundle implements Logger {
    private float samplerRate = 1f;
    private boolean enable = true;
    private LevelEnum baseLevel = LevelEnum.ALL;

    protected LogHub() {
        super();
    }

    private LogHub(List<LogSingle> loggers) {
        super(loggers);
    }




    private final static void throwEmptyList() {
        throw new IllegalArgumentException(MessageFormat.format("{0} is Empty.Please register and try again!", LogHub.class.getTypeName()));
    }

    private void check() {
        boolean b = logList != null && logList.size() > 0;
        if (!b)
            throwEmptyList();
    }

    private boolean passing() {
        boolean b = enable && sampling();
        return b;
    }

    private boolean sampling() {
        float f = new Random().nextFloat();
        return f <= samplerRate;
    }

    public final static LogHub build(List<LogSingle> loggers) {
        LogHub logHub = new LogHub(loggers);
        LogHub proxy = Proxy.toProxy(logHub);
        return proxy;
    }

    public final static LogHub build(LogSingle... logger) {
        List<LogSingle> loggers = logger == null ? Collections.emptyList() : new ArrayList<>(Arrays.asList(logger));
        return build(loggers);
    }

    public final static LogHub buildAsync(List<LogSingle> loggers){
        LogHub logHub = build(loggers);
        logHub.setAsync(true);
        return logHub;
    }










    public float getSamplerRate() {
        return samplerRate;
    }

    public LogHub setSamplerRate(float samplerRate) {
        this.samplerRate = samplerRate;
        return this;
    }

    public boolean isEnable() {
        return enable;
    }

    public LogHub setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public LevelEnum getBaseLevel() {
        return baseLevel;
    }

    public LogHub setBaseLevel(LevelEnum baseLevel) {
        this.baseLevel = baseLevel;
        return this;
    }

    public LogHub setAsync(boolean async) {
        logList.forEach(c -> c.setAsync(async));
        return this;
    }





    static class Proxy {
        public static LogHub toProxy(LogHub logHub) {
            return toCGLIBProxy(logHub);
        }

        protected static LogHub toCGLIBProxy(LogHub logHub) {
            LogHub proxy = DynamicProxy.cglibProxy(LogHub.class, new MethodInterceptor() {
                @Override
                public Object intercept(Object _proxy, Method _method, Object[] _args, MethodProxy _methodProxy) throws Throwable {
                    final LogHub hub = logHub;
                    hub.check();
                    Object invoke = null;
                    switch (_method.getName()) {
                        case "verbose":
                        case "v": {
                            if (hub.passing() && hub.baseLevel.match(LevelEnum.VERBOSE))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "info":
                        case "i": {
                            if (hub.passing() && hub.baseLevel.match(LevelEnum.INFORMATION))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "trace":
                        case "t": {
                            if (hub.passing() && hub.baseLevel.match(LevelEnum.TRACE))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "debug":
                        case "d": {
                            if (hub.passing() && hub.baseLevel.match(LevelEnum.DEBUG))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "warn":
                        case "w": {
                            if (hub.passing() && hub.baseLevel.match(LevelEnum.WARNING))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "error":
                        case "e": {
                            if (hub.passing() && hub.baseLevel.match(LevelEnum.ERROR))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "fatal":
                        case "f": {
                            if (hub.passing() && hub.baseLevel.match(LevelEnum.FATAL))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        default: {
                            invoke = _method.invoke(hub, _args);
                        }
                        break;
                    }
                    return invoke;
                }

            });
            return proxy;
        }

        protected static Logger toJDKProxy(LogHub logHub) {
            Logger proxy = DynamicProxy.jdkProxy(logHub.getClass(),
                    (_proxy, _method, _args) -> {
                        LogHub hub = logHub;
                        hub.check();
                        Object invoke = null;
                        switch (_method.getName().toLowerCase()) {
                            case "verbose":
                            case "v": {
                                if (hub.passing() && hub.baseLevel.match(LevelEnum.VERBOSE))
                                    invoke = _method.invoke(hub, _args);
                            }
                            break;
                            case "info":
                            case "i": {
                                if (hub.passing() && hub.baseLevel.match(LevelEnum.INFORMATION))
                                    invoke = _method.invoke(hub, _args);
                            }
                            break;
                            case "trace":
                            case "t": {
                                if (hub.passing() && hub.baseLevel.match(LevelEnum.TRACE))
                                    invoke = _method.invoke(hub, _args);
                            }
                            break;
                            case "debug":
                            case "d": {
                                if (hub.passing() && hub.baseLevel.match(LevelEnum.DEBUG))
                                    invoke = _method.invoke(hub, _args);
                            }
                            break;
                            case "warn":
                            case "w": {
                                if (hub.passing() && hub.baseLevel.match(LevelEnum.WARNING))
                                    invoke = _method.invoke(hub, _args);
                            }
                            break;
                            case "error":
                            case "e": {
                                if (hub.passing() && hub.baseLevel.match(LevelEnum.ERROR))
                                    invoke = _method.invoke(hub, _args);
                            }
                            break;
                            case "fatal":
                            case "f": {
                                if (hub.passing() && hub.baseLevel.match(LevelEnum.FATAL))
                                    invoke = _method.invoke(hub, _args);
                            }
                            break;
                        }
                        return invoke;
                    });
            return proxy;
        }

    }

}




/*
{
    public void dispose() {
        check();
        logList.forEach(it ->
        {
            it.dispose();
            it = null;
        });
    }

    public void verbose(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.VERBOSE))
            logList.forEach(it -> it.verbose(message));
    }

    public <T> void verbose(T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.VERBOSE))
            logList.forEach(it -> it.verbose(t));
    }

    public void verbose(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.VERBOSE))
            logList.forEach(it -> it.verbose(summary, message));
    }

    public <T> void verbose(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.VERBOSE))
            logList.forEach(it -> it.verbose(summary, t));
    }

    public <T> void verbose(String summary, String message, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.VERBOSE))
            logList.forEach(it -> it.verbose(summary, message, t));
    }

    public void trace(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.TRACE))
            logList.forEach(it -> it.trace(message));
    }

    public <T> void trace(T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.TRACE))
            logList.forEach(it -> it.trace(t));
    }

    public void trace(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.TRACE))
            logList.forEach(it -> it.trace(summary, message));
    }

    public <T> void trace(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.TRACE))
            logList.forEach(it -> it.trace(summary, t));
    }

    public <T> void trace(String summary, String message, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.TRACE))
            logList.forEach(it -> it.trace(summary, message, t));
    }

    public void info(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.INFO))
            logList.forEach(it -> it.info(message));
    }

    public <T> void info(T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.INFO))
            logList.forEach(it -> it.info(t));
    }

    public void info(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.INFO))
            logList.forEach(it -> it.info(summary, message));
    }

    public <T> void info(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.INFO))
            logList.forEach(it -> it.info(summary, t));
    }

    public <T> void info(String summary, String message, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.INFO))
            logList.forEach(it -> it.info(summary, message, t));
    }

    public void debug(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.DEBUG))
            logList.forEach(it -> it.debug(message));
    }

    public <T> void debug(T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.DEBUG))
            logList.forEach(it -> it.debug(t));
    }

    public void debug(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.DEBUG))
            logList.forEach(it -> it.debug(summary, message));
    }

    public <T> void debug(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.DEBUG))
            logList.forEach(it -> it.debug(summary, t));
    }

    public <T> void debug(String summary, String message, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.DEBUG))
            logList.forEach(it -> it.debug(summary, message, t));
    }

    public void error(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(message));
    }

    public void error(Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(e));
    }

    public void error(String summary, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, e));
    }

    public void error(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, message));
    }

    public void error(String summary, String message, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, message, e));
    }

    public <T> void error(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, t));
    }

    public <T> void error(String summary, T t, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, t, e));
    }

    public <T> void error(String summary, String message, T t, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, message, t, e));
    }

    public void warn(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(message));
    }

    public void warn(Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(e));
    }

    public void warn(String summary, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, e));
    }

    public void warn(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, message));
    }

    public void warn(String summary, String message, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, message, e));
    }

    public <T> void warn(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, t));
    }

    public <T> void warn(String summary, T t, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, t, e));
    }

    public <T> void warn(String summary, String message, T t, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, message, t, e));
    }

    public void fatal(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(message));
    }

    public void fatal(Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(e));
    }

    public void fatal(String summary, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, e));
    }

    public void fatal(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, message));
    }

    public void fatal(String summary, String message, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, message, e));
    }

    public <T> void fatal(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, t));
    }

    public <T> void fatal(String summary, T t, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, t, e));
    }

    public <T> void fatal(String summary, String message, T t, Exception e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, message, t, e));
    }
}
*/

