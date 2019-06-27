package com.york.portable.swiss.hamlet;

import com.york.portable.swiss.assist.log.hub.LoggerHubImp;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubPool;
import com.york.portable.swiss.hamlet.model.BizStatusEnum;
import com.york.portable.swiss.hamlet.model.ResponseWrapper;
import com.york.portable.swiss.hamlet.model.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    public ExceptionAdvice(LoggerHubFactory loggerHubFactory) {
        loggerPool = LoggerHubPool.newInstance(loggerHubFactory);
    }

    protected static LoggerHubPool loggerPool;

    @ExceptionHandler(value = {BizException.class})
    public ResponseWrapper handleBizException(BizException e) {
        LoggerHubImp logger = loggerPool.putByExceptionIfAbsent(e);
        if (logger != null) {
            logger.e("全局业务异常拦截", e);
        }
        ResponseWrapper responseWrapper = ResponseWrapper.build(e.getCode(), e.toString());
        return responseWrapper;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapper handleException(Exception e) {
        LoggerHubImp logger = loggerPool.putByExceptionIfAbsent(e);
        if (logger != null) {
            logger.e("全局系统异常拦截", e);
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
