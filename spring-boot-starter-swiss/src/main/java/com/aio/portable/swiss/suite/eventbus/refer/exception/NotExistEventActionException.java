package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistEventActionException extends BizException {
//    public NotExistEventactionException() {}
    public NotExistEventActionException(String action) {
        super(BizStatusEnum.FAILED.getCode(), MessageFormat.format("action {0} is not exist.", action));
    }
}
