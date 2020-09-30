package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistEventNamespaceException extends BizException {
//    public NotExistEventGroupException() {}
    public NotExistEventNamespaceException(String namespace) {
        super(BizStatusEnum.FAILED.getCode(), MessageFormat.format("namespace {0} is not exist.", namespace));
    }
}
