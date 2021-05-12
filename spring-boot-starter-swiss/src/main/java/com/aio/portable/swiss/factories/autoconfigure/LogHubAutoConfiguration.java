package com.aio.portable.swiss.factories.autoconfigure;

import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
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

//    @Bean
//    @ConfigurationProperties(prefix = LogHubProperties.PREFIX)
//    public LogHubProperties logHubProperties() {
//        return new LogHubProperties();
//    }
}
