package com.aio.portable.swiss.hamlet.model;

public enum BizStatusEnum {
    SUCCEED(0, "请求成功"),
    FAILED(1, "请求失败"),
    EXCEPTION(2, "请求异常"),
    INVALID(3, "请求格式不正确"),
    UNAUTHORIZED(4, "未授权"),
    ;


    BizStatusEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private int code;
    private String description;
}
