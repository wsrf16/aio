package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Objects;

public class LogHubUtils {
    public static class Kafka {
        public final static String DEPENDENCY = "org.springframework.kafka.core.KafkaTemplate";
        public static boolean existDependency() {
            return ClassSugar.exist(DEPENDENCY);
        }
    }

    public static class RabbitMQ {
        public final static String DEPENDENCY = "org.springframework.amqp.rabbit.core.RabbitTemplate";

        public static boolean existDependency() {
            return ClassSugar.exist(DEPENDENCY);
        }
    }

    public final static void importSingletonLogFactory(ConfigurableListableBeanFactory beanFactory) {
        String[] names = beanFactory.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            BeanDefinition definition = beanFactory.getBeanDefinition(names[i]);
            if (definition.getBeanClassName() != null && definition instanceof ScannedGenericBeanDefinition) {
                ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) definition;
                AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();
                if (metadata != null && ClassSugar.isSuper(LogHubFactory.class, metadata.getSuperClassName())) {
                    try {
                        final Class<?> clazz = scannedGenericBeanDefinition.resolveBeanClass(Thread.currentThread().getContextClassLoader());
                        clazz.newInstance();
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public final static void importSingletonLogFactory(BeanDefinitionRegistry registry) {
        String[] names = registry.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            BeanDefinition definition = registry.getBeanDefinition(names[i]);
            if (definition.getBeanClassName() != null && definition instanceof ScannedGenericBeanDefinition) {
                ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) definition;
                AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();
                if (metadata != null && ClassSugar.isSuper(LogHubFactory.class, metadata.getSuperClassName())) {
                    try {
                        final Class<?> clazz = scannedGenericBeanDefinition.resolveBeanClass(Thread.currentThread().getContextClassLoader());
                        clazz.newInstance();
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    public static void initLogHubFactory(BeanDefinitionRegistry registry) {
        if (!LogHubFactory.isInitial())
            LogHubUtils.importSingletonLogFactory(registry);
    }

    public static void initLogHubFactory(ConfigurableListableBeanFactory beanFactory) {
        if (!LogHubFactory.isInitial())
            LogHubUtils.importSingletonLogFactory(beanFactory);
    }


}
