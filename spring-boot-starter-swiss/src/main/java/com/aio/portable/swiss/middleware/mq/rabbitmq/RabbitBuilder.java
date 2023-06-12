package com.aio.portable.swiss.middleware.mq.rabbitmq;

import com.aio.portable.swiss.spring.factories.autoconfigure.properties.RabbitMQProperties;
import com.aio.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQBindingProperty;
import com.aio.portable.swiss.suite.log.solution.local.LocalLog;
import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public abstract class RabbitBuilder {
//    private static final Log log = LogFactory.getLog(RabbitBuilder.class);
    private static final LocalLog log = LocalLog.getLog(RabbitBuilder.class);

    private static class Exchange {
        public static final String DIRECT = "direct";
        public static final String TOPIC = "topic";
        public static final String FANOUT = "fanout";
    }

    private static Binding binding(RabbitAdmin rabbitAdmin, RabbitMQBindingProperty rabbitMQBindingProperty) {
        String queueText = rabbitMQBindingProperty.getQueue();
        Queue queue = new Queue(queueText, true, false, false, null);
        String exchangeText = rabbitMQBindingProperty.getExchange();
        String routingKeyText = rabbitMQBindingProperty.getRoutingKey();
        String type = rabbitMQBindingProperty.getType().toLowerCase();

        Binding binding;
        declareQueue(rabbitAdmin, queue);

        switch (type) {
            case Exchange.DIRECT: {
                DirectExchange exchange = new DirectExchange(exchangeText, true, false, null);
                declareExchange(rabbitAdmin, exchange);
                binding = BindingBuilder.bind(queue).to(exchange).with(routingKeyText);
            }
            break;
            case Exchange.TOPIC: {
                if (!StringUtils.hasText(exchangeText) || !StringUtils.hasText(routingKeyText))
                    throw new IllegalArgumentException(
                            MessageFormat.format("exchange : {0}, routingKey : {1}.",
                                    exchangeText,
                                    routingKeyText)
                    );
                TopicExchange exchange = new TopicExchange(exchangeText, true, false, null);
                declareExchange(rabbitAdmin, exchange);
                binding = BindingBuilder.bind(queue).to(exchange).with(routingKeyText);
            }
            break;
            case Exchange.FANOUT: {
                FanoutExchange exchange = new FanoutExchange(exchangeText, true, false, null);
                declareExchange(rabbitAdmin, exchange);
                binding = BindingBuilder.bind(queue).to(exchange);
            }
            break;
            default:
                throw new IllegalArgumentException(type);
        }
        declareBinding(rabbitAdmin, binding);
        return binding;


    }

    private static void declareBinding(RabbitAdmin rabbitAdmin, Binding binding) {
        try {
            rabbitAdmin.declareBinding(binding);
        } catch (Exception e) {
            log.error("declareBinding error", e);
        }
    }

    private static void declareExchange(RabbitAdmin rabbitAdmin, AbstractExchange exchange) {
        try {
            rabbitAdmin.declareExchange(exchange);
        } catch (Exception e) {
            log.error("declareExchange error", e);
        }
    }

    private static void declareQueue(RabbitAdmin rabbitAdmin, Queue queue) {
        try {
            rabbitAdmin.declareQueue(queue);
        } catch (Exception e) {
            log.error("declareQueue error", e);
        }
    }

    private static final List<Binding> binding(RabbitAdmin rabbitAdmin, List<RabbitMQBindingProperty> rabbitMQBindingPropertyList) {
        List<Binding> bindingList = rabbitMQBindingPropertyList.stream().map(c -> binding(rabbitAdmin, c)).collect(Collectors.toList());
        return bindingList;
    }

    public static final List<Binding> binding(RabbitAdmin rabbitAdmin, RabbitMQProperties rabbitMQProperties) {
        List<Binding> bindingList = rabbitMQProperties.getBindingList() == null ? null : binding(rabbitAdmin, rabbitMQProperties.getBindingList());
        return bindingList;
    }

    public static final SimpleMessageListenerContainer buildMessageListenerContainer(ConnectionFactory connectionFactory, Queue queue, MessageListener messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(queue);
        container.setMessageListener(messageListener);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                log.info("receive msg : " + new String(body, StandardCharsets.UTF_8));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }

    public static final SimpleMessageListenerContainer buildMessageListenerContainer(ConnectionFactory connectionFactory, Queue queue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(queue);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                log.info("receive msg : " + new String(body, StandardCharsets.UTF_8));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }

//    public static final AmqpTemplate buildRabbitTemplate(ConnectionFactory connectionFactory) {
//        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
//        policy.setInitialInterval(500);
//        policy.setMultiplier(10.0);
//        policy.setMaxInterval(10000);
//
//        RetryTemplate retryTemplate = new RetryTemplate();
//        retryTemplate.setBackOffPolicy(policy);
//
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setRetryTemplate(retryTemplate);
//        rabbitTemplate.setMandatory(false);
////        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        rabbitTemplate.setEncoding(StandardCharsets.UTF_8.toString());
//        return rabbitTemplate;
//    }


    public static final ConnectionFactory buildConnectionFactory(RabbitMQProperties properties) {
        CachingConnectionFactory connectionFactory = null;
        if (connectionFactory == null) {
//                    validProperties();
            String host = properties.getHost();
            int port = properties.getPort();
            connectionFactory = new CachingConnectionFactory(host, port);
            connectionFactory.setUsername(properties.getUsername());
            connectionFactory.setPassword(properties.getPassword());
            connectionFactory.setVirtualHost(properties.getVirtualHost());
            if (properties.getConnectionTimeout() != null)
                connectionFactory.setConnectionTimeout(((int) properties.getConnectionTimeout().toMillis()));
            if (properties.getRequestedHeartbeat() != null)
                connectionFactory.setRequestedHeartBeat((int) properties.getRequestedHeartbeat().getSeconds());

            RabbitProperties.Cache cache = properties.getCache();
            RabbitProperties.Cache.Channel channel = cache.getChannel();
            if (channel != null) {
                if (channel.getSize() != null)
                    connectionFactory.setChannelCacheSize(channel.getSize());
                if (channel.getCheckoutTimeout() != null)
                    connectionFactory.setChannelCheckoutTimeout(channel.getCheckoutTimeout().toMillis());
            }

            RabbitProperties.Cache.Connection connection = cache.getConnection();
            connectionFactory.setCacheMode(connection.getMode());
            if (connection.getSize() != null)
                connectionFactory.setConnectionCacheSize(connection.getSize());
//                    connectionFactory.setPublisherConfirms(properties.isPublisherConfirms());
            connectionFactory.setPublisherReturns(properties.isPublisherReturns());
        }
        return connectionFactory;
    }


    private static final boolean determineMandatoryFlag(RabbitMQProperties properties) {
        Boolean mandatory = properties.getTemplate().getMandatory();
        return mandatory != null ? mandatory : properties.isPublisherReturns();
    }

    public static final RabbitTemplate buildTemplate(RabbitMQProperties properties) {
        RabbitTemplate rabbitTemplate = null;
        if (rabbitTemplate == null) {
            synchronized (RabbitBuilder.class) {
                if (rabbitTemplate == null) {
                    ConnectionFactory connectionFactory = buildConnectionFactory(properties);
                    rabbitTemplate = new RabbitTemplate(connectionFactory);
                    rabbitTemplate.setEncoding(StandardCharsets.UTF_8.name());
                    Boolean mandatory = determineMandatoryFlag(properties);
                    rabbitTemplate.setMandatory(mandatory);
//                    Channel channel = connectionFactory.createConnection().createChannel(false);

                    boolean autoDeclare = properties.getAutoDeclare();
                    if (autoDeclare) {
                        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
                        RabbitBuilder.binding(rabbitAdmin, properties);
                    }
//                    rabbitTemplate.setExchange(false);
//                    rabbitTemplate.setRoutingKey(false);
//                    rabbitTemplate.setDefaultReceiveQueue(super.getDefaultReceiveQueue());


//            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

//            ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
//            policy.setInitialInterval(500);
//            policy.setMultiplier(10.0);
//            policy.setMaxInterval(10000);
//
//            RetryTemplate retryTemplate = new RetryTemplate();
//            retryTemplate.setBackOffPolicy(policy);
//            rabbitTemplate.setRetryTemplate(retryTemplate);
                }
            }
        }
        return rabbitTemplate;
    }


}
