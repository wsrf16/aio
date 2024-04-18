package com.aio.portable.swiss.spring.factories.autoconfigure;

import com.aio.portable.swiss.suite.log.solution.console.ConsoleLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.solution.elk.rabbit.RabbitMQLogProperties;
import com.aio.portable.swiss.suite.log.solution.slf4j.Slf4JLogProperties;
import com.aio.portable.swiss.suite.log.support.LogHubProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

public class LogHubAutoConfiguration {
    @Bean
//    @ConditionalOnProperty("spring.log.kafka.bootstrap-servers")
    @ConfigurationProperties(prefix = LogHubProperties.PREFIX)
//    @ConditionalOnClass(name = {"org.springframework.kafka.core.KafkaTemplate"})
    public LogHubProperties logHubProperties() {
        return LogHubProperties.getSingleton();
    }

    @Bean
    @ConditionalOnProperty("spring.log.rabbitmq.host")
    @ConfigurationProperties(prefix = RabbitMQLogProperties.PREFIX)
    @ConditionalOnClass(name = {"org.springframework.amqp.rabbit.core.RabbitTemplate", "com.rabbitmq.client.Channel"})
    public RabbitMQLogProperties rabbitMQLogProperties() {
        return RabbitMQLogProperties.getSingleton();
    }


    @Bean
    @ConditionalOnProperty("spring.log.kafka.bootstrap-servers")
    @ConfigurationProperties(prefix = KafkaLogProperties.PREFIX)
    @ConditionalOnClass(name = {"org.springframework.kafka.core.KafkaTemplate"})
    public KafkaLogProperties kafkaLogProperties() {
        return KafkaLogProperties.getSingleton();
    }

    @Bean
//    @ConditionalOnProperty("spring.log.console.host")
    @ConfigurationProperties(prefix = ConsoleLogProperties.PREFIX)
    @ConditionalOnClass(name = {"com.aio.portable.swiss.suite.log.solution.console.ConsoleLog"})
    public ConsoleLogProperties consoleLogProperties() {
        return ConsoleLogProperties.getSingleton();
    }

    @Bean
//    @ConditionalOnProperty("spring.log.kafka.bootstrap-servers")
    @ConfigurationProperties(prefix = Slf4JLogProperties.PREFIX)
    @ConditionalOnClass(name = {"org.slf4j.LoggerFactory"})
    public Slf4JLogProperties slf4JLogProperties() {
        return Slf4JLogProperties.getSingleton();
    }

}
