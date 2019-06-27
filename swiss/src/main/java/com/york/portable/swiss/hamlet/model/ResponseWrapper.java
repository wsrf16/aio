package com.york.portable.swiss.hamlet.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@ApiModel("返回实体")
public class ResponseWrapper<T> {
    /**
     * 作为一次请求的唯一标识，用于问题定位（不需赋值）
     */
    @ApiModelProperty("返回内容id")
    private String uniqueId;

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

    /**
     * 标识一次请求的返回时间（不需赋值）
     */
    @ApiModelProperty("时间戳")
    private Long timeStamp;


    protected ResponseWrapper() {
        this.uniqueId = UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
        this.timeStamp = System.currentTimeMillis();
    }

    protected ResponseWrapper(int code, String message, T data) {
        this.uniqueId = UUID.randomUUID().toString().replace("-", StringUtils.EMPTY);
        this.code = code;
        this.message = message;
        this.data = data;
        this.timeStamp = System.currentTimeMillis();
    }

    /**
     * 默认返回实体
     * @return
     */
    public static ResponseWrapper build() {
        return new ResponseWrapper();
    }

    /**
     * 创建返回实体（返回文字消息）
     * @param statusCode
     * @param message
     * @return
     */
    public static ResponseWrapper build(int statusCode, String message) {
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
     */
    public void set(int statusCode, String message) {
        setCode(statusCode);
        setMessage(message);
    }

    /**
     * 设置“状态码”和“数据”
     * @param statusCode
     * @param data
     */
    public void set(int statusCode, T data) {
        setCode(statusCode);
        setData(data);
    }

    /**
     * 设置“状态码”、“文字消息”和“数据”
     * @param statusCode
     * @param message
     * @param data
     */
    public void set(int statusCode, String message, T data) {
        setCode(statusCode);
        setMessage(message);
        setData(data);
    }


    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

}
