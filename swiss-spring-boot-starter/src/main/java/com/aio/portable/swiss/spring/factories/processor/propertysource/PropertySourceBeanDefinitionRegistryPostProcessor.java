package com.aio.portable.swiss.spring.factories.processor.propertysource;

import com.aio.portable.swiss.spring.SpringContextHolder;
import com.aio.portable.swiss.spring.factories.processor.propertysource.convert.PropertySourcesConverter;
import com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper.EnumerablePropertySourceWrapper;
import com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper.MapPropertySourceWrapper;
import com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper.PropertySourceWrapper;
import com.aio.portable.swiss.spring.factories.processor.propertysource.wrapper.SystemEnvironmentPropertySourceWrapper;
import com.aio.portable.swiss.suite.bean.structure.KeyValuePair;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import java.lang.reflect.Modifier;
import java.util.stream.Stream;

//@Configuration
public abstract class PropertySourceBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    //    private static final Log log = LogFactory.getLog(PropertySourceBeanDefinitionRegistryPostProcessor.class);
    private static final LocalLog log = LocalLog.getLog(PropertySourceBeanDefinitionRegistryPostProcessor.class);

    private ConfigurableEnvironment environment;

    public PropertySourceBeanDefinitionRegistryPostProcessor() {
        this.environment = SpringContextHolder.getStandardServletEnvironment();
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
        // MutablePropertySources：当前环境下的所有properties、yaml、yml文件
        MutablePropertySources propertySources = environment.getPropertySources();
        convertPropertySource(propertySources);
    }

    protected void convertPropertySource(MutablePropertySources propertySources) {
        PropertySourcesConverter converter = new PropertySourcesConverter();
        converter.setFilter(this::filter);
        converter.setPropertySourceConvert(this::propertySourceConvert);
        converter.setPropertyNameValueConvert(this::propertyNameValueConvert);
        converter.replace(propertySources);
    }


    public boolean filter(PropertySource<?> propertySource) {
        return true;
    }

    private PropertySource<?> propertySourceConvert(PropertySource<?> propertySource) {
        PropertySource<?> propertySourceProxy = ofPropertySourceWrapper(propertySource);
        return propertySourceProxy;
    }

    private PropertySource<?> ofPropertySourceWrapper(PropertySource<?> propertySource) {
        if (needsProxyAnyway(propertySource))
            return ofPropertySourceProxy(propertySource);

        if (propertySource instanceof SystemEnvironmentPropertySource) {
            return new SystemEnvironmentPropertySourceWrapper((SystemEnvironmentPropertySource) propertySource, this::propertyNameValueConvert);
        } else if (propertySource instanceof MapPropertySource) {
            return new MapPropertySourceWrapper((MapPropertySource) propertySource, this::propertyNameValueConvert);
        } else  if (propertySource instanceof EnumerablePropertySourceWrapper) {
            return new EnumerablePropertySourceWrapper((EnumerablePropertySource) propertySource, this::propertyNameValueConvert);
        } else {
            return new PropertySourceWrapper(propertySource, this::propertyNameValueConvert);
        }
    }

    private PropertySource<?> ofPropertySourceProxy(PropertySource<?> propertySource) {
        if (!CommandLinePropertySource.class.isAssignableFrom(propertySource.getClass()) && !Modifier.isFinal(propertySource.getClass().getModifiers())) {
            ProxyFactory proxyFactory = new ProxyFactory();
            proxyFactory.setTargetClass(propertySource.getClass());
            proxyFactory.setProxyTargetClass(true);
            proxyFactory.setTarget(propertySource);
            proxyFactory.addAdvice(new MethodInterceptor() {
                @Override
                public Object invoke(MethodInvocation invocation) throws Throwable {
                    Object proceed = invocation.proceed();
                    if (isGetPropertyCall(invocation)) {
                        String name = (String) invocation.getArguments()[0];
                        Object value = propertyNameValueConvert(propertySource, new KeyValuePair<>(name, proceed));
                        return value;
                    }
                    return proceed;
                }
            });
            PropertySource<?> proxy = (PropertySource<?>) proxyFactory.getProxy();
            return proxy;
        } else {
            return this.ofPropertySourceWrapper(propertySource);
        }
    }

    private static boolean needsProxyAnyway(PropertySource<?> propertySource) {
        return needsProxyAnyway(propertySource.getClass().getName());
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

    protected abstract Object propertyNameValueConvert(PropertySource<?> propertySource, KeyValuePair<String, Object> nameValuePair);
}

