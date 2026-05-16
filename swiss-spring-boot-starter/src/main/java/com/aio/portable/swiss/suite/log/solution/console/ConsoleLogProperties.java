package com.aio.portable.swiss.suite.log.solution.console;

import com.aio.portable.swiss.suite.log.support.LogProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class ConsoleLogProperties implements LogProperties, InitializingBean {
    public static final String PREFIX = "spring.log.console";

//    private static final Log log = LogFactory.getLog(ConsoleLogProperties.class);

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



    private static ConsoleLogProperties instance;

    public synchronized static ConsoleLogProperties getSingleton() {
        return instance;
    }

    public synchronized static boolean exist() {
        return instance != null;
    }

    private static boolean initialized = false;

    public static boolean initialized() {
        return initialized;
    }

    public ConsoleLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public static void initialSingletonInstance(ConsoleLogProperties properties) {
        instance = properties;
//        log.info("ConsoleLogProperties InitialSingletonInstance: " + JacksonSugar.obj2ShortJson(BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(instance)));
    }

    public static void initialSingletonInstance(Binder binder) {
        BindResult<ConsoleLogProperties> bindResult = binder.bind(ConsoleLogProperties.PREFIX, ConsoleLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            ConsoleLogProperties.initialSingletonInstance(bindResult.get());
        }
        initialized = true;
    }
}
