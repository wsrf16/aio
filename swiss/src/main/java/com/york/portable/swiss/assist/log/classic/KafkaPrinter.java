package com.york.portable.swiss.assist.log.classic;

import com.york.portable.swiss.assist.document.method.PropertiesMapping;
import com.york.portable.swiss.assist.log.base.IPrinter;
import com.york.portable.swiss.assist.log.classic.parts.LogKafkaProperties;
import com.york.portable.swiss.assist.log.classic.parts.LoggerConfig;
import com.york.portable.swiss.global.Constant;
import com.york.portable.swiss.net.mq.kafka.KafkaBuilder;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;

public class KafkaPrinter implements IPrinter {
    public static String SECTION_SEPARATOR = PropertiesMapping.instance().getString("SECTION_SEPARATOR", LoggerConfig.SECTION_SEPARATOR);
    public static String LINE_SEPARATOR = PropertiesMapping.instance().getString("LINE_SEPARATOR", LoggerConfig.LINE_SEPARATOR);
    public static String TIME_FORMAT = PropertiesMapping.instance().getString("TIME_FORMAT", LoggerConfig.TIME_FORMAT);
    public static int EMPTYLINES = PropertiesMapping.instance().getInt("EMPTY_LINES", LoggerConfig.EMPTY_LINES);

    String logName;
    String logfilePrefix;
    LogKafkaProperties properties;
    KafkaTemplate<Object, Object> kafkaTemplate;

    public KafkaPrinter(String logName, String logfilePrefix, LogKafkaProperties properties) {
        this.logName = logName;
        this.logfilePrefix = logfilePrefix;
        this.properties = properties;
        this.kafkaTemplate = (KafkaTemplate<Object, Object>) KafkaBuilder.kafkaTemplate(properties);
    }

    private static Map<String, KafkaPrinter> instanceMaps = new HashMap<>();


    /**
     * 多单例
     *
     * @param logName
     * @param logFilePrefix
     * @return 返回日志格式：[ROOT_LOGFOLDER]\[logName]\[logFilePrefix][SEPARATOR_CHAR][OCCUPY_MAX][SEPARATOR_CHAR][TIMEFORMAT][LOG_EXTENSION]
     */
    public static synchronized KafkaPrinter instance(String logName, String logFilePrefix, LogKafkaProperties properties) {
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
