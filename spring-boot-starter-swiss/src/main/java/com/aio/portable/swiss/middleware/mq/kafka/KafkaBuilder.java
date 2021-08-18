package com.aio.portable.swiss.middleware.mq.kafka;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.util.StringUtils;

import java.util.Map;

public class KafkaBuilder {
    public final static <K, V> DefaultKafkaProducerFactory<K, V> buildProducerFactory(KafkaProperties properties) {
        Map<String, Object> producerProperties = properties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(producerProperties);
    }

    public final static <K, V> DefaultKafkaProducerFactory<K, V> buildProducerFactory(KafkaProperties properties, Map<String, Object> additionalProperties, String transactionIdPrefix) {
        Map<String, Object> producerProperties = properties.buildProducerProperties();
        producerProperties.putAll(additionalProperties);

        DefaultKafkaProducerFactory<K, V> factory = new DefaultKafkaProducerFactory<>(producerProperties);
        if (transactionIdPrefix != null) {
            factory.setTransactionIdPrefix(transactionIdPrefix);
        }
        return factory;
    }

    public final static <K, V> DefaultKafkaConsumerFactory<K, V> buildConsumerFactory(KafkaProperties properties) {
        Map<String, Object> consumerProperties = properties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    public final static <K, V> DefaultKafkaConsumerFactory<K, V> buildConsumerFactory(KafkaProperties properties, Map<String, Object> additionalProperties) {
        Map<String, Object> consumerProperties = properties.buildConsumerProperties();
        consumerProperties.putAll(additionalProperties);

        return new DefaultKafkaConsumerFactory<>(consumerProperties);
    }

    public final static <K, V> KafkaTemplate<K, V> buildTemplate(KafkaProperties properties) {
        ProducerFactory<K, V> kafkaProducerFactory = KafkaBuilder.buildProducerFactory(properties);
        KafkaTemplate<K, V> kafkaTemplate = new KafkaTemplate<>(kafkaProducerFactory);

        if (properties != null && properties.getTemplate() != null && StringUtils.hasText(properties.getTemplate().getDefaultTopic())) {
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
