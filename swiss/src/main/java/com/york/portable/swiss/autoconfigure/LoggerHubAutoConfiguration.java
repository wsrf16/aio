package com.york.portable.swiss.autoconfigure;

import com.york.portable.swiss.assist.log.classic.parts.LogKafkaProperties;
import com.york.portable.swiss.assist.log.classic.parts.LogRabbitMQProperties;
import com.york.portable.swiss.assist.log.hub.factory.classic.ConsoleHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.KafkaHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.RabbitHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.classic.Slf4jHubFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class LoggerHubAutoConfiguration {
    @Bean
    public ConsoleHubFactory consoleHubFactory() {
        return ConsoleHubFactory.newInstance();
    }

    @Bean
    @ConditionalOnClass(org.slf4j.Logger.class)
    public Slf4jHubFactory slf4jHubFactory() {
        return Slf4jHubFactory.newInstance();
    }

    @Bean
    @ConditionalOnProperty("spring.log.rabbitmq.host")
    @ConfigurationProperties(prefix = "spring.log.rabbitmq")
    public LogRabbitMQProperties logRabbitMQProperties() {
        return LogRabbitMQProperties.newInstance();
    }

    @Bean
    @DependsOn("logRabbitMQProperties")
    @ConditionalOnBean(LogRabbitMQProperties.class)
    @ConditionalOnClass({org.springframework.amqp.rabbit.connection.CachingConnectionFactory.class, org.springframework.amqp.rabbit.core.RabbitTemplate.class})
    public RabbitHubFactory rabbitHubFactory() {
        return RabbitHubFactory.newInstance();
    }

    @Bean
    @ConditionalOnProperty("spring.log.kafka.producer.bootstrap-servers")
    @ConfigurationProperties(prefix = "spring.log.kafka")
    public LogKafkaProperties logKafkaProperties() {
        return LogKafkaProperties.newInstance();
    }

    @Bean
    @DependsOn("logKafkaProperties")
    @ConditionalOnBean(LogKafkaProperties.class)
    @ConditionalOnClass({org.springframework.kafka.core.KafkaTemplate.class})
    public KafkaHubFactory kafkaHubFactory() {
        return KafkaHubFactory.newInstance();
    }
}
