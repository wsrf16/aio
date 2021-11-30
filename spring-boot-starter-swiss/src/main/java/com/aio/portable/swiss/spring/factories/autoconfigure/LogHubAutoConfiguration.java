package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

//@Configuration
public class LogHubAutoConfiguration {
    @Bean
    @ConditionalOnProperty("spring.log.rabbitmq.host")
    @ConfigurationProperties(prefix = RabbitMQLogProperties.PREFIX)
    @ConditionalOnClass(name = {"org.springframework.amqp.rabbit.core.RabbitTemplate", "com.rabbitmq.client.Channel"})
    public RabbitMQLogProperties rabbitMQLogProperties() {
        return RabbitMQLogProperties.singletonInstance();
    }


    @Bean
    @ConditionalOnProperty("spring.log.kafka.bootstrap-servers")
    @ConfigurationProperties(prefix = KafkaLogProperties.PREFIX)
    @ConditionalOnClass(name = {"org.springframework.kafka.core.KafkaTemplate"})
    public KafkaLogProperties kafkaLogProperties() {
        return KafkaLogProperties.singletonInstance();
    }


}
