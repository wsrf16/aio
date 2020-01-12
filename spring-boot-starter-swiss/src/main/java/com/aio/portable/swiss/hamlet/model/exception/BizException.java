package com.aio.portable.swiss.hamlet.model.exception;

public class BizException extends RuntimeException {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int code;
    private String message;

    public BizException(int code, String message){
        this.code = code;
        this.message = message;
    }
}
