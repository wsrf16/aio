package com.aio.portable.swiss.suite.log.impl.slf4j;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class Slf4jLogProperties implements InitializingBean {
    public final static String PREFIX = "spring.log.slf4j";

    private final static Logger logger = LoggerFactory.getLogger(Slf4jLogProperties.class);

    private Boolean enabled = true;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }






    private static Slf4jLogProperties instance = new Slf4jLogProperties();

    public synchronized static Slf4jLogProperties singletonInstance() {
        return instance;
    }

    protected Slf4jLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        importSingletonInstance(this);
    }

    public final static void importSingletonInstance(Slf4jLogProperties properties) {
        instance = properties;
        logger.info("Slf4jLogProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(instance));
    }

    public final static void importSingletonInstance(Binder binder) {
        final BindResult<Slf4jLogProperties> bindResult = binder.bind(Slf4jLogProperties.PREFIX, Slf4jLogProperties.class);
        if (bindResult.isBound()) {
            Slf4jLogProperties.importSingletonInstance(bindResult.get());
        } else {
//            if (instance != null)
//                instance.setEnabled(false);
        }
    }
}
