package com.aio.portable.swiss.suite.log.impl.slf4j;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class Slf4JLogProperties implements InitializingBean {
    public final static String PREFIX = "spring.log.slf4j";

    private final static Log log = LogFactory.getLog(Slf4JLogProperties.class);

    private Boolean enabled = true;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }






    private static Slf4JLogProperties instance = new Slf4JLogProperties();

    public synchronized static Slf4JLogProperties singletonInstance() {
        return instance;
    }

    protected Slf4JLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public final static void initialSingletonInstance(Slf4JLogProperties properties) {
        instance = properties;
        log.info("Slf4jLogProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(instance));
    }

    public final static void initialSingletonInstance(Binder binder) {
        final BindResult<Slf4JLogProperties> bindResult = binder.bind(Slf4JLogProperties.PREFIX, Slf4JLogProperties.class);
        if (bindResult.isBound()) {
            Slf4JLogProperties.initialSingletonInstance(bindResult.get());
        } else {
            if (instance != null)
                instance.setEnabled(true);
        }
    }
}
