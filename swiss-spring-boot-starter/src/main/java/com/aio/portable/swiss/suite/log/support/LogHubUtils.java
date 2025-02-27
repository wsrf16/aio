package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MethodMetadataReadingVisitor;

public class LogHubUtils {
    public static class SLF4J {
        public static final String DEPENDENCY = "org.slf4j.Logger";
        public static boolean existDependency() {
            return ClassLoaderSugar.isPresent(DEPENDENCY);
        }
    }

    public static class Kafka {
        public static final String DEPENDENCY = "org.springframework.kafka.core.KafkaTemplate";
        public static final boolean existDependency() {
            return ClassLoaderSugar.isPresent(DEPENDENCY);
        }
    }

    public static class RabbitMQ {
        public static final String DEPENDENCY = "org.springframework.amqp.rabbit.core.RabbitTemplate";
        public static final boolean existDependency() {
            return ClassLoaderSugar.isPresent(DEPENDENCY);
        }
    }

    public static class Spring {
        public static final String DEPENDENCY = "org.springframework.beans.factory.InitializingBean";

        public static final boolean existDependency() {
            return ClassLoaderSugar.isPresent(DEPENDENCY);
        }
    }

    private static final void initialSingletonLogFactory(ConfigurableListableBeanFactory beanFactory) {
        String[] names = beanFactory.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            BeanDefinition definition = beanFactory.getBeanDefinition(names[i]);
            if (definition.getBeanClassName() != null && definition instanceof ScannedGenericBeanDefinition) {
                ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) definition;
                AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();
                if (metadata != null && metadata.getSuperClassName() != null && ClassSugar.isSuper(LogHubFactory.class, metadata.getSuperClassName())) {
                    try {
                        Class<?> customLogHubFactory = scannedGenericBeanDefinition.resolveBeanClass(ClassLoaderSugar.getDefaultClassLoader());
                        ClassSugar.newInstance(customLogHubFactory);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private static final void initialSingletonLogFactory(BeanDefinitionRegistry registry) {
        String[] names = registry.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            BeanDefinition definition = registry.getBeanDefinition(names[i]);
            if (definition.getBeanClassName() != null && definition instanceof ScannedGenericBeanDefinition) {
                ScannedGenericBeanDefinition scannedGenericBeanDefinition = (ScannedGenericBeanDefinition) definition;
                AnnotationMetadata metadata = scannedGenericBeanDefinition.getMetadata();
                if (metadata != null && metadata.getSuperClassName() != null && ClassSugar.isSuper(LogHubFactory.class, metadata.getSuperClassName())) {
                    try {
                        Class<?> customLogHubFactory = scannedGenericBeanDefinition.resolveBeanClass(ClassLoaderSugar.getDefaultClassLoader());
                        ClassSugar.newInstance(customLogHubFactory);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            } else if (definition.getFactoryMethodName() != null && definition.getSource() instanceof MethodMetadataReadingVisitor) {
                String superClassName = ((MethodMetadataReadingVisitor) definition.getSource()).getReturnTypeName();
                if (ClassSugar.isSuper(LogHubFactory.class, superClassName)) {
                    LogHubFactory logHubFactory = LogHubFactory.defaultLogHubFactory();

                }
            }
        }
    }

    public static final void initLogHubFactory(BeanDefinitionRegistry registry) {
        if (!LogHubFactory.isInitialized())
            LogHubUtils.initialSingletonLogFactory(registry);
    }

    public static final void initLogHubFactory(ConfigurableListableBeanFactory beanFactory) {
        if (!LogHubFactory.isInitialized())
            LogHubUtils.initialSingletonLogFactory(beanFactory);
    }


}
