package com.york.portable.swiss.middleware.mq.rabbitmq;

import com.rabbitmq.client.Channel;
//import com.york.portable.swiss.net.mq.rabbitmq.property.RabbitMQBindingListProperties;
import com.york.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQBindingProperty;
import com.york.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQCachingConnectionFactoryProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

public class RabbitMQUtil {
    public static class Exchange {
        public final static String DIRECT = "direct";
        public final static String TOPIC = "topic";
        public final static String FANOUT = "fanout";
    }

    private static Binding binding(RabbitMQBindingProperty rabbitMQBindingProperty) {
        Binding binding = null;
        switch (rabbitMQBindingProperty.getType().toLowerCase()) {
            case Exchange.DIRECT:
                binding = BindingBuilder.bind(new Queue(rabbitMQBindingProperty.getQueue())).to(new DirectExchange(rabbitMQBindingProperty.getExchange())).with(rabbitMQBindingProperty.getRoutingKey());
                break;
            case Exchange.TOPIC:
                if (StringUtils.isNotBlank(rabbitMQBindingProperty.getExchange()) && rabbitMQBindingProperty.getRoutingKey() != null)
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

    public static List<Binding> binding(List<RabbitMQBindingProperty> rabbitMQBindingPropertyList) {
        List<Binding> bindingList = rabbitMQBindingPropertyList.stream().map(c -> binding(c)).collect(Collectors.toList());
        return bindingList;
    }

//    public static List<Binding> binding(RabbitMQBindingListProperties rabbitMQBindingListProperties) {
//        List<Binding> bindingList = rabbitMQBindingListProperties.getBindingList().stream().map(c -> binding(c)).collect(Collectors.toList());
//        return bindingList;
//    }

    public static List<Binding> binding(RabbitMQCachingConnectionFactoryProperties rabbitMQCachingConnectionFactoryProperties) {
        List<Binding> bindingList = rabbitMQCachingConnectionFactoryProperties.getBindingList().stream().map(c -> binding(c)).collect(Collectors.toList());
        return bindingList;
    }

    public static SimpleMessageListenerContainer buildMessageListenerContainer(ConnectionFactory connectionFactory, Queue queue, MessageListener messageListener) {
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

    public static SimpleMessageListenerContainer buildMessageListenerContainer(ConnectionFactory connectionFactory, Queue queue) {
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

    public static AmqpTemplate buildRabbitTemplate(ConnectionFactory connectionFactory) {
        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(500);
        policy.setMultiplier(10.0);
        policy.setMaxInterval(10000);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setBackOffPolicy(policy);

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setRetryTemplate(retryTemplate);
        rabbitTemplate.setMandatory(false);
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        return rabbitTemplate;
    }

}
