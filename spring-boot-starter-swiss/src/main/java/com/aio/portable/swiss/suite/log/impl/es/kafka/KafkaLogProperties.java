package com.aio.portable.swiss.suite.log.impl.es.kafka;

import com.aio.portable.swiss.design.clone.DeepCloneable;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

import java.util.ArrayList;
import java.util.List;

public class KafkaLogProperties extends KafkaProperties implements InitializingBean, DeepCloneable {
    private final static Log log = LogFactory.getLog(KafkaLogProperties.class);
    public final static String PREFIX = "spring.log.kafka";

    private static KafkaLogProperties instance = new KafkaLogProperties();

    private Boolean enabled = true;
    private List<KafkaBindingProperty> bindingList = new ArrayList<>();
    private String esIndex;

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }


    public synchronized static KafkaLogProperties singletonInstance() {
        return instance;
    }

    public KafkaLogProperties() {
//        instance = this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public final static void initialSingletonInstance(KafkaLogProperties kafkaLogProperties) {
        instance = kafkaLogProperties;
        log.info("KafkaLogProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(instance));
    }

    public final static void initialSingletonInstance(Binder binder) {
        final BindResult<KafkaLogProperties> bindResult = binder.bind(KafkaLogProperties.PREFIX, KafkaLogProperties.class);
        if (bindResult.isBound()) {
            KafkaLogProperties.initialSingletonInstance(bindResult.get());
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
