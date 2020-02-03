package com.aio.portable.swiss.hamlet.bean;

public class ResponseWrapperUtils extends ResponseWrapper {

    public static <T> ResponseWrapper<T> succeed() {
        return ResponseWrapper.build(BizStatusEnum.SUCCEED.getCode(), "success");
    }

    public static <T> ResponseWrapper<T> succeed(T data) {
        return ResponseWrapper.build(BizStatusEnum.SUCCEED.getCode(), "success", data);
    }
}
