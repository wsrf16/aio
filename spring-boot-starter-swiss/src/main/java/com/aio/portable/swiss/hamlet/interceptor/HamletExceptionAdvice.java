package com.aio.portable.swiss.hamlet.interceptor;

import com.aio.portable.swiss.hamlet.model.RequestRecord;
import com.aio.portable.swiss.hamlet.model.exception.HandOverException;
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
import org.springframework.web.servlet.NoHandlerFoundException;

import java.text.MessageFormat;

//@RestControllerAdvice
public abstract class HamletExceptionAdvice {

    private final static String GLOBAL_BUSINESS_EXCEPTION = "全局业务异常拦截";
    private final static String GLOBAL_SYSTEM_EXCEPTION = "全局系统异常拦截";

    public HamletExceptionAdvice(LogHubFactory logFactory) {
        loggerPool = LogHubPool.getSingleton(logFactory);
    }

    protected static LogHubPool loggerPool;

    @ExceptionHandler(value = {HandOverException.class})
    public ResponseWrapper handleBizException(HandOverException inpute) {
        Exception e = inpute.getException();

        ResponseWrapper responseWrapper;
        if (e instanceof BizException) {
            BizException bizException = (BizException) (e);
            responseWrapper = ResponseWrapper.build(bizException.getCode(), bizException.getMessage());
        } else if (e instanceof NoHandlerFoundException)
            responseWrapper = ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());
        else if (e instanceof MethodArgumentNotValidException)
            responseWrapper = ResponseWrapper.build(BizStatusEnum.INVALID.getCode(), ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors());
        else
            responseWrapper = ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());
        String traceId = inpute.getTraceId();
        responseWrapper.setTraceId(traceId);

        RequestRecord requestRecord = inpute.getRequestRecord();
        LogHub logger = loggerPool.putIfAbsent(e);
        if (logger != null) {
            if (e instanceof BizException)
                logger.e(MessageFormat.format("{0}({1})", GLOBAL_BUSINESS_EXCEPTION, traceId), requestRecord, e);
            else
                logger.e(MessageFormat.format("{0}({1})", GLOBAL_SYSTEM_EXCEPTION, traceId), e.getMessage(), e);
        }
        return responseWrapper;
    }


    @ExceptionHandler(value = {BizException.class})
    public ResponseWrapper handleBizException(BizException e) {
        LogHub logger = loggerPool.putIfAbsent(e);

        ResponseWrapper responseWrapper = ResponseWrapper.build(e.getCode(), e.getMessage());
        String traceId = responseWrapper.getTraceId();

        if (logger != null) {
//            logger.e(GLOBAL_BUSINESS_EXCEPTION, e);
            logger.e(MessageFormat.format("{0}({1})", GLOBAL_BUSINESS_EXCEPTION, traceId), e.getMessage(), e);
        }
        return responseWrapper;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapper handleException(Exception e) {
        ResponseWrapper responseWrapper;
//        responseWrapper = e instanceof org.springframework.web.servlet.NoHandlerFoundException ?
//                        ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage())
//                        : ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());
        if (e instanceof NoHandlerFoundException)
            responseWrapper = ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());
        else if (e instanceof MethodArgumentNotValidException)
            responseWrapper = ResponseWrapper.build(BizStatusEnum.INVALID.getCode(), ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors());
        else
            responseWrapper = ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());
        String traceId = responseWrapper.getTraceId();

        LogHub logger = loggerPool.putIfAbsent(e);
        if (logger != null) {
//            logger.e(GLOBAL_SYSTEM_EXCEPTION, e);
            logger.e(MessageFormat.format("{0}({1})", GLOBAL_SYSTEM_EXCEPTION, traceId), e.getMessage(), e);
        }
        return responseWrapper;
    }


}
