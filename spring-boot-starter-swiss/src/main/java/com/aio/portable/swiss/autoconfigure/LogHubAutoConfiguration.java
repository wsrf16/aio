package com.aio.portable.swiss.autoconfigure;

import com.aio.portable.swiss.suite.log.impl.PropertyBean;
//import com.aio.portable.swiss.suite.log.factory.classic.ConsoleHubFactory;
//import com.aio.portable.swiss.suite.log.factory.classic.KafkaHubFactory;
//import com.aio.portable.swiss.suite.log.factory.classic.RabbitMQHubFactory;
//import com.aio.portable.swiss.suite.log.factory.classic.Slf4jHubFactory;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

//@Configuration
public class LogHubAutoConfiguration {
    @Bean
    @ConditionalOnProperty("spring.log.rabbitmq.host")
    @ConfigurationProperties(prefix = "spring.log.rabbitmq")
    @ConditionalOnClass(name = {"org.springframework.amqp.rabbit.core.RabbitTemplate" , "com.rabbitmq.client.Channel"})
    public RabbitMQLogProperties rabbitMQLogProperties() {
        return RabbitMQLogProperties.singletonInstance();
    }


    @Bean
    @ConditionalOnProperty("spring.log.kafka.bootstrap-servers")
    @ConfigurationProperties(prefix = "spring.log.kafka")
    @ConditionalOnClass(name = {"org.springframework.kafka.core.KafkaTemplate"})
    public KafkaLogProperties kafkaLogProperties() {
        return KafkaLogProperties.singletonInstance();
    }

}
