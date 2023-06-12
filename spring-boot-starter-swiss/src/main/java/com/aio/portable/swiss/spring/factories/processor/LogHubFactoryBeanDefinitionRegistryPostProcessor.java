package com.aio.portable.swiss.spring.factories.processor;

import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LogHubUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class LogHubFactoryBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
//    private static final Log log = LogFactory.getLog(LogHubFactoryBeanDefinitionRegistryPostProcessor.class);
    private static final LocalLog log = LocalLog.getLog(LogHubFactoryBeanDefinitionRegistryPostProcessor.class);

    /**
     * 注册自定义bean
     *
     * @param registry
     * @throws BeansException
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
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
        // 此处已autowired加载完
        initLogHubFactorySilently(beanFactory);
    }

    private void initLogHubFactorySilently(ConfigurableListableBeanFactory beanFactory) {
        try {
            LogHubUtils.initLogHubFactory(beanFactory);
        } catch (Exception e) {
            log.warn("InitLogHubFactory failed.", e);
        }
    }

}

