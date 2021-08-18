package com.aio.portable.swiss.factories.listener;

import com.aio.portable.swiss.factories.listener.propertysource.PropertySourcesMutator;
import com.aio.portable.swiss.suite.bean.type.KeyValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.origin.Origin;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class PropertySourceApplicationListener extends AbstractGenericApplicationListener implements ApplicationContextInitializer<ConfigurableApplicationContext>, EnvironmentPostProcessor, Ordered {
    private static final Log log = LogFactory.getLog(PropertySourceApplicationListener.class);

//    public PropertySourceApplicationListener() {
//    }

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
        mutator.setFilter(this::filter);
        mutator.setReplace(this::replace);
        mutator.setItemReplace(this::replace);
        try {
            mutator.mutate(propertySources);
        } catch (Exception e) {
            log.warn(e);
        }
    }

    public boolean filter(PropertySource<?> propertySource) {
        return propertySource instanceof OriginTrackedMapPropertySource;
//        return propertySource instanceof OriginTrackedMapPropertySource || propertySource instanceof CompositePropertySource;
    }

    public void replace(PropertySource<?> propertySource) {
        if (propertySource instanceof OriginTrackedMapPropertySource) {
            Map<String, OriginTrackedValue> map = (HashMap) propertySource.getSource();
            Iterator<Map.Entry<String, OriginTrackedValue>> iterator = map.entrySet().iterator();
            Map<String, OriginTrackedValue> addition = new HashMap<>();
            while (iterator.hasNext()) {
                Map.Entry<String, OriginTrackedValue> c = iterator.next();
                String key = c.getKey();
                // "applicationConfig: [classpath:/config/application-dev.yml]"
                Origin origin = c.getValue().getOrigin();
                Object value = c.getValue().getValue();
                KeyValuePair<String, Object> targetPair = this.replace(key, value);
                String targetKey = targetPair.getKey();
                Object targetValue = targetPair.getValue();

                if (key != targetKey) {
                    iterator.remove();
                    addition.put(targetKey, OriginTrackedValue.of(targetValue, origin));
                } else if (value != targetValue)
                    map.put(targetKey, OriginTrackedValue.of(targetValue, origin));
            }
            map.putAll(addition);



//            map.entrySet().stream().forEach(c -> {
//                String key = c.getKey();
//                // "applicationConfig: [classpath:/config/application-dev.yml]"
//                Origin origin = c.getValue().getOrigin();
//                Object value = c.getValue().getValue();
//                KeyValuePair<String, Object> targetPair = this.replace(key, value);
//                String targetKey = targetPair.getKey();
//                Object targetValue = targetPair.getValue();
//
//                if (key != targetKey) {
//                    map.remove(key);
//                    map.put(targetKey, OriginTrackedValue.of(targetValue, origin));
//                } else if (value != targetValue)
//                    map.put(targetKey, OriginTrackedValue.of(targetValue, origin));
//            });
        }

//        if (propertySource instanceof CompositePropertySource) {
//        }
    }

    public KeyValuePair<String, Object> replace(String key, Object value) {
        return new KeyValuePair<>(key, value);
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