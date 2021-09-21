package com.aio.portable.swiss.hamlet.bean;

public class ResponseWrapperUtils extends ResponseWrapper {

    private final static ResponseWrapperUtils singleton = new ResponseWrapperUtils();

    private BaseBizStatusEnum baseBizStatusEnum;

    public static BaseBizStatusEnum getBizStatusEnum() {
        return singleton.baseBizStatusEnum == null ? BaseBizStatusEnum.singletonInstance() : singleton.baseBizStatusEnum;
    }

    public static <T> ResponseWrapper<T> succeed() {
        return ResponseWrapper.build(getBizStatusEnum().succeed().getCode(), getBizStatusEnum().succeed().getMessage());
    }

    public static <T> ResponseWrapper<T> succeed(T data) {
        return ResponseWrapper.build(getBizStatusEnum().succeed().getCode(), getBizStatusEnum().succeed().getMessage(), data);
    }





    public static <T> ResponseWrapper<T> failed() {
        return ResponseWrapper.build(getBizStatusEnum().failed().getCode(), getBizStatusEnum().failed().getMessage());
    }

    public static <T> ResponseWrapper<T> failed(String message) {
        return ResponseWrapper.build(getBizStatusEnum().failed().getCode(), message);
    }

    public static <T> ResponseWrapper<T> failed(T data) {
        return ResponseWrapper.build(getBizStatusEnum().failed().getCode(), getBizStatusEnum().failed().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> failed(String message, T data) {
        return ResponseWrapper.build(getBizStatusEnum().failed().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> exception() {
        return ResponseWrapper.build(getBizStatusEnum().exception().getCode(), getBizStatusEnum().exception().getMessage());
    }

    public static <T> ResponseWrapper<T> exception(String message) {
        return ResponseWrapper.build(getBizStatusEnum().exception().getCode(), message);
    }

    public static <T> ResponseWrapper<T> exception(T data) {
        return ResponseWrapper.build(getBizStatusEnum().exception().getCode(), getBizStatusEnum().exception().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> exception(String message, T data) {
        return ResponseWrapper.build(getBizStatusEnum().exception().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> invalid() {
        return ResponseWrapper.build(getBizStatusEnum().invalid().getCode(), getBizStatusEnum().invalid().getMessage());
    }

    public static <T> ResponseWrapper<T> invalid(String message) {
        return ResponseWrapper.build(getBizStatusEnum().invalid().getCode(), message);
    }

    public static <T> ResponseWrapper<T> invalid(T data) {
        return ResponseWrapper.build(getBizStatusEnum().invalid().getCode(), getBizStatusEnum().invalid().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> invalid(String message, T data) {
        return ResponseWrapper.build(getBizStatusEnum().invalid().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> unauthorized() {
        return ResponseWrapper.build(getBizStatusEnum().unauthorized().getCode(), getBizStatusEnum().unauthorized().getMessage());
    }

    public static <T> ResponseWrapper<T> unauthorized(String message) {
        return ResponseWrapper.build(getBizStatusEnum().unauthorized().getCode(), message);
    }

    public static <T> ResponseWrapper<T> unauthorized(T data) {
        return ResponseWrapper.build(getBizStatusEnum().unauthorized().getCode(), getBizStatusEnum().unauthorized().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> unauthorized(String message, T data) {
        return ResponseWrapper.build(getBizStatusEnum().unauthorized().getCode(), message, data);
    }
}
