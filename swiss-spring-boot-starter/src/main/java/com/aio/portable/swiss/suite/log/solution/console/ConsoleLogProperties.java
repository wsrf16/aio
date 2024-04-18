package com.aio.portable.swiss.suite.log.solution.console;

import com.aio.portable.swiss.suite.log.support.LogProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class ConsoleLogProperties implements LogProperties, InitializingBean {
    public static final String PREFIX = "spring.log.console";

//    private static final Log log = LogFactory.getLog(ConsoleLogProperties.class);

    private static final boolean DEFAULT_ENABLED = false;

    private Boolean enabled = true;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public final Boolean getDefaultEnabledIfAbsent() {
        return this.getEnabled() == null ? DEFAULT_ENABLED : this.getEnabled();
    }

    private static ConsoleLogProperties instance = new ConsoleLogProperties();

    public synchronized static ConsoleLogProperties getSingleton() {
        return instance;
    }

    private static boolean initialized = false;

    public static boolean initialized() {
        return initialized;
    }

    protected ConsoleLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public static final void initialSingletonInstance(ConsoleLogProperties properties) {
        instance = properties;
//        log.info("ConsoleLogProperties InitialSingletonInstance: " + JacksonSugar.obj2ShortJson(BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(instance)));
    }

    public static final void initialSingletonInstance(Binder binder) {
        BindResult<ConsoleLogProperties> bindResult = binder.bind(ConsoleLogProperties.PREFIX, ConsoleLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            ConsoleLogProperties.initialSingletonInstance(bindResult.get());
        }
        initialized = true;
    }
}
