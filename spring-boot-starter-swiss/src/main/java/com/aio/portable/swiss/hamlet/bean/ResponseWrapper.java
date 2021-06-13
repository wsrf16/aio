package com.aio.portable.swiss.hamlet.bean;

import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

//@ApiModel("返回实体")
public class ResponseWrapper<T> {
    /**
     * 作为一次请求的唯一标识，用于问题定位（不需赋值）
     */
    @ApiModelProperty("唯一id")
    private String traceId;

    /**
     * 返回状态码
     */
    @ApiModelProperty("返回状态码")
    private int code;

    /**
     * 返回消息内容
     */
    @ApiModelProperty("返回消息内容")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty("返回数据")
    private T data;

    @ApiModelProperty("访问时间")
    private Date accessTime;

    /**
     * 标识一次请求的返回时间（不需赋值）
     */
    @ApiModelProperty("时间戳")
    private Long timestamp;


    protected ResponseWrapper() {
        this.traceId = IDS.uuid();
        this.accessTime = new Date();
        this.timestamp = System.currentTimeMillis();
    }

    protected ResponseWrapper(int code, String message, T data) {
        this.traceId = IDS.uuid();
        this.code = code;
        this.message = message;
        this.data = data;
        this.accessTime = new Date();
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 默认返回实体
     * @return
     */
    public static <T> ResponseWrapper<T> build() {
        return new ResponseWrapper();
    }

    /**
     * 创建返回实体（返回文字消息）
     * @param statusCode
     * @param message
     * @return
     */
    public static <T> ResponseWrapper<T> build(int statusCode, String message) {
        return new ResponseWrapper<>(statusCode, message, null);
    }

    /**
     * 创建返回实体（返回数据）
     * @param statusCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseWrapper<T> build(int statusCode, T data) {
        return new ResponseWrapper<>(statusCode, null, data);
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
        return new ResponseWrapper<>(statusCode, message, data);
    }

//    /**
//     * 创建“成功”返回实体
//     * @param data
//     * @param <T>
//     * @return
//     */
//    public static <T> ResponseWrapper<T> success(T data) {
//        return new ResponseWrapper<>(0, "success", data);
//    }

    /**
     * 设置“状态码”和“文字消息”
     * @param statusCode
     * @param message
     * @return
     */
    public ResponseWrapper<T> set(int statusCode, String message) {
        return this.setCode(statusCode)
                .setMessage(message);
    }

    /**
     * 设置“状态码”和“数据”
     * @param statusCode
     * @param data
     * @return
     */
    public ResponseWrapper<T> set(int statusCode, T data) {
        return this.setCode(statusCode)
                .setData(data);
    }

    /**
     * 设置“状态码”、“文字消息”和“数据”
     * @param statusCode
     * @param message
     * @param data
     * @return
     */
    public ResponseWrapper<T> set(int statusCode, String message, T data) {
        return this.setCode(statusCode)
                .setMessage(message)
                .setData(data);
    }


    public String getTraceId() {
        return traceId;
    }

    public ResponseWrapper<T> setTraceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseWrapper<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResponseWrapper<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseWrapper<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public ResponseWrapper<T> setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public ResponseWrapper<T> setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

}
