package com.aio.portable.swiss.structure.log.classic.properties;

import com.aio.portable.swiss.middleware.mq.rabbitmq.property.RabbitMQBindingProperty;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.text.MessageFormat;
import java.util.List;

public class LogRabbitMQProperties { // extends CachingConnectionFactory {
    private boolean enable = true;
    private String host;
    private Integer port;
    private String username;
    private String password;
    private int connectionTimeout = 100;
    private boolean automaticRecoveryEnabled;
    private String virtualHost;
    private String esIndex;
    private Boolean publisherConfirms;
    private Boolean publisherReturns;
    private List<RabbitMQBindingProperty> bindingList;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getEsIndex() {
        return esIndex;
    }

    public void setEsIndex(String esIndex) {
        this.esIndex = esIndex;
    }

    public Boolean getPublisherConfirms() {
        return publisherConfirms;
    }

    public void setPublisherConfirms(Boolean publisherConfirms) {
        this.publisherConfirms = publisherConfirms;
    }

    public Boolean getPublisherReturns() {
        return publisherReturns;
    }

    public void setPublisherReturns(Boolean publisherReturns) {
        this.publisherReturns = publisherReturns;
    }

    public List<RabbitMQBindingProperty> getBindingList() {
        return bindingList;
    }

    public void setBindingList(List<RabbitMQBindingProperty> bindingList) {
        this.bindingList = bindingList;
    }

    private static LogRabbitMQProperties instance = new LogRabbitMQProperties();

    public synchronized static LogRabbitMQProperties singletonInstance() {
        return instance;
    }

    private LogRabbitMQProperties() {
        instance = this;
    }

    CachingConnectionFactory connectionFactory;

    public ConnectionFactory buildConnectionFactory() {
        if (connectionFactory == null) {
            synchronized (this) {
                if (connectionFactory == null) {
                    validProperties();
                    connectionFactory = new CachingConnectionFactory(host, port);
                    connectionFactory.setUsername(username);
                    connectionFactory.setPassword(password);
                    connectionFactory.setConnectionTimeout(connectionTimeout);
                    connectionFactory.setVirtualHost(virtualHost);
                    connectionFactory.setPublisherConfirms(publisherConfirms);
                    connectionFactory.setPublisherReturns(publisherReturns);
                }
            }
        }
        return connectionFactory;
    }

    private void validProperties() {
        if (host == null)
            throwIllegalArgumentException("host");
        if (port == null)
            throwIllegalArgumentException("port");
        if (username == null)
            throwIllegalArgumentException("username");
        if (password == null)
            throwIllegalArgumentException("password");
    }

    private final void throwIllegalArgumentException(String field) {
        String template = "spring.log.rabbitmq.{0} is null in {1}";
        throw new IllegalArgumentException(MessageFormat.format(template, field, LogRabbitMQProperties.class));
    }

    RabbitTemplate rabbitTemplate;

    public RabbitTemplate buildRabbitTemplate() {
        if (rabbitTemplate == null) {
            synchronized (this) {
                if (rabbitTemplate == null) {
                    ConnectionFactory connectionFactory = buildConnectionFactory();
                    rabbitTemplate = new RabbitTemplate(connectionFactory);
                    rabbitTemplate.setMandatory(false);
                    rabbitTemplate.setEncoding("UTF-8");
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
