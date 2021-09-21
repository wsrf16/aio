package com.aio.portable.swiss.hamlet.exception;

public class BizException extends RuntimeException implements BusinessException {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

//    @Override
//    public String getMessage() {
//        return message;
//    }

//    public void setMessage(String message) {
//        this.message = message;
//    }

    private int code;
//    private String message;

    public BizException(int code, String message){
        super(message);
        this.code = code;
    }

    public BizException(int code, Throwable cause){
        super(cause);
        this.code = code;
    }

    public BizException(int code, String message, Throwable cause){
        super(message, cause);
        this.code = code;
    }


}
