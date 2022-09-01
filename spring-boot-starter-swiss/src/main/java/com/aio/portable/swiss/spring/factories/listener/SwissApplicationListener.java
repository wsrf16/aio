package com.aio.portable.swiss.spring.factories.listener;

import com.aio.portable.swiss.sugar.resource.ClassLoaderSugar;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.*;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

// GenericApplicationListener
// SmartApplicationListener
// ConfigFileApplicationListener
public class SwissApplicationListener extends AbstractGenericApplicationListener implements EnvironmentPostProcessor, Ordered {
    public static final int DEFAULT_ORDER = Integer.MIN_VALUE + 20;
    private static final Class<?>[] EVENT_TYPES = {
            ApplicationStartingEvent.class,
            ApplicationEnvironmentPreparedEvent.class,
            ApplicationPreparedEvent.class,
            ContextClosedEvent.class,
            ApplicationFailedEvent.class,
            ApplicationReadyEvent.class,
            ApplicationStartedEvent.class,
            ContextRefreshedEvent.class,
            ApplicationContextInitializedEvent.class
    };
    private static final Log log = LogFactory.getLog(SwissApplicationListener.class);
    private int order = DEFAULT_ORDER;


    // step 2: An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the context is known, but before the context is created.
    @Override
    protected void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        this.postProcessEnvironment(event.getEnvironment(), event.getSpringApplication());
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.debug("LogHubApplicationListener.postProcessEnvironment ConfigurableEnvironment: " + environment);
        initializeLogHubProperties(environment);
    }

    private static void initializeLogHubProperties(ConfigurableEnvironment environment) {
        Binder binder = Binder.get(environment);
        try {
            LogHubProperties.initialSingletonInstance(binder);
        } catch (Exception e) {
            log.warn("Initial loghub log singleton instance failed.", e);
        }

        if (LogHubUtils.RabbitMQ.existDependency()) {
            try {
                RabbitMQLogProperties.initialSingletonInstance(binder);
            } catch (Exception e) {
                log.warn("Initial log rabbitmq singleton instance failed.", e);
            }
        }
        if (LogHubUtils.Kafka.existDependency()) {
            try {
                KafkaLogProperties.initialSingletonInstance(binder);
            } catch (Exception e) {
                log.warn("Initial log kafka singleton instance failed.", e);
            }
        }
        try {
            Slf4JLogProperties.initialSingletonInstance(binder);
        } catch (Exception e) {
            log.warn("Initial slf4j log singleton instance failed.", e);
        }
        try {
            ConsoleLogProperties.initialSingletonInstance(binder);
        } catch (Exception e) {
            log.warn("Initial console log singleton instance failed.", e);
        }

    }


    // step 4: An ApplicationPreparedEvent is sent just before the refresh is started, but after bean definitions have been loaded.
    @Override
    protected void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        try {
            if (ClassLoaderSugar.isPresent("org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext")
                    && applicationContext.getClass().equals(AnnotationConfigServletWebServerApplicationContext.class)
                    && !SpringContextHolder.hasLoaded())
                SpringContextHolder.setSingletonApplicationContext(applicationContext);
        } catch (Exception e) {
            log.warn("LogHub onApplicationPreparedEvent", e);
        }
    }

    @Override
    public int getOrder() {
        return order;
    }


}
