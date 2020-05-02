package com.aio.portable.swiss.suite.log.impl.es.kafka;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

public class KafkaLogProperties extends KafkaProperties {
    private Boolean enable = true;
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
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
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
