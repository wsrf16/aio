package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

//@ConfigurationProperties(prefix = "spring.log")
public class LogHubProperties implements InitializingBean {
    public static final String PREFIX = "spring.log";

//    private static final Log log = LogFactory.getLog(LogHubProperties.class);
    private static final LocalLog log = LocalLog.getLog(LogHubProperties.class);

    private static final boolean DEFAULT_ENABLED = true;
    private static final LevelEnum DEFAULT_LEVEL = LevelEnum.INFO;
    private static final boolean DEFAULT_ASYNC = true;
    private static final float DEFAULT_SAMPLER_RATE = 1f;

    private Boolean enabled = true;

    private LevelEnum level;

    private Boolean async;

    private Float samplerRate;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public final Boolean getDefaultEnabledIfAbsent() {
        return this.getEnabled() == null ? DEFAULT_ENABLED : this.getEnabled();
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public final LevelEnum getDefaultLevelIfAbsent() {
        return this.getLevel() == null ? DEFAULT_LEVEL : this.getLevel();
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public Boolean getDefaultAsyncIfAbsent() {
        return this.getAsync() == null ? DEFAULT_ASYNC : this.getAsync();
    }

    public Float getSamplerRate() {
        return samplerRate;
    }

    public void setSamplerRate(Float samplerRate) {
        this.samplerRate = samplerRate;
    }

    public Float getDefaultSamplerRateIfAbsent() {
        return this.getSamplerRate() == null ? DEFAULT_SAMPLER_RATE
                : this.getSamplerRate();
    }
    private static LogHubProperties instance = new LogHubProperties();

    public static LogHubProperties getSingleton() {
        return instance;
    }

    private static boolean initialized = false;

    public static boolean isInitialized() {
        return initialized;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public static final void initialSingletonInstance(LogHubProperties properties) {
        instance = properties;
        log.info("LogHubProperties InitialSingletonInstance: " + JacksonSugar.obj2ShortJson(ClassSugar.PropertyDescriptors.toNameValueMapExceptNull(instance)));
    }

    public static final void initialSingletonInstance(Binder binder) {
        BindResult<LogHubProperties> bindResult = binder.bind(LogHubProperties.PREFIX, LogHubProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            LogHubProperties.initialSingletonInstance(bindResult.get());
        }
        initialized = true;
    }
}
