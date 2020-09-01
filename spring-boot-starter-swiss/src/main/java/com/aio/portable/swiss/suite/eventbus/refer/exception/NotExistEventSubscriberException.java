package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistEventSubscriberException extends BizException {
//    public NotExistEventSubscriberException() {}
    public NotExistEventSubscriberException(String listen) {
        super(BizStatusEnum.FAILED.getCode(), MessageFormat.format("subscriber {0} is not exist.", listen));
    }
}