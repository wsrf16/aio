package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistEventGroupException extends BizException {
//    public NotExistEventGroupException() {}
    public NotExistEventGroupException(String group) {
        super(BizStatusEnum.FAILED.getCode(), MessageFormat.format("group {0} is not exist.", group));
    }
}
