package com.aio.portable.swiss.suite.log.impl.es.kafka;

import com.aio.portable.swiss.suite.document.method.PropertiesMapping;
import com.aio.portable.swiss.suite.log.Printer;
import com.aio.portable.swiss.suite.log.impl.LoggerConfig;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.module.mq.kafka.KafkaBuilder;
import com.aio.portable.swiss.suite.log.parts.LevelEnum;
import org.springframework.kafka.core.KafkaTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class KafkaPrinter implements Printer {
    String logName;
    KafkaLogProperties kafkaLogProperties;
    KafkaTemplate<String, String> kafkaTemplate;

    public KafkaPrinter(String logName, KafkaLogProperties kafkaLogProperties) {
        this.logName = logName;
        this.kafkaLogProperties = kafkaLogProperties;
        this.kafkaTemplate = KafkaBuilder.kafkaTemplate(kafkaLogProperties);
    }

    private static Map<String, KafkaPrinter> instanceMaps = new HashMap<>();


    /**
     * 多单例
     *
     * @param logName
     */
    public final static synchronized KafkaPrinter instance(String logName, KafkaLogProperties properties) {
        String section = String.join(Constant.EMPTY, logName);
        {
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                KafkaPrinter _loc = new KafkaPrinter(logName, properties);
                instanceMaps.put(section, _loc);
                System.out.println(MessageFormat.format("Initial Kafka Printer Host: {0}, Name: {1}", properties.getBootstrapServers(), logName));
                return _loc;
            }
        }
    }

    @Override
    public void println(String line, LevelEnum level) {
        if (kafkaLogProperties.isEnable()) {
            kafkaTemplate.send(kafkaLogProperties.getTopic(), Thread.currentThread().getName(), line);
        }
    }

//    private void kafkaTemplate() {
//        KafkaAutoConfiguration configuration = new KafkaAutoConfiguration(properties, new ObjectProvider<RecordMessageConverter>() {
//            @Override
//            public RecordMessageConverter getObject(Object... objects) throws BeansException {
//                return null;
//            }
//
//            @Override
//            public RecordMessageConverter getIfAvailable() throws BeansException {
//                return null;
//            }
//
//            @Override
//            public RecordMessageConverter getIfUnique() throws BeansException {
//                return null;
//            }
//
//            @Override
//            public RecordMessageConverter getObject() throws BeansException {
//                return null;
//            }
//        });
//
//
////        Map<String, Object> map = this.properties.buildProducerProperties();
////        Map<String, Object> producerProperties = new HashMap<>();
////        producerProperties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
////        producerProperties.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 1000);
////        producerProperties.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 1000);
////        producerProperties.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 1000);
//        ProducerFactory<?, ?> factory = (ProducerFactory<Object, Object>) configuration.kafkaProducerFactory();
//        ProducerListener<Object, Object> listener = configuration.kafkaProducerListener();
//    }


}
