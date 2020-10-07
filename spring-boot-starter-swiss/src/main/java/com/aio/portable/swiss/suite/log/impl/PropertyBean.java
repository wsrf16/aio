package com.aio.portable.swiss.suite.log.impl;

public abstract class PropertyBean {
    //Introspector.decapitalize(RabbitMQLogProperties.class.getName());
    public final static String RABBITMQ_LOG_PROPERTIES = "rabbitMQLogProperties";

    //Introspector.decapitalize(KafkaLogProperties.class.getName());
    public final static String KAFKA_LOG_PROPERTIES = "kafkaLogProperties";

//    public final static String[] ALL = {KAFKA_PROPERTIES, RABBITMQ_PROPERTIES};
    public final static String LOG_HUB_PROPERTIES = "logHubPropertiesBean";

}
