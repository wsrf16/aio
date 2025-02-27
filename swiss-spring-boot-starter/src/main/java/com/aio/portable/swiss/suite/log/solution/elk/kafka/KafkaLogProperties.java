package com.aio.portable.swiss.suite.log.solution.elk.kafka;

import com.aio.portable.swiss.design.clone.DeepCloneable;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LogProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

import java.util.ArrayList;
import java.util.List;

public class KafkaLogProperties extends KafkaProperties implements LogProperties, InitializingBean, DeepCloneable {
//    private static final Log log = LogFactory.getLog(KafkaLogProperties.class);
    private static final LocalLog log = LocalLog.getLog(KafkaLogProperties.class);

    public static final String PREFIX = "spring.log.kafka";
    private static final boolean DEFAULT_ENABLED = true;

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

    public final Boolean getDefaultEnabledIfAbsent() {
        return this.getEnabled() == null ? DEFAULT_ENABLED : this.getEnabled();
    }

    public synchronized static KafkaLogProperties getSingleton() {
        return instance;
    }

    private static boolean initialized = false;

    public static boolean initialized() {
        return initialized;
    }

    public KafkaLogProperties() {
//        instance = this;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public static final void initialSingletonInstance(KafkaLogProperties kafkaLogHubProperties) {
        instance = kafkaLogHubProperties;
        log.debug("KafkaLogProperties InitialSingletonInstance", null, ClassSugar.PropertyDescriptors.toNameValueMapExceptNull(instance));

    }

    public static final void initialSingletonInstance(Binder binder) {
        BindResult<KafkaLogProperties> bindResult = binder.bind(KafkaLogProperties.PREFIX, KafkaLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            KafkaLogProperties.initialSingletonInstance(bindResult.get());
        }
        initialized = true;
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
