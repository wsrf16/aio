package com.aio.portable.swiss.hamlet.model.exception;

import com.aio.portable.swiss.hamlet.model.BizStatusEnum;

public class VerificationException extends BizException {

    public VerificationException() {
        super(BizStatusEnum.UNAUTHORIZED.getCode(), BizStatusEnum.UNAUTHORIZED.getDescription());
    }

    public VerificationException(String message) {
        super(BizStatusEnum.UNAUTHORIZED.getCode(), message);
    }
}
