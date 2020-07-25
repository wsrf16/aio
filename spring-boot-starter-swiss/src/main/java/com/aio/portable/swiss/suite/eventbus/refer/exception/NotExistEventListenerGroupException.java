package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistEventListenerGroupException extends BizException {
//    public NotExistEventListenerGroupException() {}
    public NotExistEventListenerGroupException(String group) {
        super(BizStatusEnum.FAILED.getCode(), MessageFormat.format("event-group {0} is not exist.", group));
    }
}
