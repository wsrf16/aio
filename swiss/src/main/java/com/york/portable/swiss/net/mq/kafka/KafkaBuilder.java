package com.york.portable.swiss.net.mq.kafka;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;

import java.util.Map;

public class KafkaBuilder {
    public static ProducerFactory<?, ?> kafkaProducerFactory(KafkaProperties properties) {
        Map<String, Object> producerProperties = properties.buildProducerProperties();
        DefaultKafkaProducerFactory<?, ?> factory = new DefaultKafkaProducerFactory(producerProperties);
        return factory;
    }

    public static ProducerFactory<?, ?> kafkaProducerFactory(KafkaProperties properties, Map<String, Object> extraProperties, String transactionIdPrefix) {
        Map<String, Object> producerProperties = properties.buildProducerProperties();
        producerProperties.putAll(extraProperties);

        DefaultKafkaProducerFactory<?, ?> factory = new DefaultKafkaProducerFactory(producerProperties);
        try {
            if (transactionIdPrefix != null) {
                factory.setTransactionIdPrefix(transactionIdPrefix);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return factory;
    }

    public static ConsumerFactory<String, Object> kafkaConsumerFactory(KafkaProperties properties) {
        return new DefaultKafkaConsumerFactory(properties.buildConsumerProperties());
    }

    public static ConsumerFactory<String, Object> kafkaConsumerFactory(KafkaProperties properties, Map<String, Object> extraProperties) {
        Map<String, Object> consumerProperties = properties.buildConsumerProperties();
        consumerProperties.putAll(extraProperties);

        return new DefaultKafkaConsumerFactory(properties.buildConsumerProperties());
    }

    private static ProducerListener<Object, Object> kafkaProducerListener() {
        return new LoggingProducerListener();
    }

    public static KafkaTemplate<?, ?> kafkaTemplate(KafkaProperties properties) {
        return KafkaBuilder.kafkaTemplate(properties, null);
    }

    public static KafkaTemplate<?, ?> kafkaTemplate(KafkaProperties properties, RecordMessageConverter messageConverter) {
        ProducerFactory<Object, Object> kafkaProducerFactory = (ProducerFactory<Object, Object>)KafkaBuilder.kafkaProducerFactory(properties);
        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate(kafkaProducerFactory);

        if (messageConverter != null) {
            kafkaTemplate.setMessageConverter(messageConverter);
        }

        ProducerListener<Object, Object> kafkaProducerListener = KafkaBuilder.kafkaProducerListener();
        if (kafkaProducerListener != null) {
            kafkaTemplate.setProducerListener(kafkaProducerListener);
        }

        if (properties != null && properties.getTemplate() != null && StringUtils.isNotBlank(properties.getTemplate().getDefaultTopic())) {
            String defaultTopic = properties.getTemplate().getDefaultTopic();
            kafkaTemplate.setDefaultTopic(defaultTopic);
        }
        return kafkaTemplate;
    }













//    protected final KafkaProperties properties;
//    protected final RecordMessageConverter messageConverter;
//
//    public KafkaBuilder(KafkaProperties properties) {
//        this.properties = properties;
//        this.messageConverter = null;
//    }
//
//    public KafkaBuilder(KafkaProperties properties, RecordMessageConverter messageConverter) {
//        this.properties = properties;
//        this.messageConverter = messageConverter;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean({KafkaTemplate.class})
//    public KafkaTemplate<?, ?> kafkaTemplate(ProducerFactory<Object, Object> kafkaProducerFactory, ProducerListener<Object, Object> kafkaProducerListener) {
//        KafkaTemplate<Object, Object> kafkaTemplate = new KafkaTemplate(kafkaProducerFactory);
//        if (this.messageConverter != null) {
//            kafkaTemplate.setMessageConverter(this.messageConverter);
//        }
//
//        kafkaTemplate.setProducerListener(kafkaProducerListener);
//        kafkaTemplate.setDefaultTopic(this.properties.getTemplate().getDefaultTopic());
//        return kafkaTemplate;
//    }

//    @Bean
//    @ConditionalOnMissingBean({ProducerListener.class})
//    public ProducerListener<Object, Object> kafkaProducerListener() {
//        return new LoggingProducerListener();
//    }

//    @Bean
//    @ConditionalOnMissingBean({ConsumerFactory.class})
//    public ConsumerFactory<?, ?> kafkaConsumerFactory() {
//        return new DefaultKafkaConsumerFactory(this.properties.buildConsumerProperties());
//    }
//
//    @Bean
//    @ConditionalOnMissingBean({ProducerFactory.class})
//    public ProducerFactory<?, ?> kafkaProducerFactory() {
//        DefaultKafkaProducerFactory<?, ?> factory = new DefaultKafkaProducerFactory(this.properties.buildProducerProperties());
//        String transactionIdPrefix = this.properties.getProducer().getTransactionIdPrefix();
//        if (transactionIdPrefix != null) {
//            factory.setTransactionIdPrefix(transactionIdPrefix);
//        }
//
//        return factory;
//    }

//    @Bean
//    @ConditionalOnProperty(
//            name = {"spring.kafka.producer.transaction-id-prefix"}
//    )
//    @ConditionalOnMissingBean
//    public KafkaTransactionManager<?, ?> kafkaTransactionManager(ProducerFactory<?, ?> producerFactory) {
//        return new KafkaTransactionManager(producerFactory);
//    }
//
//    @Bean
//    @ConditionalOnProperty(
//            name = {"spring.kafka.jaas.enabled"}
//    )
//    @ConditionalOnMissingBean
//    public KafkaJaasLoginModuleInitializer kafkaJaasInitializer() throws IOException {
//        KafkaJaasLoginModuleInitializer jaas = new KafkaJaasLoginModuleInitializer();
//        KafkaProperties.Jaas jaasProperties = this.properties.getJaas();
//        if (jaasProperties.getControlFlag() != null) {
//            jaas.setControlFlag(jaasProperties.getControlFlag());
//        }
//
//        if (jaasProperties.getLoginModule() != null) {
//            jaas.setLoginModule(jaasProperties.getLoginModule());
//        }
//
//        jaas.setOptions(jaasProperties.getOptions());
//        return jaas;
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public KafkaAdmin kafkaAdmin() {
//        KafkaAdmin kafkaAdmin = new KafkaAdmin(this.properties.buildAdminProperties());
//        kafkaAdmin.setFatalIfBrokerNotAvailable(this.properties.getAdmin().isFailFast());
//        return kafkaAdmin;
//    }
}
