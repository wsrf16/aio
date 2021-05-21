package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.impl.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4jLogProperties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;


public class LogHubProperties implements InitializingBean {
    public final static String PREFIX = "spring.log";

    private final static Log logger = LogFactory.getLog(LogHubProperties.class);

    private KafkaLogProperties kafka;

    private RabbitMQLogProperties rabbitmq;

    private ConsoleLogProperties console;

    private Slf4jLogProperties slf4j;

    private Boolean enabled = true;

    public KafkaLogProperties getKafka() {
        return kafka;
    }

    public void setKafka(KafkaLogProperties kafka) {
        this.kafka = kafka;
    }

    public RabbitMQLogProperties getRabbitmq() {
        return rabbitmq;
    }

    public void setRabbitmq(RabbitMQLogProperties rabbitmq) {
        this.rabbitmq = rabbitmq;
    }

    public ConsoleLogProperties getConsole() {
        return console;
    }

    public void setConsole(ConsoleLogProperties console) {
        this.console = console;
    }

    public Slf4jLogProperties getSlf4j() {
        return slf4j;
    }

    public void setSlf4j(Slf4jLogProperties slf4j) {
        this.slf4j = slf4j;
    }

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

    public LogHubProperties() {
        instance = this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        importSingleton(this);
    }

    public final static void importSingleton(LogHubProperties properties) {
        instance = properties;
        logger.info("LogHubProperties importSingleton: " + JacksonSugar.obj2ShortJson(instance));
    }

    public final static void importSingleton(Binder binder) {
        final BindResult<LogHubProperties> bindResult = binder.bind(LogHubProperties.PREFIX, LogHubProperties.class);
        if (bindResult.isBound()) {
            LogHubProperties.importSingleton(bindResult.get());
        } else {
            if (instance != null)
                instance.setEnabled(false);
        }
    }
















}
