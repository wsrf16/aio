package com.aio.portable.swiss.suite.log.impl;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class LogHubProperties implements InitializingBean {

    public final static String PREFIX = "spring.log";

    private final static Log log = LogFactory.getLog(LogHubProperties.class);

    private Boolean enabled = true;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }






    private static LogHubProperties instance = new LogHubProperties();

    public synchronized static LogHubProperties singletonInstance() {
        return instance;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public final static void initialSingletonInstance(LogHubProperties properties) {
        instance = properties;
        log.info("LogHubProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(instance));
    }

    public final static void initialSingletonInstance(Binder binder) {
        final BindResult<LogHubProperties> bindResult = binder.bind(LogHubProperties.PREFIX, LogHubProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            LogHubProperties.initialSingletonInstance(bindResult.get());
        } else {
            if (instance == null)
                instance.setEnabled(true);
        }
    }
}
