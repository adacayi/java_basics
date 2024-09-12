package com.sanver.basics.utils;

import java.util.concurrent.Callable;

public class RethrowAsUnchecked {
    @SuppressWarnings("unchecked")
    public static <T, E extends Throwable> T uncheck(Callable<T> callable) throws E {
        try {
            return callable.call();
        } catch (Exception e) {
            throw (E) e;
        }
    }

    @SuppressWarnings("unchecked")
    public static <E extends Throwable> void uncheck(ThrowingRunnable runnable) throws E {
        try {
            runnable.run();
        } catch (Exception e) {
            throw (E)e;
        }
    }

    @FunctionalInterface
    @SuppressWarnings("java:S112")
    public interface ThrowingRunnable {
        void run() throws Exception;
    }
}
