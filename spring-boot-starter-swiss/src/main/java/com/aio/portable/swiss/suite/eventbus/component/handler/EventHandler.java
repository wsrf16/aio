package com.aio.portable.swiss.suite.eventbus.component.handler;

import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.eventbus.component.event.Event;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.validation.constraints.NotNull;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "className", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RestTemplateEventHandler.class, name = "RestTemplateEventHandler"),
        @JsonSubTypes.Type(value = SimpleEventHandler.class, name = "SimpleEventHandler")})
public abstract class EventHandler {
    private String handler = IDS.uuid();

//    private List<String> topics = new ArrayList<>();

    private String className;

    private boolean enabled = true;

    private int retry = 0;

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

//    public List<String> getTopics() {
//        return topics;
//    }
//
//    public void setTopics(List<String> topics) {
//        this.topics = topics;
//    }

    public String getClassName() {
        return className == null ? this.getClass().getSimpleName() : className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRetry() {
        return retry;
    }

    public void setRetry(int retry) {
        this.retry = retry;
    }

    public EventHandler() {
    }

//    public EventHandler(List<String> topics) {
//        this.topics = topics;
//    }

//    public EventHandler(@NotNull String handler, @NotNull List<String> topics) {
//        this.handler = handler;
//        this.topics = topics;
//    }

    public EventHandler(@NotNull String handler) {
        this.handler = handler;
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
