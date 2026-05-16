package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLogProperties;
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

    private Boolean enabled;

    private LevelEnum level;

    private Boolean async;

    private Float samplerRate;

    private ConsoleLogProperties console;

    private Slf4JLogProperties slf4j;

    private RabbitMQLogProperties rabbit;

    private KafkaLogProperties kafka;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public final Boolean getEnabledOrDefault() {
        return this.getEnabled() == null ? DEFAULT_ENABLED : this.getEnabled();
    }

    public LevelEnum getLevel() {
        return level;
    }

    public void setLevel(LevelEnum level) {
        this.level = level;
    }

    public final LevelEnum getLevelOrDefault() {
        return this.getLevel() == null ? DEFAULT_LEVEL : this.getLevel();
    }

    public Boolean getAsync() {
        return async;
    }

    public void setAsync(Boolean async) {
        this.async = async;
    }

    public Boolean getAsyncOrDefault() {
        return this.getAsync() == null ? DEFAULT_ASYNC : this.getAsync();
    }

    public Float getSamplerRate() {
        return samplerRate;
    }

    public void setSamplerRate(Float samplerRate) {
        this.samplerRate = samplerRate;
    }

    public Float getSamplerRateOrDefault() {
        return this.getSamplerRate() == null ? DEFAULT_SAMPLER_RATE
                : this.getSamplerRate();
    }

    public ConsoleLogProperties getConsole() {
        return console;
    }

    public void setConsole(ConsoleLogProperties console) {
        this.console = console;
    }

    public Slf4JLogProperties getSlf4j() {
        return slf4j;
    }

    public void setSlf4j(Slf4JLogProperties slf4j) {
        this.slf4j = slf4j;
    }

    public RabbitMQLogProperties getRabbit() {
        return rabbit;
    }

    public void setRabbit(RabbitMQLogProperties rabbit) {
        this.rabbit = rabbit;
    }

    public KafkaLogProperties getKafka() {
        return kafka;
    }

    public void setKafka(KafkaLogProperties kafka) {
        this.kafka = kafka;
    }

    private static LogHubProperties instance;

    public static LogHubProperties getSingleton() {
        return instance;
    }

    public synchronized static boolean exist() {
        return instance != null;
    }

//    private static boolean initialized = false;
//
//    public static boolean isInitialized() {
//        return initialized;
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public static void initialSingletonInstance(LogHubProperties properties) {
        instance = properties;
//        log.info("LogHubProperties InitialSingletonInstance: " + JacksonSugar.obj2ShortJson(ClassSugar.PropertyDescriptors.toNameValueMapExceptNull(instance)));
        log.info("LogHubProperties InitialSingletonInstance", ClassSugar.PropertyDescriptors.toNameValueMapExceptNull(instance));
    }

    public static void initialSingletonInstance(Binder binder) {
        BindResult<LogHubProperties> bindResult = binder.bind(LogHubProperties.PREFIX, LogHubProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            LogHubProperties.initialSingletonInstance(bindResult.get());
        }
//        initialized = true;
    }
}
