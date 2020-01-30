package com.aio.portable.swiss.hamlet.interceptor;

import com.aio.portable.swiss.structure.log.base.LogHub;
import com.aio.portable.swiss.structure.log.base.factory.LogHubFactory;
import com.aio.portable.swiss.structure.log.base.factory.LogHubPool;
import com.aio.portable.swiss.hamlet.model.BizStatusEnum;
import com.aio.portable.swiss.hamlet.model.ResponseWrapper;
import com.aio.portable.swiss.hamlet.model.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//@RestControllerAdvice
public abstract class HamletExceptionAdvice {

    private final static String GLOBAL_BUSINESS_EXCEPTION = "全局业务异常拦截";
    private final static String GLOBAL_SYSTEM_EXCEPTION = "全局系统异常拦截";

    public HamletExceptionAdvice(LogHubFactory logFactory) {
        loggerPool = LogHubPool.getSingleton(logFactory);
    }

    protected static LogHubPool loggerPool;

    @ExceptionHandler(value = {BizException.class})
    public ResponseWrapper handleBizException(BizException e) {
        LogHub logger = loggerPool.putIfAbsent(e);
        if (logger != null) {
            logger.e(GLOBAL_BUSINESS_EXCEPTION, e);
        }
        ResponseWrapper responseWrapper = ResponseWrapper.build(e.getCode(), e.getMessage());
        return responseWrapper;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapper handleException(Exception e) {
        LogHub logger = loggerPool.putIfAbsent(e);
        if (logger != null) {
            logger.e(GLOBAL_SYSTEM_EXCEPTION, e);
        }
        ResponseWrapper responseWrapper;
//        responseWrapper = e instanceof org.springframework.web.servlet.NoHandlerFoundException ?
//                        ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage())
//                        : ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException)
            responseWrapper = ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());
        else if (e instanceof MethodArgumentNotValidException)
            responseWrapper = ResponseWrapper.build(BizStatusEnum.INVALID.getCode(), ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors());
        else
            responseWrapper = ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());

        return responseWrapper;
    }


}
