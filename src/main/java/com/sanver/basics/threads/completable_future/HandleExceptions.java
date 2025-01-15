package com.sanver.basics.threads.completable_future;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static com.sanver.basics.utils.Utils.getThreadInfo;
import static com.sanver.basics.utils.Utils.sleep;

public class HandleExceptions {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var executor = Executors.newFixedThreadPool(3)) {
            Supplier<String> supplierWithException = () -> {
                System.out.printf("Supplier with exception started. %s%n", getThreadInfo());
                sleep(7_000);
                System.out.printf("Exception will be thrown in thread %s%n", getThreadInfo());
                throw new IllegalArgumentException("Some error occurred in thread %s".formatted(getThreadInfo()));
            };

            Supplier<List<Integer>> supplier = () -> {
                System.out.printf("Supplier started. %s%n", getThreadInfo());
                sleep(7_000);
                return List.of(1, 3, 5);
            };

            System.out.printf("Example to demonstrate unhandled exception in a completable future without calling get() and join() methods%n%n");
            CompletableFuture.supplyAsync(supplierWithException);
            sleep(10_000); // This is to show that no exception is thrown or written to the stdout if no CompletableFuture.get() or join() methods are called, or the exception is not handled with handle or whenComplete.
            System.out.printf("%nSupplierWithException finished without calling get or join. No exception is propagated to the main thread or written to the stdout in the raised thread. To not lose track of exceptions, use get() or join() methods or handle the exceptions with handle or whenComplete.%n%n");

            sleep(7_000);
            System.out.printf("Example to demonstrate exception handling with handle()%n%n");
            var completableFutureWithException = CompletableFuture
                    .supplyAsync(supplierWithException)
                    .handle((r, e) -> {
                        System.out.println("handle is running for supplierWithException. " + getThreadInfo()); // Notice this thread is the same as the thread executing the supplier, since we use handle.
                        return e == null ? r : "Some error occurred 1";
                    });

            var completableFutureWithoutException = CompletableFuture
                    .supplyAsync(supplier)
                    .handle((r, e) -> {
                        System.out.println("handle is running for supplier " + getThreadInfo()); // Notice this thread is the same as the thread executing the supplier, since we handle.
                        return e == null ? r : "Some error occurred 2";
                    });

            // Notice handle changes the result of the future, while whenComplete does not.
            // Because of this, in cases of exception, whenComplete will propagate the exception to the caller,
            // while handle can just log the exception, but still return a result.
            // In handle method, we can also throw an exception, which can later be handled when join or get methods of the CompletableFuture are called.
            var exceptionResult = completableFutureWithException.get();
            var result = completableFutureWithoutException.get();
            System.out.printf("%nBoth supplier futures finished. Results: %n");
            System.out.println("SupplierWithExceptionResult: " + exceptionResult);
            System.out.println("Supplier result: " + result);

            // Demonstrating handleAsync
            sleep(7_000);
            System.out.printf("%nExample to demonstrate exception handling with handleAsync()%n%n");
            CompletableFuture
                    .supplyAsync(supplierWithException)
                    .handleAsync((r, e) -> {
                        System.out.println("handle is running for supplierWithException. " + getThreadInfo() ); // Notice this thread is different from the thread executing the supplier, since we use handleAsync.
                        return e == null ? r : "Some error occurred 1";
                    }, executor).join();

            CompletableFuture
                    .supplyAsync(supplier)
                    .handleAsync((r, e) -> {
                        System.out.println("handle is running for supplier. " + getThreadInfo()); // Notice this thread is different from the thread executing the supplier, since we use handleAsync.
                        return e == null ? r : "Some error occurred 2";
                    }, executor).join();


            sleep(7_000);
            System.out.printf("%nExample to demonstrate whenComplete()%n%n");
            var completableFutureWhenCompleteWithException = CompletableFuture
                    .supplyAsync(supplierWithException)
                    .whenComplete((r, e) -> {
                        System.out.println("whenComplete is running for supplierWithException. " + getThreadInfo()); // Notice this thread is the same as the thread executing the supplier, since we use whenComplete, not whenCompleteAsync.
                        if (e != null) {
                            printError();
                        }
                    });

            var completableFutureWhenCompleteWithoutException = CompletableFuture
                    .supplyAsync(supplier)
                    .whenComplete((r, e) -> {
                        System.out.println("whenComplete is running for supplier." + getThreadInfo()); // Notice this thread is the same as the thread executing the supplier, since we use whenComplete, not whenCompleteAsync.
                        if (e != null) {
                            printError();
                        }
                    });

            result = completableFutureWhenCompleteWithoutException.get();
            System.out.printf("%n%nSupplier completed. Result: %s%n%n", result);
            exceptionResult = completableFutureWhenCompleteWithException.get(); // This will throw an exception and the program will not continue. If this line is even just after the completableFuture start, whenComplete will be executed before the exception is thrown.
            System.out.println(exceptionResult);
        }
    }

    private static void printError() {
        System.out.println("Some error occurred. " + getThreadInfo());
    }
}
