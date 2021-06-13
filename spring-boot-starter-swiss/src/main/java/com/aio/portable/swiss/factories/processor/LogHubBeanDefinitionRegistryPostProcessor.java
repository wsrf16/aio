package com.aio.portable.swiss.factories.processor;

import com.aio.portable.swiss.suite.log.support.LogHubUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class LogHubBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static final Log log = LogFactory.getLog(LogHubBeanDefinitionRegistryPostProcessor.class);

    /**
     * 注册自定义bean
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        try {
//            LogHubUtils.initLogHubFactory(registry);
//        } catch (Exception e) {
////            e.printStackTrace();
//            log.warn("InitLogHubFactory failed.", e);
//        }
    }


    /**
     * 主要是用来自定义修改持有的bean
     * ConfigurableListableBeanFactory 其实就是DefaultListableBeanDefinition对象
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            LogHubUtils.initLogHubFactory(beanFactory);
        } catch (Exception e) {
//            e.printStackTrace();
            log.warn("InitLogHubFactory failed.", e);
        }
    }

}

