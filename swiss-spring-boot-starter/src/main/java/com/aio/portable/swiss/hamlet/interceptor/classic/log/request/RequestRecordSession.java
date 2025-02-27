package com.aio.portable.swiss.hamlet.interceptor.classic.log.request;

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

    public static final String getSpanId() {
        synchronized (Thread.currentThread()) {
            if (CURRENT_THREAD_LOCAL.get() == null)
                CURRENT_THREAD_LOCAL.set(new RequestSession());
            return CURRENT_THREAD_LOCAL.get().getRequestRecord().getSpanId();
        }
    }

    public static final synchronized void setRequestRecord(RequestRecord requestRecord) {
        synchronized(Thread.currentThread()) {
            if (CURRENT_THREAD_LOCAL.get() == null)
                CURRENT_THREAD_LOCAL.set(new RequestSession());
            CURRENT_THREAD_LOCAL.get().setRequestRecord(requestRecord);
        }
    }

    public static final synchronized RequestRecord getRequestRecord() {
        synchronized(Thread.currentThread()) {
            if (CURRENT_THREAD_LOCAL.get() == null)
                CURRENT_THREAD_LOCAL.set(new RequestSession());
            return CURRENT_THREAD_LOCAL.get().getRequestRecord();
        }
    }


    public static final synchronized void setException(Exception e) {
        synchronized(Thread.currentThread()) {
            if (CURRENT_THREAD_LOCAL.get() == null)
                CURRENT_THREAD_LOCAL.set(new RequestSession());
            CURRENT_THREAD_LOCAL.get().setException(e);
        }
    }

    public static final synchronized Exception getException() {
        synchronized(Thread.currentThread()) {
            if (CURRENT_THREAD_LOCAL.get() == null)
                CURRENT_THREAD_LOCAL.set(new RequestSession());
            return CURRENT_THREAD_LOCAL.get().getException();
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
        CURRENT_THREAD_LOCAL.remove();
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
