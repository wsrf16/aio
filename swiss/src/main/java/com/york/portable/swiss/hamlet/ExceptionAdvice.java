package com.york.portable.swiss.hamlet;

import com.york.portable.swiss.assist.log.hub.LoggerHub;
import com.york.portable.swiss.assist.log.hub.factory.ILoggerHubFactory;
import com.york.portable.swiss.assist.log.hub.factory.LoggerHubPool;
import com.york.portable.swiss.hamlet.model.BizStatusEnum;
import com.york.portable.swiss.hamlet.model.ResponseEntity;
import com.york.portable.swiss.hamlet.model.exception.BizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice
@ResponseBody
public class ExceptionAdvice {

    public ExceptionAdvice(ILoggerHubFactory loggerHubFactory) {
        loggerPool = LoggerHubPool.newInstance(loggerHubFactory);
    }

    protected static LoggerHubPool loggerPool;

    @ExceptionHandler(value = {BizException.class})
    public ResponseEntity handleBizException(BizException e) {
        LoggerHub logger = loggerPool.putByExceptionIfAbsent(e);
        if (logger != null) {
            logger.e("全局业务异常拦截", e);
        }
        ResponseEntity responseEntity = ResponseEntity.build(e.getCode(), e.toString());
        return responseEntity;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleException(Exception e) {
        LoggerHub logger = loggerPool.putByExceptionIfAbsent(e);
        if (logger != null) {
            logger.e("全局系统异常拦截", e);
        }
        ResponseEntity responseEntity;
/*        responseEntity = e instanceof org.springframework.web.servlet.NoHandlerFoundException ?
                        ResponseEntity.build(BizStatusEnum.EXCEPTION.getCode(), e.toString())
                        : ResponseEntity.build(BizStatusEnum.EXCEPTION.getCode(), e.toString());*/
        responseEntity = ResponseEntity.build(BizStatusEnum.EXCEPTION.getCode(), e.toString());
        return responseEntity;
    }


}
