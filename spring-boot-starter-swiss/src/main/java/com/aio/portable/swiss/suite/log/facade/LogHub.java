package com.aio.portable.swiss.suite.log.facade;

import com.aio.portable.swiss.factories.context.LogHubApplicationListener;
import com.aio.portable.swiss.sugar.DynamicProxy;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by York on 2017/11/23.
 */
public class LogHub extends LogBundle implements Logger {
    private static final Log log = LogFactory.getLog(LogHubApplicationListener.class);


    private final static float DEFAULT_SAMPLER_RATE = 1f;

    private boolean enabled = true;

    private LevelEnum enabledLevel = LevelEnum.ALL;

    private float samplerRate = DEFAULT_SAMPLER_RATE;

    protected LogHub() {
        super();
    }

    private LogHub(List<LogSingle> loggers) {
        super(loggers);
    }




    private final static void throwEmptyList() {
        try {
            throw new IllegalArgumentException(MessageFormat.format("{0} is Empty.Please register and try again!", LogHub.class.getTypeName()));
        } catch (IllegalArgumentException e) {
//            System.out.println(MessageFormat.format("{0}{1}{2}", ColorEnum.begin(ColorEnum.FG_YELLOW), e.getMessage(), ColorEnum.end()));
            log.warn(e.getMessage(), e);
        }
    }

    private void validate() {
        boolean b = getLogList() != null && getLogList().size() > 0;
        if (!b)
            throwEmptyList();
    }

    private boolean allow() {
        boolean b = isEnabled() && isEnabledLevel() && isSampled();
        return b;
    }

    private boolean isEnabledLevel() {
        return enabledLevel != LevelEnum.OFF;
    }

    private boolean isSampled() {
        float f = new Random().nextFloat();
        return f <= samplerRate;
    }

    public final static LogHub build(List<LogSingle> logSingleList) {
        LogHub logHub = new LogHub(logSingleList);
        LogHub proxy = Proxy.toProxy(logHub);
        return proxy;
    }

    public final static LogHub build(LogSingle... logSingles) {
        List<LogSingle> logSingleList = logSingles == null ? Collections.emptyList() : new ArrayList<>(Arrays.asList(logSingles));
        LogHub logHub = build(logSingleList);
        return logHub;
    }

    public final static LogHub buildSync(List<LogSingle> logSingleList){
        LogHub logHub = build(logSingleList);
        logHub.setAsync(false);
        return logHub;
    }










    public float getSamplerRate() {
        return samplerRate;
    }

    public LogHub setSamplerRate(float samplerRate) {
        this.samplerRate = samplerRate;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public LogHub setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public LevelEnum getEnabledLevel() {
        return enabledLevel;
    }

    public LogHub setEnabledLevel(LevelEnum enabledLevel) {
        this.enabledLevel = enabledLevel;
        return this;
    }

    public LogHub setAsync(boolean async) {
        getLogList().forEach(c -> c.setAsync(async));
        return this;
    }





    static class Proxy {
        public static LogHub toProxy(LogHub logHub) {
            return toCGLIBProxy(logHub);
        }

        protected static LogHub toCGLIBProxy(LogHub logHub) {
            LogHub proxy = DynamicProxy.cglibProxy(LogHub.class, new MethodInterceptor() {
                @Override
                public Object intercept(Object _proxy, Method _method, Object[] _args, MethodProxy _methodProxy) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                    final LogHub hub = logHub;
                    hub.validate();
                    Object invoke = null;

                    switch (_method.getName()) {
                        case "verbose":
                        case "v": {
                            if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.VERBOSE))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "info":
                        case "i": {
                            if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.INFORMATION))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "trace":
                        case "t": {
                            if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.TRACE))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "debug":
                        case "d": {
                            if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.DEBUG))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "warn":
                        case "w": {
                            if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.WARNING))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "error":
                        case "e": {
                            if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.ERROR))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        case "fatal":
                        case "f": {
                            if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.FATAL))
                                invoke = _method.invoke(hub, _args);
                        }
                        break;
                        default: {
                            invoke = _method.invoke(hub, _args);
                        }
                        break;
                    }
                    return LogHub.class.isAssignableFrom(_method.getReturnType()) ? _proxy : invoke;
//                    return _method.getReturnType().isAssignableFrom() ?  invoke;
                }

            });
            return proxy;
        }

        protected static Logger toJDKProxy(LogHub logHub) {
            Logger proxy = DynamicProxy.jdkProxy(logHub.getClass(), new InvocationHandler() {
                        @Override
                        public Object invoke(Object _proxy, Method _method, Object[] _args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                            LogHub hub = logHub;
                            hub.validate();
                            Object invoke = null;
                            switch (_method.getName().toLowerCase()) {
                                case "verbose":
                                case "v": {
                                    if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.VERBOSE))
                                        invoke = _method.invoke(hub, _args);
                                }
                                break;
                                case "info":
                                case "i": {
                                    if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.INFORMATION))
                                        invoke = _method.invoke(hub, _args);
                                }
                                break;
                                case "trace":
                                case "t": {
                                    if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.TRACE))
                                        invoke = _method.invoke(hub, _args);
                                }
                                break;
                                case "debug":
                                case "d": {
                                    if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.DEBUG))
                                        invoke = _method.invoke(hub, _args);
                                }
                                break;
                                case "warn":
                                case "w": {
                                    if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.WARNING))
                                        invoke = _method.invoke(hub, _args);
                                }
                                break;
                                case "error":
                                case "e": {
                                    if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.ERROR))
                                        invoke = _method.invoke(hub, _args);
                                }
                                break;
                                case "fatal":
                                case "f": {
                                    if (hub.allow() && hub.enabledLevel.beMatched(LevelEnum.FATAL))
                                        invoke = _method.invoke(hub, _args);
                                }
                                break;
                            }
                            return LogHub.class.isAssignableFrom(_method.getReturnType()) ? _proxy : invoke;
                        }
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

    public void error(Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(e));
    }

    public void error(String summary, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, e));
    }

    public void error(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, message));
    }

    public void error(String summary, String message, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, message, e));
    }

    public <T> void error(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, t));
    }

    public <T> void error(String summary, T t, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, t, e));
    }

    public <T> void error(String summary, String message, T t, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.ERROR))
            logList.forEach(it -> it.error(summary, message, t, e));
    }

    public void warn(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(message));
    }

    public void warn(Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(e));
    }

    public void warn(String summary, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, e));
    }

    public void warn(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, message));
    }

    public void warn(String summary, String message, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, message, e));
    }

    public <T> void warn(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, t));
    }

    public <T> void warn(String summary, T t, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, t, e));
    }

    public <T> void warn(String summary, String message, T t, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.WARNING))
            logList.forEach(it -> it.warn(summary, message, t, e));
    }

    public void fatal(String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(message));
    }

    public void fatal(Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(e));
    }

    public void fatal(String summary, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, e));
    }

    public void fatal(String summary, String message) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, message));
    }

    public void fatal(String summary, String message, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, message, e));
    }

    public <T> void fatal(String summary, T t) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, t));
    }

    public <T> void fatal(String summary, T t, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, t, e));
    }

    public <T> void fatal(String summary, String message, T t, Throwable e) {
        check();
        if (passing() && baseLevel.match(LevelEnum.FATAL))
            logList.forEach(it -> it.fatal(summary, message, t, e));
    }
}
*/

