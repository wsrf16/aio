package com.aio.portable.swiss.suite.log.classic.impl.es.kafka;

import com.aio.portable.swiss.suite.document.method.PropertiesMapping;
import com.aio.portable.swiss.suite.log.Printer;
import com.aio.portable.swiss.suite.log.classic.impl.LoggerConfig;
import com.aio.portable.swiss.suite.log.classic.properties.LogKafkaProperties;
import com.aio.portable.swiss.global.Constant;
import com.aio.portable.swiss.module.mq.kafka.KafkaBuilder;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

public class KafkaPrinter implements Printer {
    public final static String SECTION_SEPARATOR = PropertiesMapping.instance().getString("SECTION_SEPARATOR", LoggerConfig.SECTION_SEPARATOR);
    public final static String LINE_SEPARATOR = PropertiesMapping.instance().getString("LINE_SEPARATOR", LoggerConfig.LINE_SEPARATOR);
    public final static String TIME_FORMAT = PropertiesMapping.instance().getString("TIME_FORMAT", LoggerConfig.TIME_FORMAT);
    public final static int EMPTYLINES = PropertiesMapping.instance().getInt("EMPTY_LINES", LoggerConfig.EMPTY_LINES);

    String logName;
    String logfilePrefix;
    LogKafkaProperties properties;
    KafkaTemplate<String, String> kafkaTemplate;

    public KafkaPrinter(String logName, String logfilePrefix, LogKafkaProperties properties) {
        this.logName = logName;
        this.logfilePrefix = logfilePrefix;
        this.properties = properties;
        this.kafkaTemplate = KafkaBuilder.kafkaTemplate(properties);
    }

    private static Map<String, KafkaPrinter> instanceMaps = new HashMap<>();


    /**
     * 多单例
     *
     * @param logName
     * @param logFilePrefix
     * @return 返回日志格式：[ROOT_LOGFOLDER]\[logName]\[logFilePrefix][SEPARATOR_CHAR][OCCUPY_MAX][SEPARATOR_CHAR][TIMEFORMAT][LOG_EXTENSION]
     */
    public final static synchronized KafkaPrinter instance(String logName, String logFilePrefix, LogKafkaProperties properties) {
        String section = String.join(Constant.EMPTY, logName, SECTION_SEPARATOR, logFilePrefix);
        {
            if (instanceMaps.keySet().contains(section))
                return instanceMaps.get(section);
            else {
                KafkaPrinter _loc = new KafkaPrinter(logName, logFilePrefix, properties);
                instanceMaps.put(section, _loc);
                return _loc;
            }
        }
    }

    @Override
    public void println(String line) {
        if (properties.isEnable()) {
            kafkaTemplate.send(properties.getTopic(), Thread.currentThread().getName(), line);
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
