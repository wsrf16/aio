package com.aio.portable.swiss.module.mq.rabbitmq;

import com.aio.portable.swiss.autoconfigure.properties.RabbitMQProperties;
import com.aio.portable.swiss.module.mq.rabbitmq.property.RabbitMQBindingProperty;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public abstract class RabbitMQSugar {
    private static class Exchange {
        public final static String DIRECT = "direct";
        public final static String TOPIC = "topic";
        public final static String FANOUT = "fanout";
    }

    private static Binding binding(RabbitAdmin rabbitAdmin, RabbitMQBindingProperty rabbitMQBindingProperty) {
        String queueText = rabbitMQBindingProperty.getQueue();
        Queue queue = new Queue(queueText, true, false, false, null);
        String exchangeText = rabbitMQBindingProperty.getExchange();
        String routingKeyText = rabbitMQBindingProperty.getRoutingKey();
        String type = rabbitMQBindingProperty.getType().toLowerCase();

        Binding binding;
        rabbitAdmin.declareQueue(queue);

        switch (type) {
            // BuiltinExchangeType
            case Exchange.DIRECT: {
                DirectExchange exchange = new DirectExchange(exchangeText, true, false, null);
                rabbitAdmin.declareExchange(exchange);
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
                rabbitAdmin.declareExchange(exchange);
                binding = BindingBuilder.bind(queue).to(exchange).with(routingKeyText);
            }
            break;
            case Exchange.FANOUT: {
                FanoutExchange exchange = new FanoutExchange(exchangeText, true, false, null);
                rabbitAdmin.declareExchange(exchange);
                binding = BindingBuilder.bind(queue).to(exchange);
            }
            break;
            default:
                throw new IllegalArgumentException(type);
        }
        rabbitAdmin.declareBinding(binding);
        return binding;


    }

    private final static List<Binding> binding(RabbitAdmin rabbitAdmin, List<RabbitMQBindingProperty> rabbitMQBindingPropertyList) {
        List<Binding> bindingList = rabbitMQBindingPropertyList.stream().map(c -> binding(rabbitAdmin, c)).collect(Collectors.toList());
        return bindingList;
    }

    public final static List<Binding> binding(RabbitAdmin rabbitAdmin, RabbitMQProperties rabbitMQProperties) {
        List<Binding> bindingList = rabbitMQProperties.getBindingList() == null ? null : binding(rabbitAdmin, rabbitMQProperties.getBindingList());
        return bindingList;
    }

    public final static SimpleMessageListenerContainer buildMessageListenerContainer(ConnectionFactory connectionFactory, Queue queue, MessageListener messageListener) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(queue);
        container.setMessageListener(messageListener);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("receive msg : " + new String(body, StandardCharsets.UTF_8));//////////
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }

    public final static SimpleMessageListenerContainer buildMessageListenerContainer(ConnectionFactory connectionFactory, Queue queue) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.setQueues(queue);
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] body = message.getBody();
                System.out.println("receive msg : " + new String(body, StandardCharsets.UTF_8));//////////
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });
        return container;
    }

//    public final static AmqpTemplate buildRabbitTemplate(ConnectionFactory connectionFactory) {
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


    public final static ConnectionFactory buildConnectionFactory(RabbitMQProperties properties) {
        CachingConnectionFactory connectionFactory = null;
        if (connectionFactory == null) {
            synchronized (RabbitMQSugar.class) {
                if (connectionFactory == null) {
//                    validProperties();
                    connectionFactory = new CachingConnectionFactory(properties.getHost(), properties.getPort());
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
                    connectionFactory.setPublisherConfirms(properties.isPublisherConfirms());
                    connectionFactory.setPublisherReturns(properties.isPublisherReturns());
                }
            }
        }
        return connectionFactory;
    }


    private final static boolean determineMandatoryFlag(RabbitMQProperties properties) {
        Boolean mandatory = properties.getTemplate().getMandatory();
        return mandatory != null ? mandatory : properties.isPublisherReturns();
    }

    public final static RabbitTemplate buildRabbitTemplate(RabbitMQProperties properties) {
        RabbitTemplate rabbitTemplate = null;
        if (rabbitTemplate == null) {
            synchronized (RabbitMQSugar.class) {
                if (rabbitTemplate == null) {
                    ConnectionFactory connectionFactory = buildConnectionFactory(properties);
                    rabbitTemplate = new RabbitTemplate(connectionFactory);
                    rabbitTemplate.setEncoding(StandardCharsets.UTF_8.name());
                    Boolean mandatory = determineMandatoryFlag(properties);
                    rabbitTemplate.setMandatory(mandatory);
                    Channel channel = connectionFactory.createConnection().createChannel(false);

                    boolean autoDeclare = properties.isAutoDeclare();
                    if (autoDeclare) {
                        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
                        RabbitMQSugar.binding(rabbitAdmin, properties);
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
