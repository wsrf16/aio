package com.aio.portable.swiss.hamlet.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusOriginEnum;

public class VerificationException extends BizException {

    public VerificationException() {
        super(BizStatusOriginEnum.staticUnauthorized().getCode(), BizStatusOriginEnum.staticUnauthorized().getMessage());
    }

    public VerificationException(String message) {
        super(BizStatusOriginEnum.staticUnauthorized().getCode(), message);
    }
}
