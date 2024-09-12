package com.sanver.basics.utils;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This class provides a set of utility methods to handle checked exceptions in Java 8 streams.
 * Look into LambdaExceptionUtilSample.java for a reference snippet of code.
 * Also check the unit tests in LambdaExceptionUtilTest.java.
 * @deprecated This class is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked} class.
 */
@Deprecated(since = "12-09-2024")
public class LambdaExceptionUtil {

    /**
     * .forEach(rethrowConsumer(name -> System.out.println(Class.forName(name)))); or
     * .forEach(rethrowConsumer(ClassNameUtil::println));
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <T, E extends Exception> Consumer<T> rethrowConsumer(ConsumerWithExceptions<T, E> consumer) {
        return t -> uncheck(consumer, t);
    }

    /**
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <T, U, E extends Exception> BiConsumer<T, U> rethrowBiConsumer(BiConsumerWithExceptions<T, U, E> biConsumer) {
        return (t, u) -> uncheck(biConsumer, t, u);
    }

    /**
     * .map(rethrowFunction(name -> Class.forName(name))) or .map(rethrowFunction(Class::forName))
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(FunctionWithExceptions<T, R, E> function) {
        return t -> uncheck(function, t);
    }

    /**
     * rethrowSupplier(() -> Class.forName("java.lang.String")),
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <T, E extends Exception> Supplier<T> rethrowSupplier(SupplierWithExceptions<T, E> supplier) {
        return () -> uncheck(supplier);
    }

    /**
     * rethrowRunnable(() -> System.out.println(Class.forName("java.lang.String")))
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <E extends Exception> Runnable rethrowRunnable(RunnableWithExceptions<E> runnable) {
        return () -> uncheck(runnable);
    }

    /**
     * uncheck(() -> Class.forName("xxx"));
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <E extends Exception> void uncheck(RunnableWithExceptions<E> t) {
        try {
            t.run();
        } catch (Exception exception) {
            throwAsUnchecked(exception);
        }
    }

    /**
     * uncheck(x -> System.out.println(Class.forName(x)), "java.lang.String);
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <T, E extends Exception> void uncheck(ConsumerWithExceptions<T, E> consumer, T t) {
        try {
            consumer.accept(t);
        } catch (Exception exception) {
            throwAsUnchecked(exception);
        }
    }

    /**
     * uncheck(Class::forName, "xxx");
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <T, U, E extends Exception> void uncheck(BiConsumerWithExceptions<T, U, E> biconsumer, T t, U u) {
        try {
            biconsumer.accept(t, u);
        } catch (Exception exception) {
            throwAsUnchecked(exception);
        }
    }

    /**
     * uncheck(() -> Class.forName("xxx"));
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <R, E extends Exception> R uncheck(SupplierWithExceptions<R, E> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            throwAsUnchecked(exception);
            return null;
        }
    }

    /**
     * uncheck(Class::forName, "xxx");
     * @deprecated This method is deprecated, because it is easier to maintain and use the simpler {@link com.sanver.basics.utils.RethrowAsUnchecked#uncheck} method.
     */
    @Deprecated(since = "12-09-2024")
    public static <T, R, E extends Exception> R uncheck(FunctionWithExceptions<T, R, E> function, T t) {
        try {
            return function.apply(t);
        } catch (Exception exception) {
            throwAsUnchecked(exception);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E {
        throw (E) exception;
    }

    @FunctionalInterface
    public interface ConsumerWithExceptions<T, E extends Exception> {

        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface BiConsumerWithExceptions<T, U, E extends Exception> {

        void accept(T t, U u) throws E;
    }

    @FunctionalInterface
    public interface FunctionWithExceptions<T, R, E extends Exception> {

        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface SupplierWithExceptions<T, E extends Exception> {

        T get() throws E;
    }

    @FunctionalInterface
    public interface RunnableWithExceptions<E extends Exception> {

        void run() throws E;
    }
}
