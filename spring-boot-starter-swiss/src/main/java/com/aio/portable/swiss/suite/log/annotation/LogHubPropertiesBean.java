package com.aio.portable.swiss.suite.log.annotation;

import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogHubPropertiesBean {
    @Autowired(required = false)
    private RabbitMQLogProperties rabbitMQLogProperties;

    @Autowired(required = false)
    private KafkaLogProperties kafkaLogProperties;
}
