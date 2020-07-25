package com.aio.portable.swiss.swiss;

//import com.aio.portable.swiss.suite.eventbus.EventBus;
//import com.aio.portable.swiss.suite.eventbus.event.TopicEvent;
//import com.aio.portable.swiss.suite.eventbus.listen.listener.HashMapSubscriberEventListener;
//import com.aio.portable.swiss.suite.eventbus.subscribe.subscriber.RestTemplateSubscriber;
//import com.aio.portable.swiss.suite.eventbus.subscribe.subscriber.http.HttpAttempt;
//import com.aio.portable.swiss.suite.net.protocol.http.RestTemplater;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestComponent;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@TestComponent
//public class EventBusTest {
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Test
//    public void todo() {
//        RestTemplateSubscriber.setRestTemplate(restTemplate);
//
//        subscribe();
//        publish();
//    }
//
//    private void publish() {
//        Map map = new HashMap<String, Object>();
//        map.put("aaaa", 1111);
//        map.put("bbbb", "bbbb");
//        TopicEvent event = new TopicEvent("topic-test", map);
//
//        EventBus build = EventBus.singletonInstance();
//        build.send(event);
//    }
//
//    private void subscribe() {
//        HashMapSubscriberEventListener<RestTemplateSubscriber> subscriberRegistry = buildSubscriberEventListener();
//        HashMapSubscriberEventListener listener = new HashMapSubscriberEventListener("public-listen");
//        EventBus build = EventBus.singletonInstance();
//        build.addListener(listener);
//    }
//
//    private HashMapSubscriberEventListener<RestTemplateSubscriber> buildSubscriberEventListener() {
//        HttpAttempt httpAttempt1 = new HttpAttempt();
//        httpAttempt1.setUrl("https://api.uukit.com/req/mock/8knydd/");
//        httpAttempt1.setHttpHeaders(RestTemplater.Http.jsonHttpHead());
//        httpAttempt1.setHttpMethod(HttpMethod.GET);
//        HttpAttempt httpAttempt2 = new HttpAttempt();
//        httpAttempt2.setUrl("https://api.uukit.com/req/mock/8knydd/");
//        httpAttempt2.setHttpHeaders(RestTemplater.Http.jsonHttpHead());
//        httpAttempt2.setHttpMethod(HttpMethod.POST);
//
//
//        RestTemplateSubscriber subscriber1 = new RestTemplateSubscriber("subscriber-1","topic-test", httpAttempt1);
//        RestTemplateSubscriber subscriber2 = new RestTemplateSubscriber("subscriber-2","topic-test", httpAttempt2);
//
//        HashMapSubscriberEventListener<RestTemplateSubscriber> subscriberRegistry = new HashMapSubscriberEventListener<>("my-subscriber-registry");
//        subscriberRegistry.add(subscriber1);
//        subscriberRegistry.add(subscriber2);
//        return subscriberRegistry;
//    }
//}
