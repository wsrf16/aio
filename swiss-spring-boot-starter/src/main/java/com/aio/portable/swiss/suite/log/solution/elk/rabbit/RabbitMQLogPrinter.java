package com.aio.portable.swiss.suite.log.solution.elk.rabbit;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.middleware.mq.rabbitmq.RabbitBuilder;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.log.facade.LogPrinter;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RabbitMQLogPrinter implements LogPrinter {
//    private static final Log log = LogFactory.getLog(RabbitMQPrinter.class);
    private static final LocalLog log = LocalLog.getLog(RabbitMQLogPrinter.class);

    String logName;
    RabbitMQLogProperties rabbitMQLogProperties;
    private volatile static RabbitTemplate rabbitTemplate;

    private RabbitMQLogPrinter(String logName, RabbitMQLogProperties rabbitMQLogProperties) {
        this.logName = logName;
        this.rabbitMQLogProperties = rabbitMQLogProperties;
        if (rabbitTemplate == null) {
            synchronized (RabbitMQLogPrinter.class) {
                if (rabbitTemplate == null) {
                    rabbitTemplate = RabbitBuilder.buildTemplate(rabbitMQLogProperties);
                }
            }
        }
    }

    private static Map<String, RabbitMQLogPrinter> instanceMaps = new ConcurrentHashMap<>();


    /**
     * 多单例
     *
     * @param logName
     */
    public static synchronized RabbitMQLogPrinter instance(String logName, RabbitMQLogProperties properties) {
        String section = String.join(Constant.EMPTY, logName);
        {
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                RabbitMQLogPrinter printer = new RabbitMQLogPrinter(logName, properties);
                instanceMaps.put(section, printer);
                log.debug(MessageFormat.format("Initial RabbitMQ Printer Host: {0}, Name: {1}", properties.getHost(), logName));
                return printer;
            }
        }
    }

    @Override
    public void println(Object record, LevelEnum level) {
        String line = getSmartSerializerAdapter(level).serialize(record);
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
                    log.warn("rabbitmq println error", e);
                }
            });
        }
    }

}
