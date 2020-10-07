package com.aio.portable.swiss.hamlet.bean;

public class ResponseWrapperUtils extends ResponseWrapper {

    private final static ResponseWrapperUtils singleton = new ResponseWrapperUtils();

    public static <T> ResponseWrapper<T> succeed() {
        return ResponseWrapper.build(BizStatusOriginEnum.staticSucceed().getCode(), BizStatusOriginEnum.staticSucceed().getMessage());
    }

    public static <T> ResponseWrapper<T> succeed(T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticSucceed().getCode(), BizStatusOriginEnum.staticSucceed().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> failed() {
        return ResponseWrapper.build(BizStatusOriginEnum.staticFailed().getCode(), BizStatusOriginEnum.staticFailed().getMessage());
    }

    public static <T> ResponseWrapper<T> failed(String message) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticFailed().getCode(), message);
    }

    public static <T> ResponseWrapper<T> failed(T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticFailed().getCode(), BizStatusOriginEnum.staticFailed().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> failed(String message, T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticFailed().getCode(), message, data);
    }




    public static <T> ResponseWrapper<T> exception() {
        return ResponseWrapper.build(BizStatusOriginEnum.staticException().getCode(), BizStatusOriginEnum.staticException().getMessage());
    }

    public static <T> ResponseWrapper<T> exception(String message) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticException().getCode(), message);
    }

    public static <T> ResponseWrapper<T> exception(T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticException().getCode(), BizStatusOriginEnum.staticException().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> exception(String message, T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticException().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> invalid() {
        return ResponseWrapper.build(BizStatusOriginEnum.staticInvalid().getCode(), BizStatusOriginEnum.staticInvalid().getMessage());
    }

    public static <T> ResponseWrapper<T> invalid(String message) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticInvalid().getCode(), message);
    }

    public static <T> ResponseWrapper<T> invalid(T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticInvalid().getCode(), BizStatusOriginEnum.staticInvalid().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> invalid(String message, T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticInvalid().getCode(), message, data);
    }





    public static <T> ResponseWrapper<T> unauthorized() {
        return ResponseWrapper.build(BizStatusOriginEnum.staticUnauthorized().getCode(), BizStatusOriginEnum.staticUnauthorized().getMessage());
    }

    public static <T> ResponseWrapper<T> unauthorized(String message) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticUnauthorized().getCode(), message);
    }

    public static <T> ResponseWrapper<T> unauthorized(T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticUnauthorized().getCode(), BizStatusOriginEnum.staticUnauthorized().getMessage(), data);
    }

    public static <T> ResponseWrapper<T> unauthorized(String message, T data) {
        return ResponseWrapper.build(BizStatusOriginEnum.staticUnauthorized().getCode(), message, data);
    }
}
