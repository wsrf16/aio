package com.aio.portable.park.config.mq;

import com.aio.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQBindingProperty;
import com.aio.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQCachingConnectionFactoryProperties;
import com.aio.portable.swiss.middleware.mq.rabbitmq.RabbitMQUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {
    @ConditionalOnProperty("spring.rabbitmq.main.host")
    @ConfigurationProperties(prefix = "spring.rabbitmq.main")
    @Bean("main.bindingList")
    public RabbitMQCachingConnectionFactoryProperties buildConnectionFactory() {
        RabbitMQCachingConnectionFactoryProperties rabbitMQCachingConnectionFactoryProperties = new RabbitMQCachingConnectionFactoryProperties();
        return rabbitMQCachingConnectionFactoryProperties;
    }

    @ConditionalOnBean(RabbitMQCachingConnectionFactoryProperties.class)
    @Qualifier(value = "main.bindingList")
    @Autowired
    public void bindingMain(RabbitMQCachingConnectionFactoryProperties rabbitMQCachingConnectionFactoryProperties) {
        List<RabbitMQBindingProperty> rabbitMQBindingPropertyList = rabbitMQCachingConnectionFactoryProperties.getBindingList();
        RabbitMQUtil.binding(rabbitMQBindingPropertyList);
    }
}
