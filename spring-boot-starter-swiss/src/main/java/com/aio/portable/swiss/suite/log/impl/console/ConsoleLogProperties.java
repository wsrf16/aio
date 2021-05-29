package com.aio.portable.swiss.suite.log.impl.console;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class ConsoleLogProperties implements InitializingBean {
    public final static String PREFIX = "spring.log.console";

    private final static Log logger = LogFactory.getLog(ConsoleLogProperties.class);

    private Boolean enabled = true;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }






    private static ConsoleLogProperties instance = new ConsoleLogProperties();

    public synchronized static ConsoleLogProperties singletonInstance() {
        return instance;
    }

    protected ConsoleLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        importSingletonInstance(this);
    }

    public final static void importSingletonInstance(ConsoleLogProperties properties) {
        instance = properties;
        logger.info("ConsoleLogProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(instance));
    }

    public final static void importSingletonInstance(Binder binder) {
        final BindResult<ConsoleLogProperties> bindResult = binder.bind(ConsoleLogProperties.PREFIX, ConsoleLogProperties.class);
        if (bindResult.isBound()) {
            ConsoleLogProperties.importSingletonInstance(bindResult.get());
        } else {
            if (instance != null)
                instance.setEnabled(false);
        }
    }
}
