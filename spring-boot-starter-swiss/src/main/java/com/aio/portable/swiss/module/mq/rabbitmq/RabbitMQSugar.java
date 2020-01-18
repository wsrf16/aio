package com.aio.portable.swiss.module.mq.rabbitmq;

import com.aio.portable.swiss.autoconfigure.properties.RabbitMQProperties;
import com.aio.portable.swiss.module.mq.rabbitmq.property.RabbitMQBindingProperty;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.StringUtils;

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

    private static Binding binding(RabbitMQBindingProperty rabbitMQBindingProperty) {
        Binding binding;
        switch (rabbitMQBindingProperty.getType().toLowerCase()) {
            case Exchange.DIRECT:
                binding = BindingBuilder.bind(new Queue(rabbitMQBindingProperty.getQueue())).to(new DirectExchange(rabbitMQBindingProperty.getExchange())).with(rabbitMQBindingProperty.getRoutingKey());
                break;
            case Exchange.TOPIC:
                if (StringUtils.hasText(rabbitMQBindingProperty.getExchange()) && rabbitMQBindingProperty.getRoutingKey() != null)
                    binding = BindingBuilder.bind(new Queue(rabbitMQBindingProperty.getQueue())).to(new TopicExchange(rabbitMQBindingProperty.getExchange())).with(rabbitMQBindingProperty.getRoutingKey());
                else
                    throw new IllegalArgumentException(
                            MessageFormat.format("exchange : {0}, routingKey : {1}.",
                                    rabbitMQBindingProperty.getExchange(),
                                    rabbitMQBindingProperty.getRoutingKey())
                    );
                break;
            case Exchange.FANOUT:
                binding = BindingBuilder.bind(new Queue(rabbitMQBindingProperty.getQueue())).to(new FanoutExchange(rabbitMQBindingProperty.getExchange()));
                break;
            default:
                throw new IllegalArgumentException(rabbitMQBindingProperty.getType());
        }
        return binding;
    }

    private final static List<Binding> binding(List<RabbitMQBindingProperty> rabbitMQBindingPropertyList) {
        List<Binding> bindingList = rabbitMQBindingPropertyList.stream().map(c -> binding(c)).collect(Collectors.toList());
        return bindingList;
    }

//    public final static List<Binding> binding(RabbitMQBindingListProperties rabbitMQBindingListProperties) {
//        List<Binding> bindingList = rabbitMQBindingListProperties.getBindingList().stream().map(c -> binding(c)).collect(Collectors.toList());
//        return bindingList;
//    }

    public final static List<Binding> binding(RabbitMQProperties rabbitMQProperties) {
        List<Binding> bindingList = rabbitMQProperties.getBindingList() == null ? null : rabbitMQProperties.getBindingList().stream().map(c -> binding(c)).collect(Collectors.toList());
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
                    rabbitTemplate.setEncoding(StandardCharsets.UTF_8.toString());
                    Boolean mandatory = determineMandatoryFlag(properties);
                    rabbitTemplate.setMandatory(mandatory);
                    RabbitMQSugar.binding(properties);
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
