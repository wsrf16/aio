package com.york.portable.swiss.hamlet.interceptor;

import com.york.portable.swiss.assist.log.hub.LogHub;
import com.york.portable.swiss.assist.log.hub.factory.LogHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.LogHubPool;
import com.york.portable.swiss.hamlet.model.BizStatusEnum;
import com.york.portable.swiss.hamlet.model.ResponseWrapper;
import com.york.portable.swiss.hamlet.model.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//@RestControllerAdvice
public class HamletExceptionAdvice {

    private static final String GLOBAL_BUSINESS_EXCEPTION = "全局业务异常拦截";
    private static final String GLOBAL_SYSTEM_EXCEPTION = "全局系统异常拦截";

    public HamletExceptionAdvice(LogHubFactory logHubFactory) {
        loggerPool = LogHubPool.getSingleton(logHubFactory);
    }

    protected static LogHubPool loggerPool;

    @ExceptionHandler(value = {BizException.class})
    public ResponseWrapper handleBizException(BizException e) {
        LogHub logger = loggerPool.putIfAbsent(e);
        if (logger != null) {
            logger.e(GLOBAL_BUSINESS_EXCEPTION, e);
        }
        ResponseWrapper responseWrapper = ResponseWrapper.build(e.getCode(), e.toString());
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
//                        ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.toString())
//                        : ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.toString());
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException)
            responseWrapper = ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.toString());
        else if (e instanceof MethodArgumentNotValidException)
            responseWrapper = ResponseWrapper.build(BizStatusEnum.PARAM_INVALID.getCode(), ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors());
        else
            responseWrapper = ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.toString());

        return responseWrapper;
    }


}
