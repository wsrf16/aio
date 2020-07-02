package com.aio.portable.swiss.suite.eventbus.subscribe.subscriber;

import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.eventbus.event.Event;

import javax.validation.constraints.NotNull;

public abstract class Subscriber {
    private String name = IDS.uuid();

    private String className;

    private String topic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className == null ? this.getClass().getName() : className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Subscriber() {
    }

    public Subscriber(String topic) {
        this.topic = topic;
    }

    public Subscriber(@NotNull String name, @NotNull String topic) {
        this.name = name;
        this.topic = topic;
    }

    public abstract <E extends Event> Object push(E event);

//    @Override
//    public <E extends Event> RESULT push(E event) {
//        RESULT responseEntity = null;
//        try {
//            //
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (!StringUtils.isEmpty(errorURL)) {
//                ResponseEntity<Object> errorResponseEntity =
//                        echoError(e);
//            }
//        }
//        if (!StringUtils.isEmpty(errorURL)) {
//            ResponseEntity<Object> backResponseEntity =
//                    echoBack(responseEntity);
//        }
//        return responseEntity;
//    }

//    protected ResponseEntity<Object> echoError(Exception e) {
//        HttpHeaders httpHeaders = RestTemplater.Http.jsonHttpHead();
//        ResponseEntity<Object> errorResponseEntity = RestTemplater.exchange(restTemplate, errorURL, HttpMethod.POST, httpHeaders, LogThrowable.build(e), Object.class);
//        return errorResponseEntity;
//    }
//
//    protected ResponseEntity<Object> echoBack(RESULT responseEntity) {
//        HttpHeaders httpHeaders = RestTemplater.Http.jsonHttpHead();
//        ResponseEntity<Object> backResponseEntity = RestTemplater.exchange(restTemplate, backURL, HttpMethod.POST, httpHeaders, responseEntity, Object.class);
//        return backResponseEntity;
//    }
}
