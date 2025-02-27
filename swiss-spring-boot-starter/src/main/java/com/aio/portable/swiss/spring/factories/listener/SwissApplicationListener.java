package com.aio.portable.swiss.spring.factories.listener;

import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import com.aio.portable.swiss.suite.log.solution.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;
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
//    private static final Log log = LogFactory.getLog(SwissApplicationListener.class);
    private static final LocalLog log = LocalLog.getLog(SwissApplicationListener.class);
    private int order = DEFAULT_ORDER;


    // step 2: An ApplicationEnvironmentPreparedEvent is sent when the Environment to be used in the context is known, but before the context is created.
    @Override
    protected void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        try {
            Class<?> mainApplicationClass = event.getSpringApplication().getMainApplicationClass();
            if (mainApplicationClass == null)
                return;
            Class<?> clazz = ClassLoaderSugar.forName(mainApplicationClass.getName());
            SpringContextHolder.importMainApplicationClass(clazz);
        } catch (Exception e) {
            log.warn("Initial SpringContextHolder MainApplicationClass singleton instance failed.", e);
        }

        this.postProcessEnvironment(event.getEnvironment(), event.getSpringApplication());
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.debug("SwissApplicationListener.postProcessEnvironment ConfigurableEnvironment: " + environment);

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

//        this.registerTextEncryptor(applicationContext);
        try {
            if (ClassLoaderSugar.isPresent("org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext")
                    && applicationContext.getClass().equals(AnnotationConfigServletWebServerApplicationContext.class)
                    && !SpringContextHolder.hasLoaded())
                SpringContextHolder.setSingletonApplicationContext(applicationContext);
        } catch (Exception e) {
            log.warn("SwissApplicationListener#onApplicationPreparedEvent", e);
        }
    }

//    private static void registerTextEncryptor(ConfigurableApplicationContext applicationContext) {
//        if(applicationContext instanceof AnnotationConfigApplicationContext){
//            ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
////            if(!beanFactory.containsBean("textEncryptor")){
////                beanFactory.registerSingleton("textEncryptor",new TextEncryptor(){
////
////                    @Override
////                    public String encrypt(String text) {
////                        System.out.println("=====================================加密");
////                        return "加密"+text;
////                    }
////
////                    @Override
////                    public String decrypt(String encryptedText) {
////                        //这里解密就直接输出日志，然后直接解密返回
////                        System.out.println("=====================================解密");
//////                        return EncryptUtil.decrypt("work0", encryptedText);
//////                        return StringSugar.trimStart(encryptedText, "加密");
////                        return "解密" + encryptedText;
////                    }
////                });
////            }
//        }
//
//    }

    @Override
    public int getOrder() {
        return order;
    }


}
