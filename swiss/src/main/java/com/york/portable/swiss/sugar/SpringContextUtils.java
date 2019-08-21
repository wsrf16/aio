package com.york.portable.swiss.sugar;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    /**
     * 获取静态变量中的ApplicationContext.
     * @return
     */
    public final static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public final static <T extends ApplicationContext> T getSimilarApplicationContext() {
        return (T)applicationContext;
    }

    public final static ConfigurableApplicationContext getConfigurableApplicationContext() {
        return (ConfigurableApplicationContext)getSimilarApplicationContext();
    }

    public final static <T extends BeanFactory> T getSimilarBeanFactory() {
        return (T)getConfigurableApplicationContext().getBeanFactory();
    }

    public final static DefaultListableBeanFactory getDefaultListableBeanFactory() {
        return ((DefaultListableBeanFactory)getSimilarBeanFactory());
    }

    public final static ListableBeanFactory getListableBeanFactory() {
        return getConfigurableApplicationContext().getBeanFactory();
    }

    public final static BeanFactory getBeanFactory() {
        return getConfigurableApplicationContext().getBeanFactory();
    }

    public static class BeanCenter {
        public final static <T> T getBean(String beanName) {
            return (T)getBeanFactory().getBean(beanName);
        }

        public final static <T> T getBean(Class<T> clazz) {
            return getBeanFactory().getBean(clazz);
        }

        public final static void remove(String beanName) {
            getDefaultListableBeanFactory().removeBeanDefinition(beanName);
        }

        public final static void registry(String beanName, BeanDefinition beanDefinition) {
            DefaultListableBeanFactory defaultListableBeanFactory = getDefaultListableBeanFactory();
            defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
        }

        public final static BeanDefinition getRawBeanDefinition(Class<?> clazz) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            return beanDefinitionBuilder.getRawBeanDefinition();
        }

        public final static void registry(String beanName, Class<?> clazz) {
            BeanDefinition rawBeanDefinition = getRawBeanDefinition(clazz);
            registry(beanName, rawBeanDefinition);
        }
    }


    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     * @param name
     * @param <T>
     * @return
     */
    public final static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中得到Bean, 自动转型为所赋值对象的类型.
     * @param requiredType
     * @param <T>
     * @return
     */
    public final static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public final static void clearHolder() {
        applicationContext = null;
    }

}