package com.aio.portable.swiss.suite.log.classic.properties;

public abstract class PropertyBean {
    //Introspector.decapitalize(LogRabbitMQProperties.class.getName());
    public final static String RABBITMQ_PROPERTIES = "logRabbitMQProperties";

    //Introspector.decapitalize(LogKafkaProperties.class.getName());
    public final static String KAFKA_PROPERTIES = "logKafkaProperties";

//    public final static String[] ALL = {KAFKA_PROPERTIES, RABBITMQ_PROPERTIES};

}
