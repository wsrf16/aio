package com.aio.portable.swiss.factories.context;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.core.env.ConfigurableEnvironment;

// GenericApplicationListener
// SmartApplicationListener
// ConfigFileApplicationListener
public abstract class AbstractGenericApplicationListener implements GenericApplicationListener {
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
    private static final Class<?>[] SOURCE_TYPES = {SpringApplication.class, ApplicationContext.class};

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
    protected void onApplicationStartingEvent(ApplicationStartingEvent event) {
    }

    // step 2: An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the context is known, but before the context is created.
    protected void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
    }

    // step 3
    protected void onApplicationContextInitializedEvent(ApplicationContextInitializedEvent event) {
    }

    // step 4: An ApplicationPreparedEvent is sent just before the refresh is started, but after bean definitions have been loaded.
    protected void onApplicationPreparedEvent(ApplicationPreparedEvent event) {
    }

    // step 5
    protected void onContextRefreshedEvent(ContextRefreshedEvent event) {
    }

    // step 6: An ApplicationStartedEvent is sent after the context has been refreshed but before any application and command-line runners have been called
    protected void onApplicationStartedEvent(ApplicationStartedEvent event) {
    }

    // step 7: An ApplicationReadyEvent is sent after any application and command-line runners have been called. It indicates that the application is ready to service requests
    protected void onApplicationReadyEvent(ApplicationReadyEvent event) {
    }

    // step 8:
    protected void onContextClosedEvent() {
    }

    // step 9: An ApplicationFailedEvent is sent if there is an exception on startup.
    protected void onApplicationFailedEvent() {
    }

    private boolean isSet(ConfigurableEnvironment environment, String property) {
        String value = environment.getProperty(property);
        return value != null && !value.equals("false");
    }

    void registerShutdownHook(Thread shutdownHook) {
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

}
