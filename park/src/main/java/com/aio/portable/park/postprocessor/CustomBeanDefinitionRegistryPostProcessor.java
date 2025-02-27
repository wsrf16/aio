package com.aio.portable.park.postprocessor;

import com.aio.portable.park.bean.UserInfoEntity;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.sugar.meta.ClassLoaderSugar;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;

import java.lang.reflect.InvocationTargetException;

/**
 * beanDefinition aspect
 */
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
        BeanDefinition rootBeanDefinition = builder.getBeanDefinition();
        if (!registry.containsBeanDefinition("userInfoEntity"))
            registry.registerBeanDefinition("userInfoEntity", rootBeanDefinition);
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
            if (definition.getBeanClassName() == null){}
            else if (definition.getBeanClassName().contains("Hub")) {
                if ((definition instanceof ScannedGenericBeanDefinition && ((ScannedGenericBeanDefinition) definition).getMetadata().getSuperClassName().equals(LogHubFactory.class.getTypeName())))
                {
                    try {
                        Class<?> clazz = ((ScannedGenericBeanDefinition) (definition)).resolveBeanClass(ClassLoaderSugar.getDefaultClassLoader());
                        clazz.getConstructor().newInstance();
                    } catch (InstantiationException|InvocationTargetException|NoSuchMethodException|IllegalAccessException|ClassNotFoundException e) {
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
    }
}

