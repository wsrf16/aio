package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistEventHandlerException extends BizException {
//    public NotExistEventHandlerException() {}
    public NotExistEventHandlerException(String handler) {
        super(BizStatusEnum.FAILED.getCode(), MessageFormat.format("handler {0} is not exist.", handler));
    }
}
