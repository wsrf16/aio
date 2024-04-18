package com.aio.portable.swiss.hamlet.bean;

import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

//@ApiModel("返回实体")
public class ClassicResponseWrapper<T> implements ResponseWrapper<T> {
//    protected static ResponseWrapper singleton = new ResponseWrapper();

    /**
     * 作为一次请求的唯一标识，用于问题定位（不需手动赋值）
     */
    @ApiModelProperty("唯一id")
    private String spanId;

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
    @JsonFormat(pattern="yyyy/MM/dd HH:mm:ss.SSSz", timezone="GMT+8")
    private Date accessTime;

    /**
     * 标识一次请求的返回时间（不需赋值）
     */
    @ApiModelProperty("时间戳")
    private Long timestamp;


    public ClassicResponseWrapper() {
        this.spanId = IDS.uuid();
        Date date = new Date();
        this.accessTime = date;
        this.timestamp = date.getTime();
    }

    public ClassicResponseWrapper(int code, String message, T data) {
        this.spanId = IDS.uuid();
        this.code = code;
        this.message = message;
        this.data = data;
        this.accessTime = new Date();
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 设置“状态码”和“文字消息”
     * @param statusCode
     * @param message
     * @return
     */
    public ClassicResponseWrapper<T> set(int statusCode, String message) {
        return this.setCode(statusCode)
                .setMessage(message);
    }

    /**
     * 设置“状态码”和“数据”
     * @param statusCode
     * @param data
     * @return
     */
    public ClassicResponseWrapper<T> set(int statusCode, T data) {
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
    public ClassicResponseWrapper<T> set(int statusCode, String message, T data) {
        return this.setCode(statusCode)
                .setMessage(message)
                .setData(data);
    }


    public String getSpanId() {
        return spanId;
    }

    public ResponseWrapper<T> setSpanId(String spanId) {
        this.spanId = spanId;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ClassicResponseWrapper<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ClassicResponseWrapper<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public ClassicResponseWrapper<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Date getAccessTime() {
        return accessTime;
    }

    public ClassicResponseWrapper<T> setAccessTime(Date accessTime) {
        this.accessTime = accessTime;
        return this;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public ClassicResponseWrapper<T> setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
