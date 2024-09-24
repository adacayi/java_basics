package com.sanver.basics.threads.completable_future;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import static com.sanver.basics.utils.Utils.printCurrentThread;
import static com.sanver.basics.utils.Utils.sleep;

public class HandleExceptions {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        printCurrentThread("Main code is running");

        Supplier<String> supplierWithException = () -> {
            printCurrentThread("SupplierWithException is running");
            sleep(3000);
            throw new IllegalArgumentException("Some exception message");
        };

        Supplier<List<Integer>> supplier = () -> {
            printCurrentThread("Supplier is running");
            sleep(3100);
            return List.of(1, 3, 5);
        };

        CompletableFuture.supplyAsync(supplierWithException);
        sleep(5000); // This is to show that no exception is thrown or written to the stdout if no CompletableFuture.get() or join() methods are called, or the exception is not handled with handle or whenComplete.
        System.out.printf("%nSupplierWithException finished without calling get or join. No exception is propagated to the main thread or written to the stdout in the raised thread. To not lose track of exceptions, use get() or join() methods or handle the exceptions with handle or whenComplete.%n%n");

        var completableFutureWithException = CompletableFuture
                .supplyAsync(supplierWithException)
                .handle((r, e) -> {
                    printCurrentThread("handle is running for supplierWithException"); // Notice this thread is the same as the completable future.
                    return e == null ? r : "Some error occurred 1";
                });

        var completableFutureWithoutException = CompletableFuture
                .supplyAsync(supplier)
                .handle((r, e) -> {
                    printCurrentThread("handle is running for supplier"); // Notice this thread is the same as the completable future.
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

        var completableFutureWhenCompleteWithException = CompletableFuture
                .supplyAsync(supplierWithException)
                .whenComplete((r, e) -> {
                    printCurrentThread("whenComplete is running for supplierWithException"); // Notice this thread is the same as the completable future.
                    if (e != null) {
                        System.out.println("Some error occurred 3");
                    }
                });

        var completableFutureWhenCompleteWithoutException = CompletableFuture
                .supplyAsync(supplier)
                .whenComplete((r, e) -> {
                    printCurrentThread("whenComplete is running for supplier"); // Notice this thread is the same as the completable future.
                    if (e != null) {
                        System.out.println("Some error occurred 3");
                    }
                });

        result = completableFutureWhenCompleteWithoutException.get();
        System.out.printf("%n%nSupplier completed. Result: %s%n%n", result);
        exceptionResult = completableFutureWhenCompleteWithException.get(); // This will throw an exception and the program will not continue. If this line is even just after the completableFuture start, whenComplete will be executed before the exception is thrown.
        System.out.println(exceptionResult);
    }
}
