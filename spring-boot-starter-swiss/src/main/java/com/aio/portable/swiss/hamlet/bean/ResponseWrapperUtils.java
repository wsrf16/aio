package com.aio.portable.swiss.hamlet.bean;

public class ResponseWrapperUtils extends ResponseWrapper {

    private final static ResponseWrapperUtils singleton = new ResponseWrapperUtils();

    public static <T> ResponseWrapper<T> succeed() {
        return ResponseWrapper.build(BizStatusNativeEnum.staticSucceed().getCode(), BizStatusNativeEnum.staticSucceed().getMessage());
    }

    public static <T> ResponseWrapper<T> succeed(T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticSucceed().getCode(), BizStatusNativeEnum.staticSucceed().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> failed() {
        return ResponseWrapper.build(BizStatusNativeEnum.staticFailed().getCode(), BizStatusNativeEnum.staticFailed().getMessage());
    }

    public static <T> ResponseWrapper<T> failed(String message) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticFailed().getCode(), message);
    }

    public static <T> ResponseWrapper<T> failed(T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticFailed().getCode(), BizStatusNativeEnum.staticFailed().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> failed(String message, T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticFailed().getCode(), message, data);
    }




    public static <T> ResponseWrapper<T> exception() {
        return ResponseWrapper.build(BizStatusNativeEnum.staticException().getCode(), BizStatusNativeEnum.staticException().getMessage());
    }

    public static <T> ResponseWrapper<T> exception(String message) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticException().getCode(), message);
    }

    public static <T> ResponseWrapper<T> exception(T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticException().getCode(), BizStatusNativeEnum.staticException().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> exception(String message, T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticException().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> invalid() {
        return ResponseWrapper.build(BizStatusNativeEnum.staticInvalid().getCode(), BizStatusNativeEnum.staticInvalid().getMessage());
    }

    public static <T> ResponseWrapper<T> invalid(String message) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticInvalid().getCode(), message);
    }

    public static <T> ResponseWrapper<T> invalid(T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticInvalid().getCode(), BizStatusNativeEnum.staticInvalid().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> invalid(String message, T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticInvalid().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> unauthorized() {
        return ResponseWrapper.build(BizStatusNativeEnum.staticUnauthorized().getCode(), BizStatusNativeEnum.staticUnauthorized().getMessage());
    }

    public static <T> ResponseWrapper<T> unauthorized(String message) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticUnauthorized().getCode(), message);
    }

    public static <T> ResponseWrapper<T> unauthorized(T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticUnauthorized().getCode(), BizStatusNativeEnum.staticUnauthorized().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> unauthorized(String message, T data) {
        return ResponseWrapper.build(BizStatusNativeEnum.staticUnauthorized().getCode(), message, data);
    }
}
