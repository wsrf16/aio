package com.aio.portable.swiss.suite.log.impl.es.kafka;

import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.middleware.mq.kafka.KafkaBuilder;
import com.aio.portable.swiss.suite.log.facade.Printer;
import com.aio.portable.swiss.suite.log.support.LevelEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.kafka.core.KafkaTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class KafkaPrinter implements Printer {
    private final static Log log = LogFactory.getLog(KafkaPrinter.class);

    String logName;
    KafkaLogProperties properties;
    KafkaTemplate<String, String> kafkaTemplate;

    public KafkaPrinter(String logName, KafkaLogProperties properties) {
        this.logName = logName;
        this.properties = properties;
        this.kafkaTemplate = KafkaBuilder.buildTemplate(properties);
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
                KafkaPrinter printer = new KafkaPrinter(logName, properties);
                instanceMaps.put(section, printer);
                log.debug(MessageFormat.format("Initial Kafka Printer Host: {0}, Name: {1}", properties.getBootstrapServers(), logName));
                return printer;
            }
        }
    }

    @Override
    public void println(String line, LevelEnum level) {
        if (properties.getEnabled()) {
            properties.getBindingList().forEach(c -> {
                try {
                    kafkaTemplate.send(c.getTopic(), Thread.currentThread().getName(), line);
                } catch (Exception e) {
//                    e.printStackTrace();
                    log.error("kafka println error", e);
                }
            });
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
