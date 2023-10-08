package com.aio.portable.swiss.hamlet.interceptor.log;

import com.aio.portable.swiss.hamlet.bean.RequestRecord;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.exception.HandOverException;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.factory.LogHubPool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;

class AbstractWebLogAspect {
    public AbstractWebLogAspect() {
        logPool = LogHubFactory.isInitial() ?
                LogHubPool.importLogHubFactory(new LogHubFactory() {}) : LogHubPool.importLogHubFactory(new Slf4JLogHubFactory());
    }

    public AbstractWebLogAspect(LogHubFactory logHubFactory) {
        logPool = LogHubPool.importLogHubFactory(logHubFactory);
    }

    protected static LogHubPool logPool;
    protected static String REQUEST_SUMMARY = "Input Information";
    protected static String RESPONSE_SUMMARY = "Output Information";
    protected static String EXCEPTION_SUMMARY = "Exception Information";

    protected static final String POINTCUT_CONTROLLER = "" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.RequestMapping)";

    protected static final String POINTCUT_MAPPING = "" +
            "@annotation(org.springframework.web.bind.annotation.GetMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PostMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PatchMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            " || @annotation(org.springframework.web.bind.annotation.Mapping)" +
            " || @annotation(org.springframework.web.bind.annotation.RequestMapping)";

    protected static final String LOG_MARKER_TYPENAME = "com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogRecord";
    protected static final String LOG_MARKER_EXCEPT_TYPENAME = "com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogRecordExcept";

    protected static final String POINTCUT_SPECIAL_MAPPING = "" +
            "(@within(" + LOG_MARKER_TYPENAME + ")"
            + " && !@annotation(" + LOG_MARKER_EXCEPT_TYPENAME + ")"
            + " && (" + POINTCUT_MAPPING + "))"
            + " || @annotation("+ LOG_MARKER_TYPENAME +")";


    protected static final String POINTCUT_SPECIAL = "" +
            "(@within(" + LOG_MARKER_TYPENAME + ")"
            + " && !@annotation(" + LOG_MARKER_EXCEPT_TYPENAME + "))"
            + " || @annotation("+ LOG_MARKER_TYPENAME +")";

//    public void doBefore(JoinPoint joinPoint) {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        RequestRecord requestRecord = RequestRecord.newInstance(request, joinPoint);
//
//        LogHub logger = loggerPool.get(joinPoint.getSignature().getDeclaringTypeName());
//        if (logger != null) {
//            logger.info(REQUEST_SUMMARY, requestRecord);
//        }
//    }
//
//    public void doAfterReturning(JoinPoint joinPoint, Object result) {
//        LogHub logger = loggerPool.get(joinPoint.getSignature().getDeclaringTypeName());
//        if (logger != null) {
//            logger.info(RESPONSE_SUMMARY, result);
//        }
//    }

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes == null ? null : attributes.getRequest();
        RequestRecord requestRecord = RequestRecord.newInstance(request, joinPoint);

        LogHub log = logPool.get(joinPoint.getSignature().getDeclaringTypeName());
        String spanId = generateUniqueId();
        addSpanIdIfResponse(attributes, spanId);
        if (log != null) {
            log.i(MessageFormat.format("{0}({1})", REQUEST_SUMMARY, spanId), requestRecord);
        }

        try {
            Object responseRecord = joinPoint.proceed();
            addSpanIdIfResponseWrapper(responseRecord, spanId);

            if (log != null) {
                log.i(MessageFormat.format("{0}({1})", RESPONSE_SUMMARY, spanId), responseRecord);
            }

            return responseRecord;
        } catch (Exception e) {
            log.e(MessageFormat.format("{0}({1})", EXCEPTION_SUMMARY, spanId), requestRecord, e);
                throw request != null ?
                        new HandOverException(e, requestRecord, spanId) : e;
        }
    }

    private void addSpanIdIfResponse(ServletRequestAttributes attributes, String spanId) {
        HttpServletResponse response = attributes == null ? null : attributes.getResponse();
        if (response != null)
            response.addHeader(ResponseWrapper.SPAN_ID_HEADER, spanId);
    }

    private static final void addSpanIdIfResponseWrapper(Object responseRecord, String traceId) {
        if (responseRecord instanceof ResponseWrapper) {
            ((ResponseWrapper) responseRecord).setSpanId(traceId);
        }
    }

    private static final String generateUniqueId() {
        return IDS.uuid();
    }
}
