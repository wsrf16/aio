package com.aio.portable.swiss.hamlet.bean;

import org.springframework.beans.factory.annotation.Autowired;

public class ResponseWrapperUtils extends ResponseWrapper {

    private final static ResponseWrapperUtils singleton = new ResponseWrapperUtils();

    @Autowired(required = false)
    BaseBizStatusEnum baseBizStatusEnum;

    public BaseBizStatusEnum getBizStatusEnum() {
        return baseBizStatusEnum == null ? BaseBizStatusEnum.singletonInstance() : baseBizStatusEnum;
    }

    public static <T> ResponseWrapper<T> succeed() {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticSucceed().getCode(), singleton.getBizStatusEnum().staticSucceed().getMessage());
    }

    public static <T> ResponseWrapper<T> succeed(T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticSucceed().getCode(), singleton.getBizStatusEnum().staticSucceed().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> failed() {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticFailed().getCode(), singleton.getBizStatusEnum().staticFailed().getMessage());
    }

    public static <T> ResponseWrapper<T> failed(String message) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticFailed().getCode(), message);
    }

    public static <T> ResponseWrapper<T> failed(T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticFailed().getCode(), singleton.getBizStatusEnum().staticFailed().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> failed(String message, T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticFailed().getCode(), message, data);
    }




    public static <T> ResponseWrapper<T> exception() {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticException().getCode(), singleton.getBizStatusEnum().staticException().getMessage());
    }

    public static <T> ResponseWrapper<T> exception(String message) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticException().getCode(), message);
    }

    public static <T> ResponseWrapper<T> exception(T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticException().getCode(), singleton.getBizStatusEnum().staticException().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> exception(String message, T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticException().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> invalid() {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticInvalid().getCode(), singleton.getBizStatusEnum().staticInvalid().getMessage());
    }

    public static <T> ResponseWrapper<T> invalid(String message) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticInvalid().getCode(), message);
    }

    public static <T> ResponseWrapper<T> invalid(T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticInvalid().getCode(), singleton.getBizStatusEnum().staticInvalid().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> invalid(String message, T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticInvalid().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> unauthorized() {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticUnauthorized().getCode(), singleton.getBizStatusEnum().staticUnauthorized().getMessage());
    }

    public static <T> ResponseWrapper<T> unauthorized(String message) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticUnauthorized().getCode(), message);
    }

    public static <T> ResponseWrapper<T> unauthorized(T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticUnauthorized().getCode(), singleton.getBizStatusEnum().staticUnauthorized().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> unauthorized(String message, T data) {
        return ResponseWrapper.build(singleton.getBizStatusEnum().staticUnauthorized().getCode(), message, data);
    }
}
