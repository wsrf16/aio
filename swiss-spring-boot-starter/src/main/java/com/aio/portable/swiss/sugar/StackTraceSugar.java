package com.aio.portable.swiss.sugar;

public abstract class StackTraceSugar {
    private static final int CURRENT_STACK_DEPTH_THREAD = 3;
    private static final int CURRENT_STACK_DEPTH_EXCEPTION = 2;

    private static final int PREVIOUS_STACK_DEPTH_THREAD = CURRENT_STACK_DEPTH_THREAD + 1;
    private static final int PREVIOUS_STACK_DEPTH_EXCEPTION = CURRENT_STACK_DEPTH_EXCEPTION + 1;

    public static StackTraceElement[] getStackTraceElementByThread() {
        return Thread.currentThread().getStackTrace();
    }

    public static StackTraceElement[] getStackTraceByException() {
        return new RuntimeException().getStackTrace();
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
            return Thread.currentThread().getStackTrace()[CURRENT_STACK_DEPTH_THREAD];
        }

        private static StackTraceElement getStackTraceElementByThread(int previous) {
            return Thread.currentThread().getStackTrace()[CURRENT_STACK_DEPTH_THREAD + previous];
        }

        private static StackTraceElement getStackTraceByException() {
            return new RuntimeException().getStackTrace()[CURRENT_STACK_DEPTH_EXCEPTION];
        }

        private static StackTraceElement getStackTraceByException(int previous) {
            return new RuntimeException().getStackTrace()[CURRENT_STACK_DEPTH_EXCEPTION + previous];
        }

        public static String getFileName() {
            return StackTraceSugar.getFileName(getStackTraceElementByThread());
        }

        public static String getFileName(int previous) {
            return StackTraceSugar.getFileName(getStackTraceElementByThread(previous));
        }

        public static String getClassName() {
            return StackTraceSugar.getClassName(getStackTraceElementByThread());
        }

        public static String getClassName(int previous) {
            return StackTraceSugar.getClassName(getStackTraceElementByThread(previous));
        }

        public static String getMethodName() {
            return StackTraceSugar.getMethodName(getStackTraceElementByThread());
        }

        public static String getMethodName(int previous) {
            return StackTraceSugar.getMethodName(getStackTraceElementByThread(previous));
        }

        public static int getLineNumber() {
            return StackTraceSugar.getLineNumber(getStackTraceElementByThread());
        }

        public static int getLineNumber(int previous) {
            return StackTraceSugar.getLineNumber(getStackTraceElementByThread(previous));
        }
    }

    public static class Previous {
        private static StackTraceElement getStackTraceElementByThread() {
            return Thread.currentThread().getStackTrace()[PREVIOUS_STACK_DEPTH_THREAD];
        }

        private static StackTraceElement getStackTraceElementByThread(int previous) {
            return Thread.currentThread().getStackTrace()[PREVIOUS_STACK_DEPTH_THREAD + previous];
        }

        private static StackTraceElement getStackTraceByException() {
            return new Exception().getStackTrace()[PREVIOUS_STACK_DEPTH_EXCEPTION];
        }

        private static StackTraceElement getStackTraceByException(int previous) {
            return new Exception().getStackTrace()[PREVIOUS_STACK_DEPTH_EXCEPTION + previous];
        }

        public static String getFileName() {
            return StackTraceSugar.getFileName(getStackTraceElementByThread());
        }

        public static String getFileName(int previous) {
            return StackTraceSugar.getFileName(getStackTraceElementByThread(previous));
        }

        public static String getClassName() {
            return StackTraceSugar.getClassName(getStackTraceElementByThread());
        }

        public static String getClassName(int previous) {
            return StackTraceSugar.getClassName(getStackTraceElementByThread(previous));
        }

        public static String getMethodName() {
            return StackTraceSugar.getMethodName(getStackTraceElementByThread());
        }

        public static String getMethodName(int previous) {
            return StackTraceSugar.getMethodName(getStackTraceElementByThread(previous));
        }

        public static int getLineNumber() {
            return StackTraceSugar.getLineNumber(getStackTraceElementByThread());
        }

        public static int getLineNumber(int previous) {
            return StackTraceSugar.getLineNumber(getStackTraceElementByThread(previous));
        }
    }
}
