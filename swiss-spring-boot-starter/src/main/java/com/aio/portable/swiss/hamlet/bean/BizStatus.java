package com.aio.portable.swiss.hamlet.bean;

import com.aio.portable.swiss.hamlet.exception.BizException;

public class BizStatus {
    public BizStatus(int code, String message) {
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

    public BizException buildException() {
        return new BizException(this.getCode(), this.getMessage());
    }
}
