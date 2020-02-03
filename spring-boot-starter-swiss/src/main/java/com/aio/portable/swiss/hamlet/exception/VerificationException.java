package com.aio.portable.swiss.hamlet.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;

public class VerificationException extends BizException {

    public VerificationException() {
        super(BizStatusEnum.UNAUTHORIZED.getCode(), BizStatusEnum.UNAUTHORIZED.getMessage());
    }

    public VerificationException(String message) {
        super(BizStatusEnum.UNAUTHORIZED.getCode(), message);
    }
}
