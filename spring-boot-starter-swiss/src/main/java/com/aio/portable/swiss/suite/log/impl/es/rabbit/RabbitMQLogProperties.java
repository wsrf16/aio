package com.aio.portable.swiss.suite.log.impl.es.rabbit;

import com.aio.portable.swiss.autoconfigure.properties.RabbitMQProperties;
import org.springframework.beans.factory.InitializingBean;

public class RabbitMQLogProperties extends RabbitMQProperties implements InitializingBean {
    private static RabbitMQLogProperties instance = new RabbitMQLogProperties();

    private String esIndex;

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public synchronized static RabbitMQLogProperties singletonInstance() {
        return instance;
    }

    protected RabbitMQLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        injectCurrentIntoSingletonInstance();
    }

    private void injectCurrentIntoSingletonInstance() {
        instance = this;
    }


//    private final void validProperties() {
//        String template = "spring.log.rabbitmq.{0} is null in {1}";
//        String field;
//        if (getHost() == null) {
//            field = "host";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, RabbitMQLogProperties.class));
//        }
//        if (getPort() == null) {
//            field = "port";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, RabbitMQLogProperties.class));
//        }
//        if (getUsername() == null) {
//            field = "username";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, RabbitMQLogProperties.class));
//        }
//        if (getPassword() == null) {
//            field = "password";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, RabbitMQLogProperties.class));
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
