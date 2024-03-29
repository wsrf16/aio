package com.aio.portable.park.unit;

//import com.aio.portable.swiss.suite.eventbus.bus.EventBus;
//import com.aio.portable.swiss.suite.eventbus.component.event.Event;
//import com.aio.portable.swiss.suite.eventbus.component.handler.RestTemplateEventHandler;
//import com.aio.portable.swiss.suite.eventbus.component.handler.http.HttpAttempt;
//import com.aio.portable.swiss.suite.eventbus.component.subscriber.EventSubscriber;
//import com.aio.portable.swiss.suite.net.protocol.http.RestTemplater;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.TestComponent;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//@TestComponent
//public class EventBusTest {
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Autowired
//    EventBus eventBus;
//
//    @Test
//    public void foobar() {
//        RestTemplateEventHandler.setRestTemplate(restTemplate);
//
//        importEventSubscriber();
//        publish();
//    }
//
//    private void publish() {
//        Map map = new HashMap<String, Object>();
//        map.put("aaaa", 1111);
//        map.put("bbbb", "bbbb");
//        Event event = new Event(map, "tag-test");
//
//        EventBus build = EventBus.getSingleton();
//        build.send(event);
//    }
//
//    private EventSubscriber importEventSubscriber() {
//        HttpAttempt httpAttempt1 = new HttpAttempt();
//        httpAttempt1.setUrl("https://api.uukit.com/req/mock/8knydd/");
//        httpAttempt1.setHeaders(RestTemplater.Http.jsonHttpHead());
//        httpAttempt1.setMethod(HttpMethod.GET);
//        HttpAttempt httpAttempt2 = new HttpAttempt();
//        httpAttempt2.setUrl("https://api.uukit.com/req/mock/8knydd/");
//        httpAttempt2.setHeaders(RestTemplater.Http.jsonHttpHead());
//        httpAttempt2.setMethod(HttpMethod.POST);
//
//
//        RestTemplateEventHandler handler1 = new RestTemplateEventHandler("handler-1", httpAttempt1);
//        RestTemplateEventHandler handler2 = new RestTemplateEventHandler("handler-2", httpAttempt2);
//
//        EventSubscriber subscriber = eventBus.addEventSubscriber("namespace-task", "subscriber-task", new ArrayList<String>() {{
//            add(("tag-test"));
//        }});
//        subscriber.add(handler1);
//        subscriber.add(handler2);
//        return subscriber;
//    }
//}
