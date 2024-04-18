package com.aio.portable.swiss.hamlet.interceptor.classic.log;

import com.aio.portable.swiss.hamlet.bean.ResponseWrappers;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.request.RequestRecord;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.LogRecord;
import com.aio.portable.swiss.spring.RequestContextHolderSugar;
import com.aio.portable.swiss.suite.log.facade.LogHub;
import com.aio.portable.swiss.suite.log.factory.LogHubFactory;
import com.aio.portable.swiss.suite.log.factory.LogHubPool;
import com.aio.portable.swiss.hamlet.interceptor.classic.log.request.RequestRecordSession;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

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

    // LogRecord.class.getName();
    protected static final String LOG_MARKER_TYPENAME = "com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.LogRecord";
    // LogRecordIgnore.class.getName();
    protected static final String LOG_MARKER_EXCEPT_TYPENAME = "com.aio.portable.swiss.hamlet.interceptor.classic.log.annotation.LogRecordIgnore";

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

    public boolean enableAroundLog() {
        return false;
    }

    public boolean enableThrowingLog() {
        return true;
    }

    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
//        if (joinPoint.getTarget() != null) {
//            Class<?> clazz = joinPoint.getTarget().getClass();
//            if (clazz.isAnnotationPresent(LogRecord.class)) {
//                LogRecord annotation = clazz.getAnnotation(LogRecord.class);
//                if (annotation.ignore() == false)
//                    return joinPoint.proceed();
//            }
//        }


        Signature signature = joinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            Method method = ((MethodSignature) signature).getMethod();
            if (method.isAnnotationPresent(LogRecord.class)) {
                LogRecord annotation = method.getAnnotation(LogRecord.class);
                if (annotation.ignore() == false)
                    return joinPoint.proceed();
            }
        }

        Class<?> clazz = signature.getDeclaringType();
        if (clazz.isAnnotationPresent(LogRecord.class)) {
            LogRecord annotation = clazz.getAnnotation(LogRecord.class);
            if (annotation.ignore() == false)
                return joinPoint.proceed();
        }

        HttpServletRequest request = RequestContextHolderSugar.getRequest();
        HttpServletResponse response = RequestContextHolderSugar.getResponse();

        RequestRecord requestRecord = RequestRecord.newInstance(request, joinPoint);
        RequestRecordSession.setRequestRecord(requestRecord);

        LogHub log = logHubPool.get(joinPoint.getSignature().getDeclaringTypeName());
        requestRecord.fillSpanIdIntoResponse(response);

        String spanId = requestRecord.getSpanId();
        try {
            if (log != null && enableAroundLog()) {
                log.i(MessageFormat.format("{0}({1})", SUMMARY_REQUEST, spanId), requestRecord);
            }

            Object responseEntity = joinPoint.proceed();
            ResponseWrappers.fillSpanIdIntoResponseEntity(responseEntity, spanId);

            if (log != null && enableAroundLog()) {
                log.i(MessageFormat.format("{0}({1})", SUMMARY_RESPONSE, spanId), responseEntity);
            }

            return responseEntity;
        } catch (Exception e) {
            RequestRecordSession.setException(e);
            if (log != null && enableThrowingLog()) {
                log.e(MessageFormat.format("{0}({1})", SUMMARY_EXCEPTION, spanId), requestRecord, e);
            }
            throw e;
        }
    }

}
