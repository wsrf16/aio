package com.aio.portable.swiss.suite.eventbus.subscriber;

import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.eventbus.event.Event;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "className", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RestTemplateSubscriber.class, name = "RestTemplateSubscriber"),
        @JsonSubTypes.Type(value = SimpleSubscriber.class, name = "SimpleSubscriber")})
public abstract class Subscriber {
    private String name = IDS.uuid();

    private List<String> tags = new ArrayList<>();

    private String className;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getClassName() {
        return className == null ? this.getClass().getSimpleName() : className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Subscriber() {
    }

    public Subscriber(List<String> tags) {
        this.tags = tags;
    }

    public Subscriber(@NotNull String name, @NotNull List<String> tags) {
        this.name = name;
        this.tags = tags;
    }

//    public void subscribe(HashMapEventListener listener) {
//        listener.add(this);
//    }

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
