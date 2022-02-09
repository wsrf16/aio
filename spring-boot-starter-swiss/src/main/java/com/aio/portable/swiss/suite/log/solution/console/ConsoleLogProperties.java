package com.aio.portable.swiss.suite.log.solution.console;

import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class ConsoleLogProperties implements InitializingBean {
    public static final String PREFIX = "spring.log.console";

    private static final Log log = LogFactory.getLog(ConsoleLogProperties.class);

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
        initialSingletonInstance(this);
    }

    public static final void initialSingletonInstance(ConsoleLogProperties properties) {
        instance = properties;
        log.info("ConsoleLogProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(instance)));
    }

    public static final void initialSingletonInstance(Binder binder) {
        BindResult<ConsoleLogProperties> bindResult = binder.bind(ConsoleLogProperties.PREFIX, ConsoleLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            ConsoleLogProperties.initialSingletonInstance(bindResult.get());
        } else {
            if (instance != null)
                instance.setEnabled(false);
        }
    }
}
