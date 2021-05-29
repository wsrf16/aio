package com.aio.portable.swiss.factories.autoconfigure.properties;

import com.aio.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQBindingProperty;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;

import java.util.ArrayList;
import java.util.List;

public class RabbitMQProperties extends RabbitProperties {
    private int port = 5672;
    private Boolean enabled = true;
    private Boolean autoDeclare = true;
    private List<RabbitMQBindingProperty> bindingList = new ArrayList<>();

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void setPort(int port) {
        this.port = port;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAutoDeclare() {
        return autoDeclare;
    }

    public void setAutoDeclare(Boolean autoDeclare) {
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
