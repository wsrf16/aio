package com.aio.portable.swiss.factories.context;

import com.aio.portable.swiss.suite.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.log.impl.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;
import com.aio.portable.swiss.suite.resource.ClassSugar;
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
        initializeLogProperties(environment);
    }

    private static void initializeLogProperties(ConfigurableEnvironment environment) {
        final Binder binder = Binder.get(environment);
        if (LogHubUtils.RabbitMQ.existDependency()) {
            try {
                RabbitMQLogProperties.initialSingletonInstance(binder);
            } catch (Exception e) {
//                e.printStackTrace();
                log.warn("Initial rabbitmq singleton instance failed.", e);
            }
        }
        if (LogHubUtils.Kafka.existDependency()) {
            try {
                KafkaLogProperties.initialSingletonInstance(binder);
            } catch (Exception e) {
//                e.printStackTrace();
                log.warn("Initial kafka singleton instance failed.", e);
            }
        }
        try {
            Slf4JLogProperties.initialSingletonInstance(binder);
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn("Initial kafka singleton instance failed.", e);
        }
        try {
            ConsoleLogProperties.initialSingletonInstance(binder);
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn("Initial kafka singleton instance failed.", e);
        }

    }


    // step 4: An ApplicationPreparedEvent is sent just before the refresh is started, but after bean definitions have been loaded.
    @Override
    protected void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        final ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        try {
            if (ClassSugar.exist("org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext") && applicationContext.getClass().equals(AnnotationConfigServletWebServerApplicationContext.class) && !SpringContextHolder.hasLoad())
                SpringContextHolder.initialApplicationContext(applicationContext);
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn("LogHub onApplicationPreparedEvent", e);
        }
    }

    @Override
    public int getOrder() {
        return order;
    }


}
