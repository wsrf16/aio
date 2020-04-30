package com.aio.portable.swiss.suite.log.classic.properties;

public abstract class PropertyBean {
    //Introspector.decapitalize(RabbitMQLogProperties.class.getName());
    public final static String RABBITMQ_PROPERTIES = "rabbitMQLogProperties";

    //Introspector.decapitalize(KafkaLogProperties.class.getName());
    public final static String KAFKA_PROPERTIES = "kafkaLogProperties";

//    public final static String[] ALL = {KAFKA_PROPERTIES, RABBITMQ_PROPERTIES};

}
