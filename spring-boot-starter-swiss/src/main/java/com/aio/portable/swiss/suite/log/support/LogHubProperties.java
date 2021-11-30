package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.log")
public class LogHubProperties implements InitializingBean {
    public final static String PREFIX = "spring.log";

    private final static Log log = LogFactory.getLog(LogHubProperties.class);

    private Boolean enabled = true;

    private LevelEnum level = LevelEnum.INFORMATION;

    private Boolean async;

    private Float samplerRate;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public Float getSamplerRate() {
        return samplerRate;
    }

    public void setSamplerRate(Float samplerRate) {
        this.samplerRate = samplerRate;
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
        log.info("LogHubProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(instance)));
    }

    public final static void initialSingletonInstance(Binder binder) {
        BindResult<LogHubProperties> bindResult = binder.bind(LogHubProperties.PREFIX, LogHubProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            LogHubProperties.initialSingletonInstance(bindResult.get());
        } else {
            if (instance == null)
                instance.setEnabled(true);
        }
    }
}
