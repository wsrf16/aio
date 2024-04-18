package com.aio.portable.swiss.hamlet.bean;

import java.util.function.Supplier;

public abstract class ResponseWrappers {
    private static BaseBizStatusEnum singletonBizStatusEnum() {
        return BaseBizStatusEnum.getSingleton();
    }
//    private static BaseResponseWrapper<?> responseWrapper;
//    private static Class<? extends ResponseWrapper> clazz = ClassicResponseWrapper.class;
//
//    private static final <T> ResponseWrapper<T> instance() {
//        ResponseWrapper<T> responseWrapper = ClassSugar.newInstance(clazz);
//        return responseWrapper;
//    }
//
//    public static final void SetResponseWrapper(Class<? extends ResponseWrapper> clazz) {
//        ResponseWrappers.clazz = clazz;
//    }

    private static Supplier<ResponseWrapper> builder = ClassicResponseWrapper::new;

    public static final void setBuilder(Supplier<ResponseWrapper> builder) {
        ResponseWrappers.builder = builder;
    }

    /**
     * 默认返回实体
     * @return
     */
    public static <T> ResponseWrapper<T> build() {
        return builder.get();
    }

    /**
     * 创建返回实体（返回文字消息）
     * @param statusCode
     * @param message
     * @return
     */
    public static <T> ResponseWrapper<T> build(int statusCode, String message) {
        ResponseWrapper<T> responseWrapper = build();
        responseWrapper.setCode(statusCode);
        responseWrapper.setMessage(message);
        return responseWrapper;
    }

    /**
     * 创建返回实体（返回数据）
     * @param statusCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseWrapper<T> build(int statusCode, T data) {
        ResponseWrapper<T> responseWrapper = build();
        responseWrapper.setCode(statusCode);
        responseWrapper.setData(data);
        return responseWrapper;
    }

    /**
     * 创建返回实体（返回文字消息和数据）
     * @param statusCode
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseWrapper<T> build(int statusCode, String message, T data) {
        ResponseWrapper<T> responseWrapper = build();
        responseWrapper.setCode(statusCode);
        responseWrapper.setMessage(message);
        responseWrapper.setData(data);
        return responseWrapper;
    }

    public static final <T> ResponseWrapper<T> succeed() {
        return ResponseWrappers.build(singletonBizStatusEnum().succeed().getCode(), singletonBizStatusEnum().succeed().getMessage());
    }

    public static final <T> ResponseWrapper<T> succeed(T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().succeed().getCode(), singletonBizStatusEnum().succeed().getMessage(), data);
    }





    public static final <T> ResponseWrapper<T> failed() {
        return ResponseWrappers.build(singletonBizStatusEnum().failed().getCode(), singletonBizStatusEnum().failed().getMessage());
    }

    public static final <T> ResponseWrapper<T> failed(String message) {
        return ResponseWrappers.build(singletonBizStatusEnum().failed().getCode(), message);
    }

    public static final <T> ResponseWrapper<T> failed(T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().failed().getCode(), singletonBizStatusEnum().failed().getMessage(), data);
    }

    public static final <T> ResponseWrapper<T> failed(String message, T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().failed().getCode(), message, data);
    }





    public static final <T> ResponseWrapper<T> exception() {
        return ResponseWrappers.build(singletonBizStatusEnum().exception().getCode(), singletonBizStatusEnum().exception().getMessage());
    }

    public static final <T> ResponseWrapper<T> exception(String message) {
        return ResponseWrappers.build(singletonBizStatusEnum().exception().getCode(), message);
    }

    public static final <T> ResponseWrapper<T> exception(T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().exception().getCode(), singletonBizStatusEnum().exception().getMessage(), data);
    }

    public static final <T> ResponseWrapper<T> exception(String message, T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().exception().getCode(), message, data);
    }





    public static final <T> ResponseWrapper<T> invalid() {
        return ResponseWrappers.build(singletonBizStatusEnum().invalid().getCode(), singletonBizStatusEnum().invalid().getMessage());
    }

    public static final <T> ResponseWrapper<T> invalid(String message) {
        return ResponseWrappers.build(singletonBizStatusEnum().invalid().getCode(), message);
    }

    public static final <T> ResponseWrapper<T> invalid(T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().invalid().getCode(), singletonBizStatusEnum().invalid().getMessage(), data);
    }

    public static final <T> ResponseWrapper<T> invalid(String message, T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().invalid().getCode(), message, data);
    }





    public static final <T> ResponseWrapper<T> unauthorized() {
        return ResponseWrappers.build(singletonBizStatusEnum().unauthorized().getCode(), singletonBizStatusEnum().unauthorized().getMessage());
    }

    public static final <T> ResponseWrapper<T> unauthorized(String message) {
        return ResponseWrappers.build(singletonBizStatusEnum().unauthorized().getCode(), message);
    }

    public static final <T> ResponseWrapper<T> unauthorized(T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().unauthorized().getCode(), singletonBizStatusEnum().unauthorized().getMessage(), data);
    }

    public static final <T> ResponseWrapper<T> unauthorized(String message, T data) {
        return ResponseWrappers.build(singletonBizStatusEnum().unauthorized().getCode(), message, data);
    }

    public static final void fillSpanIdIntoResponseEntity(Object responseEntity, String spanId) {
        if (responseEntity instanceof ResponseWrapper) {
            ((ResponseWrapper) responseEntity).setSpanId(spanId);
        }
    }
}
