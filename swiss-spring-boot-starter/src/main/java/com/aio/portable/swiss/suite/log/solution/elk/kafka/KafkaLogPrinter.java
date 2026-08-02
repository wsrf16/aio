package com.aio.portable.swiss.suite.log.solution.elk.kafka;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.middleware.mq.kafka.KafkaBuilder;
import com.aio.portable.swiss.suite.log.facade.LogPrinter;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.ObjectUtils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KafkaLogPrinter implements LogPrinter {
//    private static final Log log = LogFactory.getLog(KafkaPrinter.class);
    private static final LocalLog log = LocalLog.getLog(KafkaLogPrinter.class);

    String logName;
    KafkaLogProperties properties;
    KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaLogPrinter(String logName, KafkaLogProperties properties) {
        this.logName = logName;
        this.properties = properties;
//        Map<String, String> configs = this.properties.getProducer().getProperties();
//        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);
//        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, org.apache.kafka.common.serialization.StringSerializer.class);

        this.properties.getProducer().setKeySerializer(org.apache.kafka.common.serialization.StringSerializer.class);
        this.properties.getProducer().setValueSerializer(org.springframework.kafka.support.serializer.JsonSerializer.class);

        this.kafkaTemplate = KafkaBuilder.buildTemplate(this.properties);
    }

    private static final Map<String, KafkaLogPrinter> instanceMaps = new ConcurrentHashMap<>();


    /**
     * 多单例
     *
     * @param logName
     */
    public static synchronized KafkaLogPrinter instance(String logName, KafkaLogProperties properties) {
        String section = String.join(Constant.EMPTY, logName);
        if (instanceMaps.containsKey(section)) {
            return instanceMaps.get(section);
        } else {
            KafkaLogPrinter printer = new KafkaLogPrinter(logName, properties);
            instanceMaps.put(section, printer);
            log.debug(MessageFormat.format("Initial Kafka Printer Host: {0}, Name: {1}", properties.getBootstrapServers(), logName));
            return printer;
        }
    }

    @Override
    public void println(Object record, LevelEnum level) {
        if (properties.getEnabledOrDefault()) {
            if (properties.getTemplate() != null && !ObjectUtils.isEmpty(properties.getTemplate().getDefaultTopic())) {
                send(properties.getTemplate().getDefaultTopic(), record);
            }

            if (properties.getBindingList() != null) {
                properties.getBindingList().forEach(c -> {
                    send(c.getTopic(), record);
                });
            }
        }
    }


    private void send(String topic, Object record) {
        try {
            kafkaTemplate.send(topic, Thread.currentThread().getName(), record);
        } catch (Exception e) {
//                    e.printStackTrace();
            log.warn("kafka println error", e);
        }
    }

}
