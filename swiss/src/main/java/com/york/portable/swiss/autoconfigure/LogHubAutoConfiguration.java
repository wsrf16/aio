package com.york.portable.swiss.autoconfigure;

import com.rabbitmq.client.Channel;
import com.york.portable.swiss.assist.log.classic.properties.LogKafkaProperties;
import com.york.portable.swiss.assist.log.classic.properties.LogRabbitMQProperties;
import com.york.portable.swiss.assist.log.hub.factory.classic.ConsoleHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.KafkaHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.RabbitMQHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.Slf4jHubFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class LogHubAutoConfiguration {
    @Bean
    public ConsoleHubFactory consoleHubFactory() {
        return ConsoleHubFactory.newInstance();
    }

    @Bean
    @ConditionalOnClass(org.slf4j.Logger.class)
    public Slf4jHubFactory slf4jHubFactory() {
        return Slf4jHubFactory.singletonInstance();
    }

    @Bean
    @ConditionalOnProperty("spring.log.rabbitmq.host")
    @ConfigurationProperties(prefix = "spring.log.rabbitmq")
//    @ConditionalOnClass({RabbitTemplate.class, Channel.class})
    public LogRabbitMQProperties logRabbitMQProperties() {
        return LogRabbitMQProperties.singletonInstance();
    }

    @Bean
    @DependsOn("logRabbitMQProperties")
    @ConditionalOnBean(LogRabbitMQProperties.class)
//    @ConditionalOnClass({org.springframework.amqp.rabbit.connection.CachingConnectionFactory.class, org.springframework.amqp.rabbit.core.RabbitTemplate.class})
//    @ConditionalOnClass({RabbitTemplate.class, Channel.class})
    @ConditionalOnClass(name = {"org.springframework.amqp.rabbit.core.RabbitTemplate" , "com.rabbitmq.client.Channel"})
    public RabbitMQHubFactory rabbitHubFactory() {
        return RabbitMQHubFactory.singletonInstance();
    }

    @Bean
    @ConditionalOnProperty("spring.log.kafka.producer.bootstrap-servers")
    @ConfigurationProperties(prefix = "spring.log.kafka")
    public LogKafkaProperties logKafkaProperties() {
        return LogKafkaProperties.singletonInstance();
    }

    @Bean
    @DependsOn("logKafkaProperties")
    @ConditionalOnBean(LogKafkaProperties.class)
//    @ConditionalOnClass({org.springframework.kafka.core.KafkaTemplate.class})
    @ConditionalOnClass(name = {"org.springframework.kafka.core.KafkaTemplate"})
    public KafkaHubFactory kafkaHubFactory() {
        return KafkaHubFactory.singletonInstance();
    }
}
