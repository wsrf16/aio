package com.york.portable.swiss.hamlet.model.exception;

import com.york.portable.swiss.hamlet.model.BizStatusEnum;

public class VerificationException extends BizException {

    public VerificationException() {
        super(BizStatusEnum.VERIFICATION.getCode(), BizStatusEnum.VERIFICATION.getDescription());
    }

    public VerificationException(String message) {
        super(BizStatusEnum.VERIFICATION.getCode(), message);
    }
}
