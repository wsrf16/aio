package com.aio.portable.swiss.sugar;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.beans.Introspector;
import java.util.Map;

@Component
public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    private static Class<?> primarySource;

    private static String[] args;

    public static Class<?> getPrimarySource() {
        return primarySource;
    }

    public static String[] getArgs() {
        return args;
    }

    public static void beReadyForStarting(Class<?> primarySource, String[] args) {
        SpringContextHolder.primarySource = primarySource;
        SpringContextHolder.args = args;
    }

    public final static boolean hasLoad() {
        return applicationContext != null;
    }

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextHolder.applicationContext = applicationContext;
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
        return ((DefaultListableBeanFactory) getBeanFactory());
    }

    public final static ListableBeanFactory getListableBeanFactory() {
        return getConfigurableApplicationContext().getBeanFactory();
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
    public final static Environment getEnvironment() {
        return applicationContext.getEnvironment();
    }

    /**
     * getPropertiesBean
     * @param environment
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> T getPropertiesBean(ConfigurableEnvironment environment, String name, Class<T> clazz) {
        final BindResult<T> bind = Binder.get(environment).bind(name, clazz);
        return bind.isBound() ? bind.get() : null;
    }

    /**
     * getPropertiesBean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public final static <T> T getPropertiesBean(String name, Class<T> clazz) {
        final BindResult<T> bind = Binder.get(getEnvironment()).bind(name, clazz);
        return bind.isBound() ? bind.get() : null;
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public final static void clearHolder() {
        applicationContext = null;
    }

//    public final static void importApplicationContext(ApplicationContext applicationContext) {
//        SpringContextHolder.applicationContext = applicationContext;
//    }

    /**
     * restart
     */
    public final static void restart() {
        SpringContextHolder.<ConfigurableApplicationContext>getApplicationContext().close();
        SpringApplication.run(primarySource, args);
    }
}