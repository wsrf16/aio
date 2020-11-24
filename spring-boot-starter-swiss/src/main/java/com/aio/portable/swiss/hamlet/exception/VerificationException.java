package com.aio.portable.swiss.hamlet.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusNativeEnum;

public class VerificationException extends BizException {

    public VerificationException() {
        super(BizStatusNativeEnum.staticUnauthorized().getCode(), BizStatusNativeEnum.staticUnauthorized().getMessage());
    }

    public VerificationException(String message) {
        super(BizStatusNativeEnum.staticUnauthorized().getCode(), message);
    }
}
