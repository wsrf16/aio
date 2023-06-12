package com.aio.portable.swiss.spring.factories.processor.propertysource;

import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.env.*;

import java.util.stream.Stream;

//@Configuration
public abstract class PropertySourceBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
//    private static final Log log = LogFactory.getLog(PropertySourceBeanDefinitionRegistryPostProcessor.class);
    private static final LocalLog log = LocalLog.getLog(PropertySourceBeanDefinitionRegistryPostProcessor.class);

    private ConfigurableEnvironment environment;

    public PropertySourceBeanDefinitionRegistryPostProcessor() {
        this.environment = SpringContextHolder.getEnvironment();
    }

    public PropertySourceBeanDefinitionRegistryPostProcessor(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

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
        MutablePropertySources propertySources = environment.getPropertySources();
        convertPropertySource(propertySources);
    }

    protected void convertPropertySource(MutablePropertySources propertySources) {
        PropertySourcesConverter converter = new PropertySourcesConverter();
        converter.setFilter(this::filter);
        converter.setPropertySourceConvert(this::propertySourceConvert);
        converter.setPropertyValueConvert(this::notNullPropertyValueConvert);
        try {
            converter.replace(propertySources);
        } catch (Exception e) {
            log.warn(e);
        }
    }


    public boolean filter(PropertySource<?> propertySource) {
        return true;
    }

    private PropertySource<?> propertySourceConvert(PropertySource<?> propertySource) {
        PropertySource<?> proxyPropertySource = wrapPropertySource(propertySource);
//        PropertySource<?> proxyPropertySource = proxyPropertySource(propertySource);
        return proxyPropertySource;
    }


    private final Object notNullPropertyValueConvert(String key, Object value) {
        return value == null ? null : propertyValueConvert(key, value);
    }

    public abstract Object propertyValueConvert(String key, Object value);
//    public Object intercept(String key, Object value) {
//        if (Objects.equals(value, "abc"))
//            return ("v" + "111111");
//        if (Objects.equals(key, "swagger.api-info.title"))
//            return (value + "111111");
//        if (Objects.equals(value, "对外接口在线文档"))
//            return (value + "111111");
//        return value;
//    }

    private PropertySource<?> wrapPropertySource(PropertySource<?> propertySource) {
        PropertySource<?> proxyPropertySource;
        if (needsProxyAnyway((propertySource.getClass()).getName())) {
            proxyPropertySource = proxyPropertySource(propertySource);
        } else if (propertySource instanceof EnumerablePropertySource) {
            proxyPropertySource = new PropertySourceWrapper(propertySource, this::notNullPropertyValueConvert);
        } else if (propertySource instanceof MapPropertySource) {
            proxyPropertySource = new PropertySourceWrapper(propertySource, this::notNullPropertyValueConvert);
        } else if (propertySource instanceof OriginTrackedMapPropertySource) {
            proxyPropertySource = new PropertySourceWrapper(propertySource, this::notNullPropertyValueConvert);
        } else {
            proxyPropertySource = new PropertySourceWrapper(propertySource, this::notNullPropertyValueConvert);
        }

        return proxyPropertySource;
    }

    private PropertySource<?> proxyPropertySource(PropertySource<?> propertySource) {
        if (CommandLinePropertySource.class.isAssignableFrom(propertySource.getClass())) {
            return wrapPropertySource(propertySource);
        } else {
            return createProxyPropertySource(propertySource);
        }
    }

    private PropertySource<?> createProxyPropertySource(PropertySource<?> propertySource) {
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetClass(propertySource.getClass());
        proxyFactory.setProxyTargetClass(true);
//        proxyFactory.addInterface(PropertySource.class);
        proxyFactory.setTarget(propertySource);
        proxyFactory.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                Object proceed = invocation.proceed();
                if (isGetPropertyCall(invocation)) {
                    String name = (String) invocation.getArguments()[0];
                    Object value = notNullPropertyValueConvert(name, proceed);
                    return value;
                }
                return proceed;
            }
        });
        PropertySource<?> proxy = (PropertySource<?>) proxyFactory.getProxy();
        return proxy;
    }

    private static boolean needsProxyAnyway(String className) {
        return Stream.of(
                "org.springframework.boot.context.config.ConfigFileApplicationListener$ConfigurationPropertySources",
                "org.springframework.boot.context.properties.source.ConfigurationPropertySourcesPropertySource"
        ).anyMatch(className::equals);
    }

    private boolean isGetPropertyCall(MethodInvocation invocation) {
        return invocation.getMethod().getName().equals("getProperty")
                && invocation.getMethod().getParameters().length == 1
                && invocation.getMethod().getParameters()[0].getType() == String.class;
    }
}

