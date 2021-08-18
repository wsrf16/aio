package com.aio.portable.swiss.hamlet.interceptor;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.hamlet.exception.HandOverException;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.factory.Slf4JLogHubFactory;
import com.aio.portable.swiss.suite.log.impl.slf4j.Slf4JLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

//@RestControllerAdvice
public abstract class HamletExceptionAdvice {

    private final static String GLOBAL_BUSINESS_EXCEPTION = "全局业务异常拦截";
    private final static String GLOBAL_SYSTEM_EXCEPTION = "全局系统异常拦截";
    protected final LogHub log;

    @Autowired(required = false)
    BaseBizStatusEnum baseBizStatusEnum;

    public BaseBizStatusEnum getBizStatusEnum() {
        return baseBizStatusEnum == null ? BaseBizStatusEnum.singletonInstance() : baseBizStatusEnum;
    }

    public HamletExceptionAdvice() {
        log = LogHubFactory.isInitial() ?
                LogHubFactory.staticBuild(getClass()) : Slf4JLogHubFactory.staticBuild(getClass());
    }

    public HamletExceptionAdvice(LogHubFactory logHubFactory) {
        log = logHubFactory.build(getClass());
    }

//    protected static LogHubPool loggerPool;

    @ExceptionHandler(value = {Exception.class})
    public ResponseWrapper handleBizException(Exception input) {
        Exception e = input instanceof HandOverException ? ((HandOverException)input).getException() : input;

        ResponseWrapper responseWrapper;
        if (e instanceof BizException) {
            BizException bizException = (BizException)e;
            log.e(GLOBAL_BUSINESS_EXCEPTION, e);
            responseWrapper = ResponseWrapper.build(bizException.getCode(), bizException.getMessage());
        }
//        else if (Arrays.asList(e.getClass().getAnnotations()).contains(BusinessException.class)){
//            log.e(GLOBAL_BUSINESS_EXCEPTION, e);
//            responseWrapper = ResponseWrapper.build(BizStatusNativeEnum.getCode(), bizException.getMessage());
//        }
        else if (e instanceof MethodArgumentNotValidException)
            responseWrapper = ResponseWrapper.build(getBizStatusEnum().staticInvalid().getCode(), ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors());
        else if (e instanceof NoHandlerFoundException)
            responseWrapper = ResponseWrapper.build(getBizStatusEnum().staticException().getCode(), e.getMessage());
        else {
            log.e(GLOBAL_SYSTEM_EXCEPTION, e);
            responseWrapper = ResponseWrapper.build(getBizStatusEnum().staticException().getCode(), getBizStatusEnum().staticException().getMessage());
        }

        if (input instanceof HandOverException) {
            String traceId = ((HandOverException) input).getTraceId();
            responseWrapper.setTraceId(traceId);
        }
        return responseWrapper;
    }


//    @ExceptionHandler(value = {BizException.class})
//    public ResponseWrapper handleBizException(BizException e) {
//        LogHub logger = loggerPool.putIfAbsent(e);
//
//        ResponseWrapper responseWrapper = ResponseWrapper.build(e.getCode(), e.getMessage());
//        String traceId = responseWrapper.getTraceId();
//
//        if (logger != null) {
//            logger.e(MessageFormat.format("{0}({1})", GLOBAL_BUSINESS_EXCEPTION, traceId), e.getMessage(), e);
//        }
//        return responseWrapper;
//    }

//    @ExceptionHandler(value = {Exception.class})
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseWrapper handleException(Exception e) {
//        ResponseWrapper responseWrapper;
////        responseWrapper = e instanceof org.springframework.web.servlet.NoHandlerFoundException ?
////                        ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage())
////                        : ResponseWrapper.build(BizStatusEnum.EXCEPTION.getCode(), e.getMessage());
//        if (e instanceof NoHandlerFoundException)
//            responseWrapper = ResponseWrapper.build(httpResponseStatus.exception().getCode(), e.getMessage());
//        else if (e instanceof MethodArgumentNotValidException)
//            responseWrapper = ResponseWrapper.build(httpResponseStatus.invalid().getCode(), ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors());
//        else
//            responseWrapper = ResponseWrapper.build(httpResponseStatus.exception().getCode(), httpResponseStatus.exception());
//        String traceId = responseWrapper.getTraceId();
//
//        LogHub logger = loggerPool.putIfAbsent(e);
//        if (logger != null) {
////            logger.e(GLOBAL_SYSTEM_EXCEPTION, e);
//            logger.e(MessageFormat.format("{0}({1})", GLOBAL_SYSTEM_EXCEPTION, traceId), e.getMessage(), e);
//        }
//        return responseWrapper;
//    }


}
