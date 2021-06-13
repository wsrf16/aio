package com.aio.portable.park.postprocessor;

import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;

@Configuration
public class CustomBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    /**
     * 注册自定义bean
     * @param registry
     * @throws BeansException
     */
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String[] names = registry.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];

            BeanDefinition bd = registry.getBeanDefinition(name);
//            System.out.println(name + " bean properties: " + bd.getPropertyValues().toString());
        }

        // 创建BeanDefinitionBuilder
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(UserInfoEntity.class);
        // 设置属性值
        builder.addPropertyValue("name","list_test");
        // 设置可通过@Autowire注解引用
        builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
        // 注册到BeanDefinitionRegistry
//        registry.registerBeanDefinition("userInfoEntity", builder.getBeanDefinition());
    }


    /**
     * 主要是用来自定义修改持有的bean
     * ConfigurableListableBeanFactory 其实就是DefaultListableBeanDefinition对象
     * @param beanFactory
     * @throws BeansException
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] names = beanFactory.getBeanDefinitionNames();
        for (int i = 0; i < names.length; i++) {
            String name = names[i];

            BeanDefinition definition = beanFactory.getBeanDefinition(name);
            if (definition.getBeanClassName() == null)
                System.out.println();
            else if (definition.getBeanClassName().contains("Hub")) {
                if ((definition instanceof ScannedGenericBeanDefinition && ((ScannedGenericBeanDefinition) definition).getMetadata().getSuperClassName().equals(LogHubFactory.class.getTypeName())))
                {
                    try {
                        final Class<?> clazz = ((ScannedGenericBeanDefinition) (definition)).resolveBeanClass(Thread.currentThread().getContextClassLoader());
                        clazz.newInstance();
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

//            String shortClassName = ClassUtils.getShortName(definition.getBeanClassName());
//            String beanName = Introspector.decapitalize(shortClassName);

//            System.out.println(name + " bean properties: " + definition.getPropertyValues().toString());
        }
//        Stream.of(names).filter(c -> c.equals("rabbitMQLogProperties")).findFirst().get()
//        Object rabbitMQLogProperties = beanFactory.getBean("rabbitMQLogProperties");
//        beanFactory.destroyBean(rabbitMQLogProperties);
        System.out.println();
    }
}

