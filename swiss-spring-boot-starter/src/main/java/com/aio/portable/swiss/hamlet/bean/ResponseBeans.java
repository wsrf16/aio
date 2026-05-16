package com.aio.portable.swiss.hamlet.bean;

import com.aio.portable.swiss.hamlet.interceptor.classic.log.request.RequestRecord;
import com.aio.portable.swiss.sugar.meta.ClassSugar;

import java.util.function.Supplier;

public abstract class ResponseBeans {
//    private static BaseResponseWrapper<?> responseWrapper;
//    private static Class<? extends ResponseWrapper> clazz = ClassicResponseWrapper.class;
//
//    private static <T> ResponseWrapper<T> instance() {
//        ResponseWrapper<T> responseWrapper = ClassSugar.newInstance(clazz);
//        return responseWrapper;
//    }
//

    public static ResponseStatuses getSingletonStatuses() {
        return ResponseStatuses.getSingleton();
    }

    // 注入status和response
    private static Supplier<ResponseBean> builder = ClassicResponseBean::new;

    public static void setBuilder(Supplier<ResponseBean> builder) {
        ResponseBeans.builder = builder;
    }

    public static void setBuilder(Class<? extends ResponseBean> clazz) {
        ResponseBeans.setBuilder(() -> ClassSugar.newInstance(clazz));
    }

    /**
     * 默认返回实体
     * @return
     */
    public static <T> ResponseBean<T> build() {
        return builder.get();
    }

    /**
     * 创建返回实体（返回文字消息）
     * @param statusCode
     * @param message
     * @return
     */
    public static <T> ResponseBean<T> build(int statusCode, String message) {
        ResponseBean<T> responseBean = build();
        responseBean.setCode(statusCode);
        responseBean.setMessage(message);
        return responseBean;
    }

    /**
     * 创建返回实体（返回数据）
     * @param statusCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> build(int statusCode, T data) {
        ResponseBean<T> responseBean = build();
        responseBean.setCode(statusCode);
        responseBean.setData(data);
        return responseBean;
    }

    /**
     * 创建返回实体（返回文字消息和数据）
     * @param statusCode
     * @param message
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseBean<T> build(int statusCode, String message, T data) {
        ResponseBean<T> responseBean = build();
        responseBean.setCode(statusCode);
        responseBean.setMessage(message);
        responseBean.setData(data);
        return responseBean;
    }

    public static <T> ResponseBean<T> succeed() {
        return ResponseBeans.build(
                getSingletonStatuses().succeed().getCode(),
                getSingletonStatuses().succeed().getMessage()
        );
    }

    public static <T> ResponseBean<T> succeed(T data) {
        return ResponseBeans.build(
                getSingletonStatuses().succeed().getCode(),
                getSingletonStatuses().succeed().getMessage(),
                data
        );
    }





    public static <T> ResponseBean<T> failed() {
        return ResponseBeans.build(
                getSingletonStatuses().failed().getCode(),
                getSingletonStatuses().failed().getMessage()
        );
    }

    public static <T> ResponseBean<T> failed(String message) {
        return ResponseBeans.build(
                getSingletonStatuses().failed().getCode(),
                message
        );
    }

    public static <T> ResponseBean<T> failed(T data) {
        return ResponseBeans.build(
                getSingletonStatuses().failed().getCode(),
                getSingletonStatuses().failed().getMessage(),
                data
        );
    }

    public static <T> ResponseBean<T> failed(String message, T data) {
        return ResponseBeans.build(
                getSingletonStatuses().failed().getCode(),
                message,
                data
        );
    }





    public static <T> ResponseBean<T> exception() {
        return ResponseBeans.build(
                getSingletonStatuses().exception().getCode(),
                getSingletonStatuses().exception().getMessage()
        );
    }

    public static <T> ResponseBean<T> exception(String message) {
        return ResponseBeans.build(
                getSingletonStatuses().exception().getCode(),
                message
        );
    }

    public static <T> ResponseBean<T> exception(T data) {
        return ResponseBeans.build(
                getSingletonStatuses().exception().getCode(),
                getSingletonStatuses().exception().getMessage(),
                data
        );
    }

    public static <T> ResponseBean<T> exception(String message, T data) {
        return ResponseBeans.build(
                getSingletonStatuses().exception().getCode(),
                message,
                data
        );
    }





    public static <T> ResponseBean<T> invalid() {
        return ResponseBeans.build(
                getSingletonStatuses().invalid().getCode(),
                getSingletonStatuses().invalid().getMessage()
        );
    }

    public static <T> ResponseBean<T> invalid(String message) {
        return ResponseBeans.build(
                getSingletonStatuses().invalid().getCode(),
                message
        );
    }

    public static <T> ResponseBean<T> invalid(T data) {
        return ResponseBeans.build(
                getSingletonStatuses().invalid().getCode(),
                getSingletonStatuses().invalid().getMessage(),
                data
        );
    }

    public static <T> ResponseBean<T> invalid(String message, T data) {
        return ResponseBeans.build(
                getSingletonStatuses().invalid().getCode(),
                message,
                data
        );
    }





    public static <T> ResponseBean<T> unauthorized() {
        return ResponseBeans.build(
                getSingletonStatuses().unauthorized().getCode(),
                getSingletonStatuses().unauthorized().getMessage()
        );
    }

    public static <T> ResponseBean<T> unauthorized(String message) {
        return ResponseBeans.build(
                getSingletonStatuses().unauthorized().getCode(),
                message
        );
    }

    public static <T> ResponseBean<T> unauthorized(T data) {
        return ResponseBeans.build(
                getSingletonStatuses().unauthorized().getCode(),
                getSingletonStatuses().unauthorized().getMessage(),
                data
        );
    }

    public static <T> ResponseBean<T> unauthorized(String message, T data) {
        return ResponseBeans.build(
                getSingletonStatuses().unauthorized().getCode(),
                message,
                data
        );
    }

    public static void fillSpanId(Object responseEntity, String spanId) {
        if (responseEntity instanceof ResponseBean) {
            ((ResponseBean<?>) responseEntity).setSpanId(spanId);
        }
    }

    public static void fillHeader(Object responseEntity, RequestRecord requestRecord) {
        if (responseEntity instanceof ResponseBean) {
            ((ResponseBean<?>) responseEntity).setSpanId(requestRecord.getSpanId());
            ((ResponseBean<?>) responseEntity).setTraceId(requestRecord.getTraceId());
        }
    }
}
