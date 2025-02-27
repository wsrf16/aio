package com.aio.portable.swiss.suite.log.solution.elk.rabbit;

import com.aio.portable.swiss.design.clone.DeepCloneable;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.RabbitMQProperties;
import com.aio.portable.swiss.sugar.meta.ClassSugar;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.aio.portable.swiss.suite.log.support.LogProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class RabbitMQLogProperties extends RabbitMQProperties implements LogProperties, InitializingBean, DeepCloneable {
    private static final boolean DEFAULT_ENABLED = true;

    //    private static final Log log = LogFactory.getLog(RabbitMQLogProperties.class);
    private static final LocalLog log = LocalLog.getLog(RabbitMQLogProperties.class);
    public static final String PREFIX = "spring.log.rabbitmq";

    private static RabbitMQLogProperties instance = new RabbitMQLogProperties();

    public final Boolean getDefaultEnabledIfAbsent() {
        return this.getEnabled() == null ? DEFAULT_ENABLED : this.getEnabled();
    }

    private String esIndex;

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public synchronized static RabbitMQLogProperties getSingleton() {
        return instance;
    }

    private static boolean initialized = false;

    public static boolean initialized() {
        return initialized;
    }

    public RabbitMQLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public static final void initialSingletonInstance(RabbitMQLogProperties rabbitMQLogProperties) {
        instance = rabbitMQLogProperties;
        log.debug("RabbitMQLogProperties InitialSingletonInstance", null, ClassSugar.PropertyDescriptors.toNameValueMapExceptNull(instance));
    }

    public static final void initialSingletonInstance(Binder binder) {
        BindResult<RabbitMQLogProperties> bindResult = binder.bind(RabbitMQLogProperties.PREFIX, RabbitMQLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            RabbitMQLogProperties.initialSingletonInstance(bindResult.get());
        }
        initialized = true;
    }



//    private final void validProperties() {
//        String template = "spring.log.rabbitmq.{0} is null in {1}";
//        String field;
//        if (getHost() == null) {
//            field = "host";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, RabbitMQLogProperties.class));
//        }
//        if (getPort() == null) {
//            field = "port";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, RabbitMQLogProperties.class));
//        }
//        if (getUsername() == null) {
//            field = "username";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, RabbitMQLogProperties.class));
//        }
//        if (getPassword() == null) {
//            field = "password";
//            throw new IllegalArgumentException(MessageFormat.format(template, field, RabbitMQLogProperties.class));
//        }
//    }




//    @Bean
//    public Binding buildBinding() {
//        Queue queue = new Queue(this.queue);
//        DirectExchange exchange = new DirectExchange(this.exchange);
//        return BindingBuilder.bind(queue).to(exchange).with(this.routingKey);
//    }

//    @Bean
//    public void getRabbitTemplate(AmqpTemplate rabbitTemplate) {
//        instance.rabbitTemplate = rabbitTemplate;
////        return rabbitTemplate;
//    }


}
