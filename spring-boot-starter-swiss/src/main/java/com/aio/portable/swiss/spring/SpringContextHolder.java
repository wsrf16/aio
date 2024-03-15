package com.aio.portable.swiss.spring;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.SpringVersion;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.StandardServletEnvironment;

import java.beans.Introspector;
import java.util.Map;
import java.util.function.Consumer;

@Component
public abstract class SpringContextHolder implements ApplicationContextAware {
//    private static final Log log = LogFactory.getLog(SpringContextHolder.class);
    private static final LocalLog log = LocalLog.getLog(SpringContextHolder.class);

    private static ApplicationContext applicationContext = null;

    private static Class<?> mainApplicationClass;

    public static Class<?> getMainApplicationClass() {
        return mainApplicationClass;
    }

    public static String[] getMainApplicationClassArgs() {
        final ConfigurableApplicationContext context = SpringContextHolder.<ConfigurableApplicationContext>getApplicationContext();
        ApplicationArguments applicationArguments = context.getBean(ApplicationArguments.class);
        String[] args = applicationArguments.getSourceArgs();
        return args;
    }

    public static void importMainApplicationClass(Class<?> mainApplicationClass) {
        SpringContextHolder.mainApplicationClass = mainApplicationClass;
    }

    public static final boolean hasLoaded() {
        return applicationContext != null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        synchronized (SpringContextHolder.class) {
            if (applicationContext != null)
                SpringContextHolder.applicationContext = applicationContext;
//            if (SpringContextHolder.applicationContext == null)
//                SpringContextHolder.applicationContext = applicationContext;
//            else {
//                log.warn("applicationContext is not null");
////                new UnsupportedOperationException().printStackTrace();
//            }
        }
    }

    public static final void clearContext() {
        SpringContextHolder.applicationContext = null;
    }

    public static final void setSingletonApplicationContext(ApplicationContext applicationContext) {
        if (applicationContext != null
                && SpringContextHolder.applicationContext == null)
            SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * close
     */
    public static final void close() {
        SpringContextHolder.<ConfigurableApplicationContext>getApplicationContext().close();
    }

    /**
     * restart
     */
    public synchronized static final void restart() {
        Thread thread = new Thread(() -> {
            final ConfigurableApplicationContext context = SpringContextHolder.<ConfigurableApplicationContext>getApplicationContext();
            String[] args = getMainApplicationClassArgs();
            context.close();
            applicationContext = SpringApplication.run(mainApplicationClass, args);
        });
        thread.setDaemon(false);
        thread.start();
    }

    /**
     * 获取静态变量中的ApplicationContext.
     *
     * @return
     */
    public static final <T extends ApplicationContext> T getApplicationContext() {
        return (T) applicationContext;
    }

    public static final ConfigurableApplicationContext getConfigurableApplicationContext() {
        return SpringContextHolder.<ConfigurableApplicationContext>getApplicationContext();
    }

    public static final Binder getBinder() {
        return Binder.get(getConfigurableApplicationContext().getEnvironment());
    }

    public static final <T extends org.springframework.beans.factory.BeanFactory> T getBeanFactory() {
        return (T) getConfigurableApplicationContext().getBeanFactory();
    }

    public static final DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return getBeanFactory();
    }

    public static final ListableBeanFactory getListableBeanFactory() {
        return getConfigurableApplicationContext().getBeanFactory();
    }

    public static final ConfigurableApplicationContext doBeforeRunning(Class<?> primarySource, Consumer<SpringApplication> consumer, String[] args) {
        SpringApplication springApplication = new SpringApplication(primarySource);
        consumer.accept(springApplication);
        return springApplication.run(args);
    }

    public static final Class<?> deduceMainApplicationClass() {
        try {
            StackTraceElement[] stackTrace = StackTraceSugar.getStackTraceByException();
            for (StackTraceElement stackTraceElement : stackTrace) {
                if ("main".equals(stackTraceElement.getMethodName())) {
                    return Class.forName(stackTraceElement.getClassName());
                }
            }
        }
        catch (ClassNotFoundException ex) {
            // Swallow and continue
        }
        return null;
    }

    public static String getBeanName(@Nullable String simpleClassName) {
        return Introspector.decapitalize(simpleClassName);
    }

    public static class BeanFactory {

        public static final <T> T getBean(@Nullable String beanName) {
            return (T) getBeanFactory().getBean(beanName);
        }

        public static final <T> T getBean(@Nullable Class<T> clazz) {
            return getBeanFactory().getBean(clazz);
        }

        public static final void remove(@Nullable String beanName) {
            getDefaultListableBeanFactory().removeBeanDefinition(beanName);
        }

        public static final void registry(@Nullable String beanName, @Nullable BeanDefinition beanDefinition) {
            DefaultListableBeanFactory defaultListableBeanFactory = getDefaultListableBeanFactory();
            defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
        }

        public static final BeanDefinition getRawBeanDefinition(@Nullable Class<?> clazz, @Nullable Map<String, Object> propertyValueMap) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            for (Map.Entry<String, Object> stringObjectEntry : propertyValueMap.entrySet()) {
                String name = stringObjectEntry.getKey();
                Object value = stringObjectEntry.getValue();
                beanDefinitionBuilder.addPropertyValue(name, value);
            }
            return beanDefinitionBuilder.getRawBeanDefinition();
        }

        public static final void registry(@Nullable String beanName, @Nullable Class<?> clazz, @Nullable Map<String, Object> propertyValueMap) {
            BeanDefinition rawBeanDefinition = getRawBeanDefinition(clazz, propertyValueMap);
            registry(beanName, rawBeanDefinition);
        }
    }


    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     *
     * @param name
     * @param <T>
     * @return
     */
    public static final <T> T getBean(@Nullable String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public static final <T> T getBean(@Nullable Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static final <T> Map<String, T> getBeansOfType(@Nullable Class<T> clazz) throws BeansException {
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * getEnvironment
     * @return
     */
    public static final StandardServletEnvironment getStandardServletEnvironment() {
        return (StandardServletEnvironment) applicationContext.getEnvironment();
    }

    public static final String getSpringVersion() {
        return SpringVersion.getVersion();
    }

    public static final String getSpringBootVersion() {
        return SpringBootVersion.getVersion();
    }

}