package com.aio.portable.swiss.hamlet.interceptor.classic.log.request;

import org.aspectj.lang.JoinPoint;

import javax.servlet.http.HttpServletRequest;

public abstract class RequestRecordSession {
    static class RequestSession {
        private RequestRecord requestRecord;
        private Exception exception;
//        private Boolean weblogHasPrinted;

        public RequestRecord getRequestRecord() {
            return requestRecord;
        }

        public void setRequestRecord(RequestRecord requestRecord) {
            this.requestRecord = requestRecord;
        }

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

//        public Boolean getWeblogHasPrinted() {
//            return weblogHasPrinted;
//        }
//
//        public void setWeblogHasPrinted(Boolean weblogHasPrinted) {
//            this.weblogHasPrinted = weblogHasPrinted;
//        }
    }

    private static final ThreadLocal<RequestSession> CURRENT_THREAD_LOCAL = new ThreadLocal<>();

    public static synchronized String getSpanId() {
        return RequestRecordSession.getRequestRecord().getSpanId();
    }

    public static synchronized String getTraceId() {
        return RequestRecordSession.getRequestRecord().getTraceId();
    }

    public static synchronized RequestRecord injectRequestRecord(HttpServletRequest request, JoinPoint joinPoint) {
        RequestRecord requestRecord = RequestRecord.newInstance(request, joinPoint);
        RequestRecordSession.setRequestRecord(requestRecord);
        return requestRecord;
    }

    public static synchronized void setRequestRecord(RequestRecord requestRecord) {
        synchronized(Thread.currentThread()) {
            if (CURRENT_THREAD_LOCAL.get() == null)
                CURRENT_THREAD_LOCAL.set(new RequestSession());
            CURRENT_THREAD_LOCAL.get().setRequestRecord(requestRecord);
        }
    }

    public static synchronized RequestRecord getRequestRecord() {
        if (CURRENT_THREAD_LOCAL.get() == null)
            CURRENT_THREAD_LOCAL.set(new RequestSession());
        return CURRENT_THREAD_LOCAL.get().getRequestRecord();
    }


    public static synchronized void setException(Exception e) {
        synchronized(Thread.currentThread()) {
            if (CURRENT_THREAD_LOCAL.get() == null)
                CURRENT_THREAD_LOCAL.set(new RequestSession());
            CURRENT_THREAD_LOCAL.get().setException(e);
        }
    }

    public static synchronized Exception getException() {
        synchronized(Thread.currentThread()) {
            if (CURRENT_THREAD_LOCAL.get() == null)
                CURRENT_THREAD_LOCAL.set(new RequestSession());
            return CURRENT_THREAD_LOCAL.get().getException();
        }
    }

//    public static synchronized void setWeblogHasPrinted(boolean weblogHasPrinted) {
//        synchronized(Thread.currentThread()) {
//            if (threadLocal.get() == null)
//                threadLocal.set(new RequestSession());
//            threadLocal.get().setWeblogHasPrinted(weblogHasPrinted);
//        }
//    }
//
//    public static synchronized Boolean getWeblogHasPrinted() {
//        synchronized(Thread.currentThread()) {
//            if (threadLocal.get() == null)
//                threadLocal.set(new RequestSession());
//            return threadLocal.get().getWeblogHasPrinted();
//        }
//    }
    public static void remove() {
        CURRENT_THREAD_LOCAL.remove();
    }

//    private static ThreadLocal<String> SpanIdThreadLocal = new ThreadLocal<>();
//    private static ThreadLocal<RequestRecord> RequestRecordThreadLocal = new ThreadLocal<>();
//    private static ThreadLocal<Exception> ExceptionThreadLocal = new ThreadLocal<>();
//
//
//
//
//    public static synchronized String getSpanId() {
//        if (SpanIdThreadLocal.get() == null)
//            SpanIdThreadLocal.set(IDS.uuid());
//        return SpanIdThreadLocal.get();
//    }
//
//    public static void removeSpanId() {
//        SpanIdThreadLocal.remove();
//    }
//
//
//    public static synchronized void setRequestRecord(RequestRecord requestRecord) {
//        if (RequestRecordThreadLocal.get() == null)
//            RequestRecordThreadLocal.set(requestRecord);
//    }
//
//    public static synchronized RequestRecord getRequestRecord() {
//        return RequestRecordThreadLocal.get();
//    }
//
//    public static void removeRequestRecord() {
//        RequestRecordThreadLocal.remove();
//    }
//
//
//    public static synchronized void setException(Exception e) {
//        if (ExceptionThreadLocal.get() == null)
//            ExceptionThreadLocal.set(e);
//    }
//
//    public static synchronized Exception getException() {
//        return ExceptionThreadLocal.get();
//    }
//
//    public static void removeException() {
//        ExceptionThreadLocal.remove();
//    }
//
//
//    public static void remove() {
//        SpanIdThreadLocal.remove();
//        RequestRecordThreadLocal.remove();
//        ExceptionThreadLocal.remove();
//    }

}
