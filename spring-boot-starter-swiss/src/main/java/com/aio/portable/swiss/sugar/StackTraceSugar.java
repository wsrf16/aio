package com.aio.portable.swiss.sugar;

public abstract class StackTraceSugar {
    private final static int CURRENT_STACK_INDEX_THREAD = 3;
    private final static int CURRENT_STACK_INDEX_EXCEPTION = 2;

    private final static int PREVIOUS_STACK_INDEX_THREAD = CURRENT_STACK_INDEX_THREAD + 1;
    private final static int PREVIOUS_STACK_INDEX_EXCEPTION = CURRENT_STACK_INDEX_EXCEPTION + 1;

    public static StackTraceElement[] getStackTraceElementByThread() {
        return Thread.currentThread().getStackTrace();
    }

    public static StackTraceElement[] getStackTraceByException() {
        return new Exception().getStackTrace();
    }

    public static String getFileName(StackTraceElement stackTraceElement) {
        return stackTraceElement.getFileName();
    }

    public static String getClassName(StackTraceElement stackTraceElement) {
        return stackTraceElement.getClassName();
    }

    public static String getMethodName(StackTraceElement stackTraceElement) {
        return stackTraceElement.getMethodName();
    }

    public static int getLineNumber(StackTraceElement stackTraceElement) {
        return stackTraceElement.getLineNumber();
    }

    public static class Current {
        private static StackTraceElement getStackTraceElementByThread() {
            return Thread.currentThread().getStackTrace()[CURRENT_STACK_INDEX_THREAD];
        }

        private static StackTraceElement getStackTraceByException() {
            return new Exception().getStackTrace()[CURRENT_STACK_INDEX_EXCEPTION];
        }

        public static String getFileName() {
            return StackTraceSugar.getFileName(getStackTraceElementByThread());
        }

        public static String getClassName() {
            return StackTraceSugar.getClassName(getStackTraceElementByThread());
        }

        public static String getMethodName() {
            return StackTraceSugar.getMethodName(getStackTraceElementByThread());
        }

        public static int getLineNumber() {
            return StackTraceSugar.getLineNumber(getStackTraceElementByThread());
        }
    }

    public static class Previous {
        private static StackTraceElement getStackTraceElementByThread() {
            return Thread.currentThread().getStackTrace()[PREVIOUS_STACK_INDEX_THREAD];
        }

        private static StackTraceElement getStackTraceByException() {
            return new Exception().getStackTrace()[PREVIOUS_STACK_INDEX_EXCEPTION];
        }

        public static String getFileName() {
            return StackTraceSugar.getFileName(getStackTraceElementByThread());
        }

        public static String getClassName() {
            return StackTraceSugar.getClassName(getStackTraceElementByThread());
        }

        public static String getMethodName() {
            return StackTraceSugar.getMethodName(getStackTraceElementByThread());
        }

        public static int getLineNumber() {
            return StackTraceSugar.getLineNumber(getStackTraceElementByThread());
        }
    }
}
