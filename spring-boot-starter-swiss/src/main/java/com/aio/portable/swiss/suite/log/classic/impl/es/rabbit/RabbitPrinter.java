package com.aio.portable.swiss.suite.log.classic.impl.es.rabbit;

import com.aio.portable.swiss.module.mq.rabbitmq.RabbitMQSugar;
import com.aio.portable.swiss.suite.document.method.PropertiesMapping;
import com.aio.portable.swiss.suite.log.Printer;
import com.aio.portable.swiss.suite.log.classic.impl.LoggerConfig;
import com.aio.portable.swiss.suite.log.classic.properties.RabbitMQLogProperties;
import com.aio.portable.swiss.global.Constant;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.HashMap;
import java.util.Map;

public class RabbitPrinter implements Printer {
    public static String SECTION_SEPARATOR = PropertiesMapping.instance().getString("SECTION_SEPARATOR", LoggerConfig.SECTION_SEPARATOR);
    public static String LINE_SEPARATOR = PropertiesMapping.instance().getString("LINE_SEPARATOR", LoggerConfig.LINE_SEPARATOR);
    public static String TIME_FORMAT = PropertiesMapping.instance().getString("TIME_FORMAT", LoggerConfig.TIME_FORMAT);
    public static int EMPTYLINES = PropertiesMapping.instance().getInt("EMPTY_LINES", LoggerConfig.EMPTY_LINES);

    String logName;
    String logfilePrefix;
    RabbitMQLogProperties configuration;
    RabbitTemplate rabbitTemplate;

    private RabbitPrinter(String logName, String logfilePrefix, RabbitMQLogProperties configuration) {
        this.logName = logName;
        this.logfilePrefix = logfilePrefix;
        this.configuration = configuration;
        this.rabbitTemplate = RabbitMQSugar.buildRabbitTemplate(configuration);
    }

    private static Map<String, RabbitPrinter> instanceMaps = new HashMap<>();


    /**
     * 多单例
     *
     * @param logName
     * @param logFilePrefix
     * @return 返回日志格式：[ROOT_LOGFOLDER]\[logName]\[logFilePrefix][SEPARATOR_CHAR][OCCUPY_MAX][SEPARATOR_CHAR][TIMEFORMAT][LOG_EXTENSION]
     */
    public static synchronized RabbitPrinter instance(String logName, String logFilePrefix, RabbitMQLogProperties configuration) {
        String section = String.join(Constant.EMPTY, logName, SECTION_SEPARATOR, logFilePrefix);
        {
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                RabbitPrinter _loc = new RabbitPrinter(logName, logFilePrefix, configuration);
                instanceMaps.put(section, _loc);
                return _loc;
            }
        }
    }

    @Override
    public void println(String line) {
        if (configuration.isEnable()) {
            configuration.getBindingList().forEach(c -> {
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
