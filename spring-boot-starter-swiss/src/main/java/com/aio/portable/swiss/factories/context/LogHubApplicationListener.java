package com.aio.portable.swiss.factories.context;

import com.aio.portable.swiss.suite.log.impl.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4jLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;

// GenericApplicationListener
// SmartApplicationListener
// ConfigFileApplicationListener
public class LogHubApplicationListener implements EnvironmentPostProcessor, GenericApplicationListener, Ordered {
    public static final int DEFAULT_ORDER = Integer.MIN_VALUE + 20;
    private static final Class<?>[] EVENT_TYPES = {ApplicationStartingEvent.class,
            ApplicationEnvironmentPreparedEvent.class, ApplicationPreparedEvent.class, ContextClosedEvent.class,
            ApplicationFailedEvent.class};
    private static final Class<?>[] SOURCE_TYPES = {SpringApplication.class, ApplicationContext.class};
    private static final Log logger = LogFactory.getLog(LogHubApplicationListener.class);
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
        } else if (event instanceof ApplicationFailedEvent) {
            this.onApplicationFailedEvent();
        }

    }

    private void onApplicationStartingEvent(ApplicationStartingEvent event) {
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        this.postProcessEnvironment(event.getEnvironment(), event.getSpringApplication());
    }

    private void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
        ConfigurableListableBeanFactory beanFactory = event.getApplicationContext().getBeanFactory();
    }

    private void onContextClosedEvent() {
    }

    private void onApplicationFailedEvent() {
    }


//    private void initializeEarlyLoggingLevel(ConfigurableEnvironment environment) {
//        if (this.parseArgs && this.springBootLogging == null) {
//            if (this.isSet(environment, "debug")) {
//                this.springBootLogging = LogLevel.DEBUG;
//            }
//
//            if (this.isSet(environment, "trace")) {
//                this.springBootLogging = LogLevel.TRACE;
//            }
//        }
//
//    }

    private boolean isSet(ConfigurableEnvironment environment, String property) {
        String value = environment.getProperty(property);
        return value != null && !value.equals("false");
    }

//    private void initialize(ConfigurableEnvironment environment) {
//        initializeLogProperties(environment);
//    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        logger.debug("LogHubApplicationListener.postProcessEnvironment ConfigurableEnvironment: " + environment);
        initializeLogProperties(environment);
    }

    private static void initializeLogProperties(ConfigurableEnvironment environment) {
        final Binder binder = Binder.get(environment);
        try {
            if (LogHubUtils.RabbitMQ.existDependency())
                RabbitMQLogProperties.importSingleton(binder);
            if (LogHubUtils.Kafka.existDependency())
                KafkaLogProperties.importSingleton(binder);
            Slf4jLogProperties.importSingleton(binder);
            ConsoleLogProperties.importSingleton(binder);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("initializeLogProperties error", e);
//            throw new IllegalStateException("initializeLogProperties error", e);

//            logger.error("Cannot bind to SpringApplication", e);
//            throw new IllegalStateException("Cannot bind to SpringApplication", e);
        }
    }


//    private void registerShutdownHookIfNecessary(Environment environment, LoggingSystem loggingSystem) {
//        boolean registerShutdownHook = (Boolean)environment.getProperty("logging.register-shutdown-hook", Boolean.class, false);
//        if (registerShutdownHook) {
//            Runnable shutdownHandler = loggingSystem.getShutdownHandler();
//            if (shutdownHandler != null && shutdownHookRegistered.compareAndSet(false, true)) {
//                this.registerShutdownHook(new Thread(shutdownHandler));
//            }
//        }
//    }

    void registerShutdownHook(Thread shutdownHook) {
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

    @Override
    public int getOrder() {
        return order;
    }


}
