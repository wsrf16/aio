package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistSubscriberException extends BizException {
//    public NotExistSubscriberException() {}
    public NotExistSubscriberException(String subscriber) {
        super(BizStatusEnum.FAILED.getCode(), MessageFormat.format("subscriber {0} is not exist.", subscriber));
    }
}
