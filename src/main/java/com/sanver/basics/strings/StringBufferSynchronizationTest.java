package com.sanver.basics.strings;

import java.util.concurrent.CompletableFuture;

import static com.sanver.basics.utils.Utils.sleep;

public class StringBufferSynchronizationTest {

    public static void main(String[] args) {
        var buffer = new StringBuffer();
        int count = 100000;

        Runnable append = () -> {
            for (int i = 0; i < count; i++)
                buffer.append("a");
        };

        Runnable delete = () -> {
            for (int i = 0; i < count; i++) {
                while (buffer.isEmpty()) {
                    sleep(1);
                }
                buffer.deleteCharAt(0);
            }
        };

        var appendFuture = CompletableFuture.runAsync(append);
        var deleteFuture = CompletableFuture.runAsync(delete);
        CompletableFuture.allOf(appendFuture, deleteFuture).join();

        System.out.println("Finished. Buffer length = " + buffer.length());// Since StringBuffer is synchronised the size
        // is 0. If we used StringBuilder instead the
        // code would not end always.
    }
}
