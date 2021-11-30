package com.aio.portable.swiss.spring;

import com.aio.portable.swiss.sugar.StackTraceSugar;
import com.aio.portable.swiss.sugar.type.StringSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class SpringContextHolder implements ApplicationContextAware {
    private final static Log log = LogFactory.getLog(SpringContextHolder.class);

    private static ApplicationContext applicationContext = null;


    public final static boolean hasLoaded() {
        return applicationContext != null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        synchronized(SpringContextHolder.class) {
//            SpringContextHolder.applicationContext = applicationContext;
            if (SpringContextHolder.applicationContext == null)
                SpringContextHolder.applicationContext = applicationContext;
            else {
                log.warn("applicationContext is not null");
//                new UnsupportedOperationException().printStackTrace();
            }
        }
    }

    /**
     * 获取静态变量中的ApplicationContext.
     *
     * @return
     */
    public final static <T extends ApplicationContext> T getApplicationContext() {
        return (T) applicationContext;
    }

    public final static ConfigurableApplicationContext getConfigurableApplicationContext() {
        return SpringContextHolder.<ConfigurableApplicationContext>getApplicationContext();
    }

    public final static <T extends BeanFactory> T getBeanFactory() {
        return (T) getConfigurableApplicationContext().getBeanFactory();
    }

    public final static DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return getBeanFactory();
    }

    public final static ListableBeanFactory getListableBeanFactory() {
        return getConfigurableApplicationContext().getBeanFactory();
    }

    public final static ConfigurableApplicationContext run(Class<?> primarySource, String[] args, Consumer<SpringApplication> otherAction) {
        SpringApplication springApplication = new SpringApplication(primarySource);
        otherAction.accept(springApplication);
        return springApplication.run(args);
    }

    public final static Class<?> deduceMainApplicationClass() {
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

    public static class BeanCenter {
        public static String getBeanName(@Nullable String simpleClassName) {
            return Introspector.decapitalize(simpleClassName);
        }

        public final static <T> T getBean(@Nullable String beanName) {
            return (T) getBeanFactory().getBean(beanName);
        }

        public final static <T> T getBean(@Nullable Class<T> clazz) {
            return getBeanFactory().getBean(clazz);
        }

        public final static void remove(@Nullable String beanName) {
            getDefaultListableBeanFactory().removeBeanDefinition(beanName);
        }

        public final static void registry(@Nullable String beanName, @Nullable BeanDefinition beanDefinition) {
            DefaultListableBeanFactory defaultListableBeanFactory = getDefaultListableBeanFactory();
            defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
        }

        public final static BeanDefinition getRawBeanDefinition(@Nullable Class<?> clazz, @Nullable Map<String, Object> propertyValueMap) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            for (Map.Entry<String, Object> stringObjectEntry : propertyValueMap.entrySet()) {
                String name = stringObjectEntry.getKey();
                Object value = stringObjectEntry.getValue();
                beanDefinitionBuilder.addPropertyValue(name, value);
            }
            return beanDefinitionBuilder.getRawBeanDefinition();
        }

        public final static void registry(@Nullable String beanName, @Nullable Class<?> clazz, @Nullable Map<String, Object> propertyValueMap) {
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
    public final static <T> T getBean(@Nullable String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     *
     * @param requiredType
     * @param <T>
     * @return
     */
    public final static <T> T getBean(@Nullable Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public final static <T> Map<String, T> getBeansOfType(@Nullable Class<T> clazz) throws BeansException {
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * getEnvironment
     * @return
     */
    public final static ConfigurableEnvironment getEnvironment() {
        return (ConfigurableEnvironment) applicationContext.getEnvironment();
    }

    /**
     * getPropertyBean
     * @param environment
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> T getPropertyBean(ConfigurableEnvironment environment, String name, Class<T> clazz) {
        BindResult<T> bind = Binder.get(environment).bind(name, clazz);
        return bind.isBound() ? bind.get() : null;
    }

    /**
     * getPropertyBean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> T getPropertyBean(String name, Class<T> clazz) {
        BindResult<T> bind = Binder.get(getEnvironment()).bind(name, clazz);
        T t = bind != null && bind.isBound() ? bind.get() : null;
        return t;
    }


    public final static <T> T getPropertyBean(Class<T> clazz) {
        boolean present = clazz.isAnnotationPresent(ConfigurationProperties.class);
        if (!present)
            return null;

        ConfigurationProperties[] annotationsByType = clazz.getAnnotationsByType(ConfigurationProperties.class);
        String name = StringSugar.getFirstHasText(null, annotationsByType[0].prefix(), annotationsByType[0].value());
        if (name == null)
            return null;
        BindResult<T> bind = Binder.get(getEnvironment()).bind(name, clazz);
        if (bind == null || !bind.isBound())
            return null;
        return bind.get();
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public final static void clearHolder() {
        applicationContext = null;
    }

    public final static void initialApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * restart
     */
    public final static void restart(Class<?> primarySource, String[] args) {
        SpringContextHolder.<ConfigurableApplicationContext>getApplicationContext().close();
        SpringApplication.run(primarySource, args);
    }
}