package com.aio.portable.swiss.suite.log.solution.elk.kafka;

import com.aio.portable.swiss.design.clone.DeepCloneable;
import com.aio.portable.swiss.suite.bean.BeanSugar;
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
    private static final Log log = LogFactory.getLog(KafkaLogProperties.class);
    public static final String PREFIX = "spring.log.kafka";

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

    public static final void initialSingletonInstance(KafkaLogProperties kafkaLogProperties) {
        instance = kafkaLogProperties;
        log.debug("KafkaLogProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(instance)));
    }

    public static final void initialSingletonInstance(Binder binder) {
        BindResult<KafkaLogProperties> bindResult = binder.bind(KafkaLogProperties.PREFIX, KafkaLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
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
