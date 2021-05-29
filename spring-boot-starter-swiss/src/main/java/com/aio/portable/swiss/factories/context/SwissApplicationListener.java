package com.aio.portable.swiss.factories.context;

import com.aio.portable.swiss.sugar.SpringContextHolder;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.impl.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.*;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

// GenericApplicationListener
// SmartApplicationListener
// ConfigFileApplicationListener
public class SwissApplicationListener implements EnvironmentPostProcessor, GenericApplicationListener, Ordered {
    public static final int DEFAULT_ORDER = Integer.MIN_VALUE + 20;
    private static final Class<?>[] EVENT_TYPES = {ApplicationStartingEvent.class,
            ApplicationEnvironmentPreparedEvent.class, ApplicationPreparedEvent.class, ContextClosedEvent.class,
            ApplicationFailedEvent.class, ApplicationReadyEvent.class, ApplicationStartedEvent.class,
            ContextRefreshedEvent.class, ApplicationContextInitializedEvent.class};
    private static final Class<?>[] SOURCE_TYPES = {SpringApplication.class, ApplicationContext.class};
    private static final Log log = LogFactory.getLog(SwissApplicationListener.class);
    private int order = DEFAULT_ORDER;

//    @Override
//    public boolean supportsEventType(Class<? extends ApplicationEvent> resolvableType) {
//        return this.isAssignableFrom(resolvableType, EVENT_TYPES);
//    }

    @Override
    public boolean supportsEventType(ResolvableType resolvableType) {
        return this.isAssignableFrom(resolvableType.getRawClass(), EVENT_TYPES);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return this.isAssignableFrom(sourceType, SOURCE_TYPES);
    }

    private boolean isAssignableFrom(Class<?> type, Class<?>... supportedTypes) {
        if (type != null) {
            for (Class<?> supportedType : supportedTypes) {
                if (supportedType.isAssignableFrom(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartingEvent) {
            this.onApplicationStartingEvent((ApplicationStartingEvent) event);
        } else if (event instanceof ApplicationEnvironmentPreparedEvent) {
            this.onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent) event);
        } else if (event instanceof ApplicationPreparedEvent) {
            this.onApplicationPreparedEvent((ApplicationPreparedEvent) event);
        } else if (event instanceof ContextClosedEvent && ((ContextClosedEvent) event).getApplicationContext().getParent() == null) {
            this.onContextClosedEvent();
        } else if (event instanceof ApplicationReadyEvent) {
            this.onApplicationReadyEvent((ApplicationReadyEvent) event);
        } else if (event instanceof ApplicationStartedEvent) {
            this.onApplicationStartedEvent((ApplicationStartedEvent) event);
        } else if (event instanceof ContextRefreshedEvent) {
            this.onContextRefreshedEvent((ContextRefreshedEvent) event);
        } else if (event instanceof ApplicationContextInitializedEvent) {
            this.onApplicationContextInitializedEvent((ApplicationContextInitializedEvent) event);
        } else if (event instanceof ApplicationFailedEvent) {
            this.onApplicationFailedEvent();
        }
    }

    // step 1: An ApplicationStartingEvent is sent at the start of a run, but before any processing except the registration of listeners and initializers.
    private void onApplicationStartingEvent(ApplicationStartingEvent event) {
    }

    // step 2: An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the context is known, but before the context is created.
    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
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
                RabbitMQLogProperties.importSingletonInstance(binder);
            } catch (Exception e) {
//                e.printStackTrace();
                log.warn("Import rabbitmq singleton instance failed.", e);
            }
        }
        if (LogHubUtils.Kafka.existDependency()) {
            try {
                KafkaLogProperties.importSingletonInstance(binder);
            } catch (Exception e) {
//                e.printStackTrace();
                log.warn("Import kafka singleton instance failed.", e);
            }
        }
        try {
            Slf4JLogProperties.importSingletonInstance(binder);
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn("Import kafka singleton instance failed.", e);
        }
        try {
            ConsoleLogProperties.importSingletonInstance(binder);
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn("Import kafka singleton instance failed.", e);
        }

//        log.error("InitializeLogProperties error", e);
    }

    // step 3
    private void onApplicationContextInitializedEvent(ApplicationContextInitializedEvent event) {
    }

    // step 4: An ApplicationPreparedEvent is sent just before the refresh is started, but after bean definitions have been loaded.
    private void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        final ConfigurableApplicationContext applicationContext = event.getApplicationContext();
        try {
            if (ClassSugar.exist("org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext") && applicationContext.getClass().equals(AnnotationConfigServletWebServerApplicationContext.class) && !SpringContextHolder.hasLoad())
                SpringContextHolder.importApplicationContext(applicationContext);
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn("LogHub onApplicationPreparedEvent", e);
        }
    }

    // step 5
    private void onContextRefreshedEvent(ContextRefreshedEvent event) {
    }

    // step 6: An ApplicationStartedEvent is sent after the context has been refreshed but before any application and command-line runners have been called
    private void onApplicationStartedEvent(ApplicationStartedEvent event) {
    }

    // step 7: An ApplicationReadyEvent is sent after any application and command-line runners have been called. It indicates that the application is ready to service requests
    private void onApplicationReadyEvent(ApplicationReadyEvent event) {
    }


    private static void initLogHubFactory(ConfigurableListableBeanFactory beanFactory) {
        try {
            LogHubUtils.importSingletonLogFactory(beanFactory);
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn("Failed to initial LogHubFactory singleton instance.", e);
        }
    }

    // step 8:
    private void onContextClosedEvent() {
    }

    // step 9: An ApplicationFailedEvent is sent if there is an exception on startup.
    private void onApplicationFailedEvent() {
    }

    private boolean isSet(ConfigurableEnvironment environment, String property) {
        String value = environment.getProperty(property);
        return value != null && !value.equals("false");
    }

//    private void initialize(ConfigurableEnvironment environment) {
//        initializeLogProperties(environment);
//    }


    void registerShutdownHook(Thread shutdownHook) {
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    @Override
    public int getOrder() {
        return order;
    }


}
