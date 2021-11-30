package com.aio.portable.swiss.hamlet.bean;

public abstract class ResponseWrappers extends ResponseWrapper {
    //    private BaseBizStatusEnum baseBizStatusEnum;

    private static BaseBizStatusEnum singletonBizStatusEnum() {
        return BaseBizStatusEnum.singletonInstance();
    }

    public static <T> ResponseWrapper<T> succeed() {
        return ResponseWrapper.build(singletonBizStatusEnum().succeed().getCode(), singletonBizStatusEnum().succeed().getMessage());
    }

    public static <T> ResponseWrapper<T> succeed(T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().succeed().getCode(), singletonBizStatusEnum().succeed().getMessage(), data);
    }





    public static <T> ResponseWrapper<T> failed() {
        return ResponseWrapper.build(singletonBizStatusEnum().failed().getCode(), singletonBizStatusEnum().failed().getMessage());
    }

    public static <T> ResponseWrapper<T> failed(String message) {
        return ResponseWrapper.build(singletonBizStatusEnum().failed().getCode(), message);
    }

    public static <T> ResponseWrapper<T> failed(T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().failed().getCode(), singletonBizStatusEnum().failed().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> failed(String message, T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().failed().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> exception() {
        return ResponseWrapper.build(singletonBizStatusEnum().exception().getCode(), singletonBizStatusEnum().exception().getMessage());
    }

    public static <T> ResponseWrapper<T> exception(String message) {
        return ResponseWrapper.build(singletonBizStatusEnum().exception().getCode(), message);
    }

    public static <T> ResponseWrapper<T> exception(T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().exception().getCode(), singletonBizStatusEnum().exception().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> exception(String message, T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().exception().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> invalid() {
        return ResponseWrapper.build(singletonBizStatusEnum().invalid().getCode(), singletonBizStatusEnum().invalid().getMessage());
    }

    public static <T> ResponseWrapper<T> invalid(String message) {
        return ResponseWrapper.build(singletonBizStatusEnum().invalid().getCode(), message);
    }

    public static <T> ResponseWrapper<T> invalid(T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().invalid().getCode(), singletonBizStatusEnum().invalid().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> invalid(String message, T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().invalid().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> unauthorized() {
        return ResponseWrapper.build(singletonBizStatusEnum().unauthorized().getCode(), singletonBizStatusEnum().unauthorized().getMessage());
    }

    public static <T> ResponseWrapper<T> unauthorized(String message) {
        return ResponseWrapper.build(singletonBizStatusEnum().unauthorized().getCode(), message);
    }

    public static <T> ResponseWrapper<T> unauthorized(T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().unauthorized().getCode(), singletonBizStatusEnum().unauthorized().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> unauthorized(String message, T data) {
        return ResponseWrapper.build(singletonBizStatusEnum().unauthorized().getCode(), message, data);
    }
}
