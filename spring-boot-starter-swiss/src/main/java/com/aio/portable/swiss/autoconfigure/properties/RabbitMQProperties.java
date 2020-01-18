package com.aio.portable.swiss.autoconfigure.properties;

import com.aio.portable.swiss.module.mq.rabbitmq.property.RabbitMQBindingProperty;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class RabbitMQProperties extends RabbitProperties {
    private boolean enable = true;
    private List<RabbitMQBindingProperty> bindingList = new ArrayList<>();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public List<RabbitMQBindingProperty> getBindingList() {
        return bindingList;
    }

    public void setBindingList(List<RabbitMQBindingProperty> bindingList) {
        this.bindingList = bindingList;
    }

    private static RabbitMQProperties instance = new RabbitMQProperties();

    public synchronized static RabbitMQProperties singletonInstance() {
        return instance;
    }

    protected RabbitMQProperties() {
        instance = this;
    }

}
