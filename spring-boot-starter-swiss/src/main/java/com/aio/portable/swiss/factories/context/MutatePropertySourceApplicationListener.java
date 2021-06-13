package com.aio.portable.swiss.factories.context;

import com.aio.portable.swiss.suite.properties.PropertySourcesMutator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

public abstract class MutatePropertySourceApplicationListener extends AbstractGenericApplicationListener implements ApplicationContextInitializer<ConfigurableApplicationContext>, EnvironmentPostProcessor, Ordered {
    private static final Log log = LogFactory.getLog(MutatePropertySourceApplicationListener.class);

    public MutatePropertySourceApplicationListener() {
    }

    @Override
    protected void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        this.postProcessEnvironment(event.getEnvironment(), event.getSpringApplication());
    }


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources propertySources = environment.getPropertySources();
        mutatePropertySource(propertySources);
    }

    protected void mutatePropertySource(MutablePropertySources propertySources) {
        PropertySourcesMutator mutator = new PropertySourcesMutator();
//        mutator.setResolver((name, value) -> {
//            // swagger.api-info.title
//            if (name.equals("swagger.api-info.title"))
//                return value + new Date().toString();
//            else
//                return value;
//        });
        mutator.setResolver(this::resolve);
        mutator.mutate(propertySources);
    }

    protected Object resolve(String name, Object value) {
        return value;
    }

//    private PropertySource<?> proxyPropertySource(PropertySource<?> propertySource, InterceptPropertyResolver propertyResolver) {
//        ProxyFactory proxyFactory = new ProxyFactory();
//        proxyFactory.setTargetClass(propertySource.getClass());
//        proxyFactory.setProxyTargetClass(true);
//        proxyFactory.addInterface(EncryptablePropertySource.class);
//        proxyFactory.setTarget(propertySource);
//        proxyFactory.addAdvice(new EncryptablePropertySourceMethodInterceptor(propertySource, resolver));
////        return (PropertySource)proxyFactory.getProxy();
//        propertySource
//    }


    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("Bootstraping jasypt-string-boot auto configuration in context: " + applicationContext.getId());
    }

    @Override
    public int getOrder() {
        return 2147483647;
    }
}