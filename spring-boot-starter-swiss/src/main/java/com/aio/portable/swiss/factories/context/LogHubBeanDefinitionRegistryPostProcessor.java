package com.aio.portable.swiss.factories.context;

import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

public class LogHubBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static final Log log = LogFactory.getLog(LogHubBeanDefinitionRegistryPostProcessor.class);

    /**
     * 注册自定义bean
     *
     * @param registry
     * @throws BeansException
     */
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }


    /**
     * 主要是用来自定义修改持有的bean
     * ConfigurableListableBeanFactory 其实就是DefaultListableBeanDefinition对象
     *
     * @param beanFactory
     * @throws BeansException
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (!LogHubFactory.isInitial())
            initLogHubFactory(beanFactory);
    }

    private static void initLogHubFactory(ConfigurableListableBeanFactory beanFactory) {
        String[] names = beanFactory.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            BeanDefinition definition = beanFactory.getBeanDefinition(names[i]);
            if (definition.getBeanClassName() != null && definition instanceof ScannedGenericBeanDefinition) {
                ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) definition;
                AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();
                if (metadata != null && Objects.equals(LogHubFactory.class.getTypeName(), metadata.getSuperClassName())) {
                    try {
                        final Class<?> clazz = scannedGenericBeanDefinition.resolveBeanClass(Thread.currentThread().getContextClassLoader());
                        clazz.newInstance();
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                        log.warn("Failed to initial LogHubFactory singleton instance.", e);
                    }
                }
            }
        }
    }
}

