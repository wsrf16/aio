package com.aio.portable.swiss.hamlet.interceptor.classic;

import com.aio.portable.swiss.hamlet.bean.ResponseBean;
import com.aio.portable.swiss.hamlet.bean.ResponseBeans;
import com.aio.portable.swiss.hamlet.bean.ResponseStatuses;
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

import java.text.MessageFormat;

//@RestControllerAdvice
public abstract class HamletExceptionAdvice {

    private static final String GLOBAL_BUSINESS_EXCEPTION = "GlobalBusinessException";
    private static final String GLOBAL_SYSTEM_EXCEPTION = "GlobalSystemException";
    protected final LogHub log;

    @Autowired(required = false)
    ResponseStatuses responseStatuses;

    public ResponseStatuses getBizStatusEnum() {
        return responseStatuses != null ? responseStatuses : ResponseStatuses.getSingleton();
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
    public ResponseBean<?> handleBizException(Exception input) {
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


        Throwable e = ThrowableSugar.getRootCause(input);
        RequestRecord requestRecord = RequestRecordSession.getRequestRecord();

        ResponseBean<?> responseBean = requestRecord != null ? buildResponseWrapper(e, requestRecord.getSpanId()) : buildResponseWrapper(e);

//        String spanId = responseBean.getSpanId();
        String globalId = requestRecord.getGlobalId();
        log.e(MessageFormat.format("{0}({1})", GLOBAL_BUSINESS_EXCEPTION, globalId), requestRecord, e);

        RequestRecordSession.remove();
        return responseBean;
    }

    private ResponseBean<?> buildResponseWrapper(Throwable input) {
        ResponseBean<?> responseBean;
        if (beBusinessException(input)) {
            BusinessException e = (BusinessException) input;
            responseBean = ResponseBeans.build(e.getCode(), e.getMessage());
        } else if (input instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException e = (MethodArgumentNotValidException) input;
            responseBean = ResponseBeans.build(getBizStatusEnum().staticInvalid().getCode(), e.getBindingResult().getAllErrors());
        } else {
            responseBean = ResponseBeans.build(getBizStatusEnum().staticException().getCode(), getBizStatusEnum().staticException().getMessage());
        }
        return responseBean;
    }

    private ResponseBean<?> buildResponseWrapper(Throwable input, String spanId) {
        ResponseBean<?> responseBean = buildResponseWrapper(input);
        if (spanId != null)
            responseBean.setSpanId(spanId);
        return responseBean;
    }




}
