package com.aio.portable.swiss.suite.log.support;

import com.aio.portable.swiss.hamlet.bean.RequestRecord;
import com.aio.portable.swiss.suite.algorithm.identity.IDS;

public abstract class TracingLogSession {
    static class RequestSession {
        private String spanId;
        private RequestRecord requestRecord;
        private Exception exception;
//        private Boolean weblogHasPrinted;

        public String getSpanId() {
            return spanId;
        }

        public void setSpanId(String spanId) {
            this.spanId = spanId;
        }

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

    private static ThreadLocal<RequestSession> threadLocal = new ThreadLocal<>();

    public static final String getSpanId() {
        synchronized (Thread.currentThread()) {
            if (threadLocal.get() == null)
                threadLocal.set(new RequestSession());
            RequestSession requestSession = threadLocal.get();
            if (requestSession.getSpanId() == null)
                requestSession.setSpanId(IDS.uuid());
            return requestSession.getSpanId();
        }
    }


    public static final synchronized void setRequestRecord(RequestRecord requestRecord) {
        synchronized(Thread.currentThread()) {
            threadLocal.get().setRequestRecord(requestRecord);
        }
    }

    public static final synchronized RequestRecord getRequestRecord() {
        synchronized(Thread.currentThread()) {
            if (threadLocal.get() == null)
                threadLocal.set(new RequestSession());
            return threadLocal.get().getRequestRecord();
        }
    }


    public static final synchronized void setException(Exception e) {
        synchronized(Thread.currentThread()) {
            if (threadLocal.get() == null)
                threadLocal.set(new RequestSession());
            threadLocal.get().setException(e);
        }
    }

    public static final synchronized Exception getException() {
        synchronized(Thread.currentThread()) {
            if (threadLocal.get() == null)
                threadLocal.set(new RequestSession());
            return threadLocal.get().getException();
        }
    }

//    public static final synchronized void setWeblogHasPrinted(boolean weblogHasPrinted) {
//        synchronized(Thread.currentThread()) {
//            if (threadLocal.get() == null)
//                threadLocal.set(new RequestSession());
//            threadLocal.get().setWeblogHasPrinted(weblogHasPrinted);
//        }
//    }
//
//    public static final synchronized Boolean getWeblogHasPrinted() {
//        synchronized(Thread.currentThread()) {
//            if (threadLocal.get() == null)
//                threadLocal.set(new RequestSession());
//            return threadLocal.get().getWeblogHasPrinted();
//        }
//    }
    public static final void remove() {
        threadLocal.remove();
    }

//    private static ThreadLocal<String> SpanIdThreadLocal = new ThreadLocal<>();
//    private static ThreadLocal<RequestRecord> RequestRecordThreadLocal = new ThreadLocal<>();
//    private static ThreadLocal<Exception> ExceptionThreadLocal = new ThreadLocal<>();
//
//
//
//
//    public static final synchronized String getSpanId() {
//        if (SpanIdThreadLocal.get() == null)
//            SpanIdThreadLocal.set(IDS.uuid());
//        return SpanIdThreadLocal.get();
//    }
//
//    public static final void removeSpanId() {
//        SpanIdThreadLocal.remove();
//    }
//
//
//    public static final synchronized void setRequestRecord(RequestRecord requestRecord) {
//        if (RequestRecordThreadLocal.get() == null)
//            RequestRecordThreadLocal.set(requestRecord);
//    }
//
//    public static final synchronized RequestRecord getRequestRecord() {
//        return RequestRecordThreadLocal.get();
//    }
//
//    public static final void removeRequestRecord() {
//        RequestRecordThreadLocal.remove();
//    }
//
//
//    public static final synchronized void setException(Exception e) {
//        if (ExceptionThreadLocal.get() == null)
//            ExceptionThreadLocal.set(e);
//    }
//
//    public static final synchronized Exception getException() {
//        return ExceptionThreadLocal.get();
//    }
//
//    public static final void removeException() {
//        ExceptionThreadLocal.remove();
//    }
//
//
//    public static final void remove() {
//        SpanIdThreadLocal.remove();
//        RequestRecordThreadLocal.remove();
//        ExceptionThreadLocal.remove();
//    }

}
