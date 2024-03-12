package com.aio.portable.swiss.sugar;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Stack;
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

    public static final <R> R handleIfCatch(Supplier<R> supplier, Function<Throwable, R> failHandler) {
        try {
            R r = supplier.get();
            return r;
        } catch (Throwable e) {
            e.printStackTrace();
            R r = failHandler.apply(e);
            return r;
        }
    }

    public static final <R> R throwRuntimeExceptionIfCatch(Supplier<R> supplier) {
        try {
            R r = supplier.get();
            return r;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static final void handleIfCatch(Runnable runnable, Consumer<Throwable> failHandler) {
        try {
            runnable.run();
        } catch (Throwable e) {
            failHandler.accept(e);
        }
    }

    public static final void throwRuntimeExceptionIfCatch(Runnable runnable) {
        handleIfCatch(runnable, e -> {
            throw new RuntimeException(e);
        });
    }

    public static final void printStackTraceIfCatch(Runnable runnable) {
        handleIfCatch(runnable, e -> {
            e.printStackTrace();
        });
    }

    public static final void ignoreIfCatch(Runnable runnable) {
        handleIfCatch(runnable, e -> {
        });
    }

    public static Throwable getRootCause(Throwable throwable) {
        return getRootCauseIfInstanceOf(throwable, Throwable.class);
    }

    public static <X extends Throwable> X getRootCauseIfInstanceOf(Throwable throwable, Class<X> declaredType) {
        // Keep a second pointer that slowly walks the causal chain. If the fast pointer ever catches
        // the slower pointer, then there's a loop.
        Throwable slowPointer = throwable;
        boolean advanceSlowPointer = false;

        Stack stack = new Stack();
        stack.push(throwable);

        Throwable cause;
        while ((cause = throwable.getCause()) != null) {
            throwable = cause;
            stack.push(throwable);

            if (throwable == slowPointer) {
                throw new IllegalArgumentException("Loop in causal chain detected.", throwable);
            }
            if (advanceSlowPointer) {
                slowPointer = slowPointer.getCause();
            }
            advanceSlowPointer = !advanceSlowPointer; // only advance every other iteration
        }

        Object e;
        while ((e = stack.pop()) != null) {
            if (declaredType.isInstance(e))
                return (X)e;
        }

        return null;
    }
}
