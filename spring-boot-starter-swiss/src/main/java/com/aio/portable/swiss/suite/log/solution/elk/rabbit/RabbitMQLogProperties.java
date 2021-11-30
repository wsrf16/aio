package com.aio.portable.swiss.suite.log.solution.elk.rabbit;

import com.aio.portable.swiss.design.clone.DeepCloneable;
import com.aio.portable.swiss.spring.factories.autoconfigure.properties.RabbitMQProperties;
import com.aio.portable.swiss.suite.bean.BeanSugar;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class RabbitMQLogProperties extends RabbitMQProperties implements InitializingBean, DeepCloneable {
    private final static Log log = LogFactory.getLog(RabbitMQLogProperties.class);
    public final static String PREFIX = "spring.log.rabbitmq";

    private static RabbitMQLogProperties instance = new RabbitMQLogProperties();

    private String esIndex;

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public synchronized static RabbitMQLogProperties singletonInstance() {
        return instance;
    }

    public RabbitMQLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initialSingletonInstance(this);
    }

    public final static void initialSingletonInstance(RabbitMQLogProperties rabbitMQLogProperties) {
        instance = rabbitMQLogProperties;
        log.info("RabbitMQLogProperties importSingletonInstance: " + JacksonSugar.obj2ShortJson(BeanSugar.PropertyDescriptors.toNameValueMapExceptNull(instance)));
    }

    public final static void initialSingletonInstance(Binder binder) {
        BindResult<RabbitMQLogProperties> bindResult = binder.bind(RabbitMQLogProperties.PREFIX, RabbitMQLogProperties.class);
        if (bindResult != null && bindResult.isBound()) {
            RabbitMQLogProperties.initialSingletonInstance(bindResult.get());
        } else {
            if (instance != null)
                instance.setEnabled(false);
        }
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
