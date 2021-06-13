package com.aio.portable.swiss.sugar;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ThrowableSugar {
    public static String getStackTraceAsString(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static <R> R catchThenReturn(Supplier<R> supplier, Function<Throwable, R> failHandler) {
        try {
            R r = supplier.get();
            return r;
        } catch (Throwable e) {
            e.printStackTrace();
            final R r = failHandler.apply(e);
            return r;
        }
    }

    public static void catchThenHandle(Runnable runnable, Consumer<Throwable> failHandler) {
        try {
            runnable.run();
        } catch (Throwable e) {
            failHandler.accept(e);
        }
    }

    public static void catchThenHandleThrowRuntimeException(Runnable runnable) {
        catchThenHandle(runnable, e -> {
            throw new RuntimeException(e);
        });
    }

    public static void catchThenHandlePrintStackTrace(Runnable runnable) {
        catchThenHandle(runnable, e -> {
            e.printStackTrace();
        });
    }
}
