package com.aio.portable.swiss.hamlet.interceptor.classic;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.hamlet.exception.BusinessException;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.request.RequestRecord;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.request.RequestRecordSession;
import com.aio.portable.swiss.sugar.ThrowableSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.text.MessageFormat;

//@RestControllerAdvice
public abstract class HamletExceptionAdvice {

    private static final String GLOBAL_BUSINESS_EXCEPTION = "GlobalBusinessException";
    private static final String GLOBAL_SYSTEM_EXCEPTION = "GlobalSystemException";
    protected final LogHub log;

    @Autowired(required = false)
    BaseBizStatusEnum baseBizStatusEnum;

    public BaseBizStatusEnum getBizStatusEnum() {
        return baseBizStatusEnum == null ? BaseBizStatusEnum.getSingleton() : baseBizStatusEnum;
    }

    public void setLogEnabled(boolean enabled) {
        log.setEnabled(enabled);
    }

    public HamletExceptionAdvice() {
        log = LogHubFactory.staticBuild(this.getClass());
//        this.setLogEnabled(false);
    }

    public HamletExceptionAdvice(LogHubFactory logHubFactory) {
        log = logHubFactory.build(this.getClass());
//        this.setLogEnabled(false);
    }


    protected Class<? extends Throwable> getBusinessExceptionClass() {
        return BizException.class;
    }

    protected boolean beBusinessException(Throwable e) {
        return getBusinessExceptionClass().isInstance(e);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseWrapper handleBizException(Exception input) {
        ResponseWrapper responseWrapper;

//        if (input instanceof HandOverException) {
//            responseWrapper = buildResponseWrapper(input.getCause());
//            HandOverException instance = (HandOverException) input;
//            String spanId = instance.getSpanId();
//            RequestRecord requestRecord = instance.getRequestRecord();
//            responseWrapper.setSpanId(spanId);
//            log.e(GLOBAL_BUSINESS_EXCEPTION, requestRecord, input);
//        } else if (input instanceof NoHandlerFoundException) {
//            responseWrapper = buildResponseWrapper(input);
//            log.e(GLOBAL_BUSINESS_EXCEPTION, input);
//        } else {
//            responseWrapper = buildResponseWrapper(input);
//            log.e(GLOBAL_BUSINESS_EXCEPTION, input);
//        }
        String spanId = RequestRecordSession.getSpanId();

        Throwable e = ThrowableSugar.getRootCause(input);
        RequestRecord requestRecord = RequestRecordSession.getRequestRecord();
        if (beBusinessException(e)) {
            responseWrapper = buildResponseWrapper(e);
            responseWrapper.setSpanId(spanId);
            if (RequestRecordSession.getException() == null)
                log.e(MessageFormat.format("{0}({1})", GLOBAL_BUSINESS_EXCEPTION, spanId), requestRecord, e);
        } else if (e instanceof NoHandlerFoundException) {
            responseWrapper = buildResponseWrapper(e);
            responseWrapper.setSpanId(spanId);
            if (RequestRecordSession.getException() == null)
                log.e(MessageFormat.format("{0}({1})", GLOBAL_SYSTEM_EXCEPTION, spanId), requestRecord, e);
        } else {
            responseWrapper = buildResponseWrapper(e);
            responseWrapper.setSpanId(spanId);
            if (RequestRecordSession.getException() == null)
                log.e(MessageFormat.format("{0}({1})", GLOBAL_SYSTEM_EXCEPTION, spanId), requestRecord, e);
        }
        RequestRecordSession.remove();

        return responseWrapper;
    }

    private ResponseWrapper buildResponseWrapper(Throwable input) {
        ResponseWrapper responseWrapper;
        if (beBusinessException(input)) {
            BusinessException e = (BusinessException) input;
            responseWrapper = ResponseWrappers.build(e.getCode(), e.getMessage());
        } else if (input instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) input;
            responseWrapper = ResponseWrappers.build(getBizStatusEnum().staticInvalid().getCode(), e.getBindingResult().getAllErrors());
        } else {
            responseWrapper = ResponseWrappers.build(getBizStatusEnum().staticException().getCode(), getBizStatusEnum().staticException().getMessage());
        }
        return responseWrapper;
    }




}
