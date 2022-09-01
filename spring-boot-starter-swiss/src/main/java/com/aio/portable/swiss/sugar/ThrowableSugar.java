package com.aio.portable.swiss.sugar;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ThrowableSugar {
    public static final String getStackTraceAsString(Throwable throwable) {
        if (throwable == null)
            return null;
        StringWriter stringWriter = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public static final <R> R catchThenReturn(Supplier<R> supplier, Function<Throwable, R> failHandler) {
        try {
            R r = supplier.get();
            return r;
        } catch (Throwable e) {
            e.printStackTrace();
            R r = failHandler.apply(e);
            return r;
        }
    }

    public static final <R> R catchThenThrowRuntimeException(Supplier<R> supplier) {
        try {
            R r = supplier.get();
            return r;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static final void catchThenHandle(Runnable runnable, Consumer<Throwable> failHandler) {
        try {
            runnable.run();
        } catch (Throwable e) {
            failHandler.accept(e);
        }
    }

    public static final void catchThenThrowRuntimeException(Runnable runnable) {
        catchThenHandle(runnable, e -> {
            throw new RuntimeException(e);
        });
    }

    public static final void catchThenPrintStackTrace(Runnable runnable) {
        catchThenHandle(runnable, e -> {
            e.printStackTrace();
        });
    }

    public static final void catchThenSilent(Runnable runnable) {
        catchThenHandle(runnable, e -> {
        });
    }
}
