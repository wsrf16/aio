package com.aio.portable.swiss.hamlet.bean;

import com.aio.portable.swiss.autoconfigure.properties.Swagger2Properties;

public enum BizStatusEnum implements Swagger2Properties.SwaggerStatus {
    SUCCEED(0, "请求成功"),
    FAILED(1, "请求失败"),
    EXCEPTION(2, "请求异常"),
    INVALID(3, "请求无效"),
    UNAUTHORIZED(4, "无效授权"),
    ;


    BizStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
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

    private int code;
    private String message;
}
