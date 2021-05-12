package com.aio.portable.swiss.suite.log.impl.es.rabbit;

import com.aio.portable.swiss.factories.autoconfigure.properties.RabbitMQProperties;
import com.aio.portable.swiss.suite.bean.serializer.json.JacksonSugar;
import com.aio.portable.swiss.suite.log.impl.es.kafka.KafkaLogProperties;
import com.aio.portable.swiss.suite.resource.ClassSugar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;

public class RabbitMQLogProperties extends RabbitMQProperties implements InitializingBean {
    private final static Logger logger = LoggerFactory.getLogger(RabbitMQLogProperties.class);
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

    protected RabbitMQLogProperties() {
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        importSingleton(this);
    }

    public final static void importSingleton(RabbitMQLogProperties rabbitMQLogProperties) {
        instance = rabbitMQLogProperties;
        logger.info("RabbitMQLogProperties importSingleton: " + JacksonSugar.obj2ShortJson(instance));
    }

    public final static void importSingleton(Binder binder) {
        final BindResult<RabbitMQLogProperties> bindResult = binder.bind(RabbitMQLogProperties.PREFIX, RabbitMQLogProperties.class);
        if (bindResult.isBound()) {
            RabbitMQLogProperties.importSingleton(bindResult.get());
        } else {
            if (RabbitMQLogProperties.singletonInstance() != null)
                RabbitMQLogProperties.singletonInstance().setEnabled(false);
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
