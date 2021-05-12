package com.aio.portable.swiss.suite.log.impl.es.kafka;

import com.aio.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQBindingProperty;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

import java.util.ArrayList;
import java.util.List;

public class KafkaLogProperties extends KafkaProperties implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(KafkaLogProperties.class);
    public final static String PREFIX = "spring.log.kafka";

    private Boolean enabled = true;
    private List<KafkaBindingProperty> bindingList = new ArrayList<>();
    private String esIndex;

    private static KafkaLogProperties instance = new KafkaLogProperties();

    public synchronized static KafkaLogProperties singletonInstance() {
        return instance;
    }

    public KafkaLogProperties() {
        instance = this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        importSingleton(this);
    }

    public final static void importSingleton(KafkaLogProperties kafkaLogProperties) {
        instance = kafkaLogProperties;
        logger.info("KafkaLogProperties importSingleton: " + JacksonSugar.obj2ShortJson(instance));
    }

    public final static void importSingleton(Binder binder) {
        final BindResult<KafkaLogProperties> bindResult = binder.bind(KafkaLogProperties.PREFIX, KafkaLogProperties.class);
        if (bindResult.isBound()) {
            KafkaLogProperties.importSingleton(bindResult.get());
        } else {
            if (instance != null)
                instance.setEnabled(false);
        }
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<KafkaBindingProperty> getBindingList() {
        return bindingList;
    }

    public void setBindingList(List<KafkaBindingProperty> bindingList) {
        this.bindingList = bindingList;
    }

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }


    public static class KafkaBindingProperty {
        private String topic;

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }
    }


}
