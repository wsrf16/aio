package com.aio.portable.swiss.suite.log.solution.elk.rabbit;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.middleware.mq.rabbitmq.RabbitBuilder;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RabbitMQPrinter implements Printer {
    private static final Log log = LogFactory.getLog(RabbitMQPrinter.class);

    String logName;
    RabbitMQLogProperties rabbitMQLogProperties;
    private volatile static RabbitTemplate rabbitTemplate;

    private RabbitMQPrinter(String logName, RabbitMQLogProperties rabbitMQLogProperties) {
        this.logName = logName;
        this.rabbitMQLogProperties = rabbitMQLogProperties;
        if (rabbitTemplate == null) {
            synchronized (RabbitMQPrinter.class) {
                if (rabbitTemplate == null) {
                    rabbitTemplate = RabbitBuilder.buildTemplate(rabbitMQLogProperties);
                }
            }
        }
    }

    private static Map<String, RabbitMQPrinter> instanceMaps = new ConcurrentHashMap<>();


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
                RabbitMQPrinter printer = new RabbitMQPrinter(logName, properties);
                instanceMaps.put(section, printer);
                log.debug(MessageFormat.format("Initial RabbitMQ Printer Host: {0}, Name: {1}", properties.getHost(), logName));
                return printer;
            }
        }
    }

    @Override
    public void println(String line, LevelEnum level) {
        if (rabbitMQLogProperties.getEnabled()) {
            rabbitMQLogProperties.getBindingList().forEach(c -> {
                Message msg = MessageBuilder
                        .withBody(line.getBytes())
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .setMessageId(IDS.uuid())
                        .build();

//                String msg = line;
                try {
                    rabbitTemplate.convertAndSend(c.getExchange(), c.getRoutingKey(), msg);
                } catch (AmqpException e) {
                    log.error("rabbitmq println error", e);
                }
            });
        }
    }

}
