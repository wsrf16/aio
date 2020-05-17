package com.aio.portable.swiss.suite.log.annotation;

import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.log.impl.es.rabbit.RabbitMQLogProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackageClasses = {RabbitMQLogProperties.class, KafkaLogProperties.class})
@Import({RabbitMQLogProperties.class, KafkaLogProperties.class})
public class LogProperties {
    @Autowired(required = false)
    RabbitMQLogProperties rabbitMQLogProperties;

    @Autowired(required = false)
    KafkaLogProperties kafkaLogProperties;

}
