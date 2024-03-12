package com.aio.portable.swiss.hamlet.interceptor;

import com.aio.portable.swiss.hamlet.bean.BaseBizStatusEnum;
import com.aio.portable.swiss.hamlet.bean.RequestRecord;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.hamlet.exception.BizException;
import com.aio.portable.swiss.hamlet.exception.BusinessException;
import com.aio.portable.swiss.hamlet.exception.HandOverException;
import com.aio.portable.swiss.hamlet.interceptor.log.Slf4JLogHubFactory;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

//@RestControllerAdvice
public abstract class HamletExceptionAdvice {

    private static final String GLOBAL_BUSINESS_EXCEPTION = "全局业务异常拦截";
    private static final String GLOBAL_SYSTEM_EXCEPTION = "全局系统异常拦截";
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
        log = LogHubFactory.isInitial() ?
                LogHubFactory.staticBuild(this.getClass()) : Slf4JLogHubFactory.staticBuild(this.getClass());
//        this.setLogEnabled(false);
    }

    public HamletExceptionAdvice(LogHubFactory logHubFactory) {
        log = logHubFactory.build(this.getClass());
//        this.setLogEnabled(false);
    }


    protected Class<? extends Throwable> getBusinessExceptionClass() {
        return BizException.class;
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseWrapper handleBizException(Exception input) {
        ResponseWrapper responseWrapper;

        if (input instanceof HandOverException) {
            responseWrapper = buildResponseWrapper(input.getCause());
            HandOverException instance = (HandOverException) input;
            String spanId = instance.getSpanId();
            RequestRecord requestRecord = instance.getRequestRecord();
            responseWrapper.setSpanId(spanId);
            log.e(GLOBAL_BUSINESS_EXCEPTION, requestRecord, input);
        } else if (input instanceof NoHandlerFoundException) {
            responseWrapper = buildResponseWrapper(input);
            log.e(GLOBAL_BUSINESS_EXCEPTION, input);
        } else {
            responseWrapper = buildResponseWrapper(input);
            log.e(GLOBAL_BUSINESS_EXCEPTION, input);
        }

        return responseWrapper;
    }

    private ResponseWrapper buildResponseWrapper(Throwable input) {
        ResponseWrapper responseWrapper;
        if (getBusinessExceptionClass().isInstance(input)) {
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
