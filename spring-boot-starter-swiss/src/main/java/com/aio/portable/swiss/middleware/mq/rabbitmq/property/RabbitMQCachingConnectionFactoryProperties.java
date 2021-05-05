package com.aio.portable.swiss.middleware.mq.rabbitmq.property;

//public class RabbitMQCachingConnectionFactoryProperties extends CachingConnectionFactory {
//    private List<RabbitMQBindingProperty> bindingList;
//
//    public List<RabbitMQBindingProperty> getBindingList() {
//        return bindingList;
//    }
//
//    public void setBindingList(List<RabbitMQBindingProperty> bindingList) {
//        this.bindingList = bindingList;
//    }
//
//    private static RabbitMQCachingConnectionFactoryProperties instance = new RabbitMQCachingConnectionFactoryProperties();
//
//    public synchronized static RabbitMQCachingConnectionFactoryProperties singletonInstance() {
//        return instance;
//    }
//
//    RabbitTemplate rabbitTemplate;
//
//    public RabbitMQCachingConnectionFactoryProperties() {
//        validProperties();
//        instance = this;
//    }
//
////    CachingConnectionFactory connectionFactory;
//
//    private void validProperties() {
//        if (super.getHost() == null)
//            throw new IllegalArgumentException(MessageFormat.format("spring.log.rabbitmq.{0} is null in {1}", "host", "application.properties"));
//        if (super.getUsername() == null)
//            throw new IllegalArgumentException(MessageFormat.format("spring.log.rabbitmq.{0} is null in {1}", "username", "application.properties"));
//    }
//
//    public RabbitTemplate buildRabbitTemplate() {
//        if (rabbitTemplate == null) {
//            synchronized (this) {
//                if (rabbitTemplate == null) {
//                    ConnectionFactory connectionFactory = this;
//                    rabbitTemplate = new RabbitTemplate(connectionFactory);
//                    rabbitTemplate.setMandatory(false);
//                    // "UTF-8"
//                    rabbitTemplate.setEncoding(StandardCharsets.UTF_8.displayName());
////            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//
////            ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
////            policy.setInitialInterval(500);
////            policy.setMultiplier(10.0);
////            policy.setMaxInterval(10000);
////
////            RetryTemplate retryTemplate = new RetryTemplate();
////            retryTemplate.setBackOffPolicy(policy);
////            rabbitTemplate.setRetryTemplate(retryTemplate);
//                }
//            }
//        }
//        return rabbitTemplate;
//    }
//
//    public SimpleRabbitListenerContainerFactory buildSimpleRabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer containerFactoryConfigurer) {
//        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
//        // 把 ConnectionFactory 注入 SimpleRabbitListenerContainerFactory
//        containerFactoryConfigurer.configure(containerFactory, this);
//        return containerFactory;
//    }
//
//
//}
