package com.aio.portable.swiss.assist.log.classic.properties;

import org.apache.commons.lang3.StringUtils;

import java.beans.Introspector;

public class PropertyBean {
    //Introspector.decapitalize(LogRabbitMQProperties.class.getName());
    public static final String RABBITMQ_PROPERTIES = "logRabbitMQProperties";

    //Introspector.decapitalize(LogKafkaProperties.class.getName());
    public static final String KAFKA_PROPERTIES = "logKafkaProperties";

//    public static final String[] ALL = {KAFKA_PROPERTIES, RABBITMQ_PROPERTIES};

}
