package com.aio.portable.swiss.suite.eventbus.refer.exception;

import com.aio.portable.swiss.hamlet.bean.BizStatusOriginEnum;
import com.aio.portable.swiss.hamlet.exception.BizException;

import java.text.MessageFormat;

public class NotExistEventNamespaceException extends BizException {
//    public NotExistEventNamespaceException() {}
    public NotExistEventNamespaceException(String namespace) {
        super(BizStatusOriginEnum.staticFailed().getCode(), MessageFormat.format("namespace {0} is not exist.", namespace));
    }
}
