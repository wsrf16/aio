package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistEventListenerException extends BizException {
//    public NotExistEventListenerException() {}
    public NotExistEventListenerException(String listen) {
        super(BizStatusEnum.FAILED.getCode(), MessageFormat.format("listener {0} is not exist.", listen));
    }
}
