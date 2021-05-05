package com.aio.portable.swiss.suite.log.impl.es.rabbit;

import com.aio.portable.swiss.middleware.mq.rabbitmq.RabbitBuilder;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class RabbitMQPrinter implements Printer {
    String logName;
    RabbitMQLogProperties rabbitMQLogProperties;
    RabbitTemplate rabbitTemplate;

    private RabbitMQPrinter(String logName, RabbitMQLogProperties rabbitMQLogProperties) {
        this.logName = logName;
        this.rabbitMQLogProperties = rabbitMQLogProperties;
        this.rabbitTemplate = RabbitBuilder.buildTemplate(rabbitMQLogProperties);
    }

    private static Map<String, RabbitMQPrinter> instanceMaps = new HashMap<>();


    /**
     * 多单例
     *
     * @param logName
     */
    public static synchronized RabbitMQPrinter instance(String logName, RabbitMQLogProperties properties) {
        String section = String.join(Constant.EMPTY, logName);
        {
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                RabbitMQPrinter _loc = new RabbitMQPrinter(logName, properties);
                instanceMaps.put(section, _loc);
                System.out.println(MessageFormat.format("Initial RabbitMQ Printer Host: {0}, Name: {1}", properties.getHost(), logName));
                return _loc;
            }
        }
    }

    @Override
    public void println(String line, LevelEnum level) {
        if (rabbitMQLogProperties.isEnabled()) {
            rabbitMQLogProperties.getBindingList().forEach(c -> {
                String exchange = c.getExchange();
                String routingKey = c.getRoutingKey();
//                String message = MessageBuilder
//                        .withBody(orderJson.getBytes())
//                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
//                        .build();

                try {
                    rabbitTemplate.convertAndSend(exchange, routingKey, line);
                } catch (AmqpException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
