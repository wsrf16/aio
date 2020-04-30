package com.aio.portable.swiss.autoconfigure;

import com.aio.portable.swiss.suite.log.classic.properties.PropertyBean;
import com.aio.portable.swiss.suite.log.factory.classic.ConsoleHubFactory;
import com.aio.portable.swiss.suite.log.factory.classic.KafkaHubFactory;
import com.aio.portable.swiss.suite.log.factory.classic.RabbitMQHubFactory;
import com.aio.portable.swiss.suite.log.factory.classic.Slf4jHubFactory;
import com.aio.portable.swiss.suite.log.classic.properties.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.classic.properties.RabbitMQLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

//@Configuration
public class LogHubAutoConfiguration {
    @Bean
    public ConsoleHubFactory consoleHubFactory() {
        return ConsoleHubFactory.singletonInstance();
    }

    @Bean
    public Slf4jHubFactory slf4jHubFactory() {
        return Slf4jHubFactory.singletonInstance();
    }

    @Bean
    @ConditionalOnProperty("spring.log.rabbitmq.host")
    @ConfigurationProperties(prefix = "spring.log.rabbitmq")
//    @ConditionalOnClass({RabbitTemplate.class, Channel.class})
    @ConditionalOnClass(name = {"org.springframework.amqp.rabbit.core.RabbitTemplate" , "com.rabbitmq.client.Channel"})
    public RabbitMQLogProperties rabbitMQLogProperties() {
        return RabbitMQLogProperties.singletonInstance();
    }

    @Bean
    @DependsOn(PropertyBean.RABBITMQ_PROPERTIES)
    @ConditionalOnBean(RabbitMQLogProperties.class)
//    @ConditionalOnClass({org.springframework.amqp.rabbit.connection.CachingConnectionFactory.class, org.springframework.amqp.rabbit.core.RabbitTemplate.class})
//    @ConditionalOnClass({RabbitTemplate.class, Channel.class})
    @ConditionalOnClass(name = {"org.springframework.amqp.rabbit.core.RabbitTemplate" , "com.rabbitmq.client.Channel"})
    public RabbitMQHubFactory rabbitMQHubFactory() {
        return RabbitMQHubFactory.singletonInstance();
    }

    @Bean
    @ConditionalOnProperty("spring.log.kafka.producer.bootstrap-servers")
    @ConfigurationProperties(prefix = "spring.log.kafka")
    @ConditionalOnClass(name = {"org.springframework.kafka.core.KafkaTemplate"})
    public KafkaLogProperties kafkaLogProperties() {
        return KafkaLogProperties.singletonInstance();
    }

    @Bean
    @DependsOn(PropertyBean.KAFKA_PROPERTIES)
    @ConditionalOnBean(KafkaLogProperties.class)
//    @ConditionalOnClass({org.springframework.kafka.core.KafkaTemplate.class})
    @ConditionalOnClass(name = {"org.springframework.kafka.core.KafkaTemplate"})
    public KafkaHubFactory kafkaHubFactory() {
        return KafkaHubFactory.singletonInstance();
    }
}
