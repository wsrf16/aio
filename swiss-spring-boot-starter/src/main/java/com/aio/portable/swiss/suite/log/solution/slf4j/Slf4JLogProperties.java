package com.aio.portable.swiss.suite.log.solution.slf4j;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LogProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class Slf4JLogProperties implements LogProperties, InitializingBean {
    public static final String PREFIX = "spring.log.slf4j";

//    private static final Log log = LogFactory.getLog(Slf4JLogProperties.class);
    private static final LocalLog log = LocalLog.getLog(Slf4JLogProperties.class);

    private static final boolean DEFAULT_ENABLED = true;

    private Boolean enabled;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public final Boolean getEnabledOrDefault() {
        return this.getEnabled() == null ? DEFAULT_ENABLED : this.getEnabled();
    }

    public Boolean getEnabledIsTrue() {
        return enabled != null && enabled;
    }

    public Boolean getEnabledIsFalse() {
        return enabled != null && !enabled;
    }



    private static Slf4JLogProperties instance;

    public synchronized static Slf4JLogProperties getSingleton() {
        return instance;
    }

    public synchronized static boolean exist() {
        return instance != null;
    }

    private static boolean initialized = false;

    public static boolean initialized() {
        return initialized;
    }

    public Slf4JLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public static void initialSingletonInstance(Slf4JLogProperties properties) {
        instance = properties;
        log.debug("Slf4jLogProperties InitialSingletonInstance", null, ClassSugar.PropertyDescriptors.toNameValueMapExceptNull(instance));
    }

    public static void initialSingletonInstance(Binder binder) {
        final BindResult<Slf4JLogProperties> bindResult = binder.bind(Slf4JLogProperties.PREFIX, Slf4JLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            Slf4JLogProperties.initialSingletonInstance(bindResult.get());
        }
        initialized = true;
    }

//    public static void ff(ConfigurableEnvironment environment) {
//        Binder.get(environment)
//    }
}
