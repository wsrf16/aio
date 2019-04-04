package com.york.portable.swiss.assist.log.classic.parts;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties;

public class LogKafkaProperties extends KafkaProperties {
    private Boolean enable = true;
    private String topic;
    private String esIndex;


    private static LogKafkaProperties instance = new LogKafkaProperties();

    public synchronized static LogKafkaProperties newInstance() {
        return instance;
    }

    public LogKafkaProperties() {
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
