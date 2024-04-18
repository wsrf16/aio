package com.aio.portable.swiss.hamlet.exception;

//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.TYPE)
public interface BusinessException {
    int getCode();
    String getMessage();
}
