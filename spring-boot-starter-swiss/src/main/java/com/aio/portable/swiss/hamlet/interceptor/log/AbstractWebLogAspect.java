package com.aio.portable.swiss.hamlet.interceptor.log;

import com.aio.portable.swiss.hamlet.bean.RequestRecord;
import com.aio.portable.swiss.hamlet.bean.ResponseWrapper;
import com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogRecord;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.factory.LogHubPool;
import com.aio.portable.swiss.suite.log.support.TracingLogSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.MessageFormat;

class AbstractWebLogAspect {
    public AbstractWebLogAspect() {
        logHubPool = LogHubPool.buildLogHubPool();
    }

    public AbstractWebLogAspect(LogHubFactory logHubFactory) {
        logHubPool = LogHubPool.buildLogHubPool(logHubFactory);
    }

    protected static LogHubPool logHubPool;
    protected static String SUMMARY_REQUEST = "Input Parameters";
    protected static String SUMMARY_RESPONSE = "Output Parameters";
    protected static String SUMMARY_EXCEPTION = "Exception Information";

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
    protected static final String LOG_MARKER_EXCEPT_TYPENAME = "com.aio.portable.swiss.hamlet.interceptor.log.annotation.LogRecordIgnore";

    protected static final String POINTCUT_SPECIAL_MAPPING = "" +
            "(@within(" + LOG_MARKER_TYPENAME + ")"
            + " && !@annotation(" + LOG_MARKER_EXCEPT_TYPENAME + ")"
            + " && (" + POINTCUT_MAPPING + ")"
            + ")"
            + " || @annotation("+ LOG_MARKER_TYPENAME +")";


    protected static final String POINTCUT_SPECIAL = "" +
            "(@within(" + LOG_MARKER_TYPENAME + ")"
            + " && !@annotation(" + LOG_MARKER_EXCEPT_TYPENAME + ")"
            + ")"
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

    public boolean enableInputAndOutputLog() {
        return false;
    }

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (joinPoint.getSignature() instanceof MethodSignature) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            if (method.isAnnotationPresent(LogRecord.class)) {
                LogRecord annotation = method.getAnnotation(LogRecord.class);
                if (annotation.ignore() == false)
                    return joinPoint.proceed();
            }
        }

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes == null ? null : attributes.getRequest();
        HttpServletResponse response = attributes == null ? null : attributes.getResponse();

        String spanId = TracingLogSession.getSpanId();
        RequestRecord requestRecord = RequestRecord.newInstance(spanId, request, joinPoint);
        TracingLogSession.setRequestRecord(requestRecord);

        LogHub log = logHubPool.get(joinPoint.getSignature().getDeclaringTypeName());
        setResponseSpanId(response, spanId);

        try {
            if (log != null && enableInputAndOutputLog()) {
                log.i(MessageFormat.format("{0}({1})", SUMMARY_REQUEST, spanId), requestRecord);
            }

            Object responseRecord = joinPoint.proceed();
            injectSpanIdIntoResponseWrapper(responseRecord, spanId);

            if (log != null && enableInputAndOutputLog()) {
                log.i(MessageFormat.format("{0}({1})", SUMMARY_RESPONSE, spanId), responseRecord);
            }

            return responseRecord;
        } catch (Exception e) {
            TracingLogSession.setException(e);
            if (log != null) {
                {
                    log.e(MessageFormat.format("{0}({1})", SUMMARY_EXCEPTION, spanId), requestRecord, e);
//                    TracingSession.setWeblogHasPrinted(true);
                }
            }
            throw e;
        }
    }

    private void setResponseSpanId(HttpServletResponse response, String spanId) {
        if (response != null && !response.containsHeader(ResponseWrapper.SPAN_ID_HEADER))
            response.addHeader(ResponseWrapper.SPAN_ID_HEADER, spanId);
    }

    private static final void injectSpanIdIntoResponseWrapper(Object responseRecord, String traceId) {
        if (responseRecord instanceof ResponseWrapper) {
            ((ResponseWrapper) responseRecord).setSpanId(traceId);
        }
    }

    private static final String generateUniqueId() {
        return IDS.uuid();
    }
}
