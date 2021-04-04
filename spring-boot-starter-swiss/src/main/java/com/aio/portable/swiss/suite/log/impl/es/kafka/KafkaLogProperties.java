package com.aio.portable.swiss.suite.log.impl.es.kafka;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

public class KafkaLogProperties extends KafkaProperties {
    private Boolean enabled = true;
    private String topic;
    private String esIndex;

    private static KafkaLogProperties instance = new KafkaLogProperties();

    public synchronized static KafkaLogProperties singletonInstance() {
        return instance;
    }

    public KafkaLogProperties() {
        instance = this;
    }

    public Boolean isEnable() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }
}
