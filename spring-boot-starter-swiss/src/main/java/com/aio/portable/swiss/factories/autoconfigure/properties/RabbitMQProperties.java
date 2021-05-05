package com.aio.portable.swiss.factories.autoconfigure.properties;

import com.aio.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQBindingProperty;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import java.util.ArrayList;
import java.util.List;

public class RabbitMQProperties extends RabbitProperties {
    private boolean enabled = true;
    private boolean autoDeclare = true;
    private List<RabbitMQBindingProperty> bindingList = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isAutoDeclare() {
        return autoDeclare;
    }

    public void setAutoDeclare(boolean autoDeclare) {
        this.autoDeclare = autoDeclare;
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
