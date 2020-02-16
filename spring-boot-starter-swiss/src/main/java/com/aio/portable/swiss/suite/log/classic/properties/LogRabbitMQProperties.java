package com.aio.portable.swiss.suite.log.classic.properties;

import com.aio.portable.swiss.autoconfigure.properties.RabbitMQProperties;

public class LogRabbitMQProperties extends RabbitMQProperties {
    private String esIndex;

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    private static LogRabbitMQProperties instance = new LogRabbitMQProperties();

    public synchronized static LogRabbitMQProperties singletonInstance() {
        return instance;
    }

    protected LogRabbitMQProperties() {
        instance = this;
    }





//    private final void validProperties() {
//        String template = "spring.log.rabbitmq.{0} is null in {1}";
//        String field;
//        if (getHost() == null) {
//            field = "host";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, LogRabbitMQProperties.class));
//        }
//        if (getPort() == null) {
//            field = "port";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, LogRabbitMQProperties.class));
//        }
//        if (getUsername() == null) {
//            field = "username";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, LogRabbitMQProperties.class));
//        }
//        if (getPassword() == null) {
//            field = "password";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, LogRabbitMQProperties.class));
//        }
//    }




//    @Bean
//    public Binding buildBinding() {
//        Queue queue = new Queue(this.queue);
//        DirectExchange exchange = new DirectExchange(this.exchange);
//        return BindingBuilder.bind(queue).to(exchange).with(this.routingKey);
//    }

//    @Bean
//    public void getRabbitTemplate(AmqpTemplate rabbitTemplate) {
//        instance.rabbitTemplate = rabbitTemplate;
////        return rabbitTemplate;
//    }
}
