package com.aio.portable.swiss.suite.eventbus.subscriber;

import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.eventbus.event.AbstractEvent;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "className", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RestTemplateSubscriber.class, name = "RestTemplateSubscriber"),
        @JsonSubTypes.Type(value = SimpleSubscriber.class, name = "SimpleSubscriber")})
public abstract class Subscriber {
    private String name = IDS.uuid();

    private String tag;

    private String className;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getClassName() {
        return className == null ? this.getClass().getSimpleName() : className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Subscriber() {
    }

    public Subscriber(String tag) {
        this.tag = tag;
    }

    public Subscriber(@NotNull String name, @NotNull String tag) {
        this.name = name;
        this.tag = tag;
    }

//    public void subscribe(HashMapEventListener listener) {
//        listener.add(this);
//    }

    public abstract <E extends AbstractEvent> Object push(E event);

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
