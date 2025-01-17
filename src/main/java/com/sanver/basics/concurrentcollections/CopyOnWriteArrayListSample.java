package com.sanver.basics.concurrentcollections;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.sanver.basics.utils.Utils.sleep;


/**
 * The design of the CopyOnWriteArrayList uses an interesting technique to make it thread-safe without a need for synchronization.
 * When we are using any of the modify methods – such as add() or remove() – the whole content of the CopyOnWriteArrayList is copied into the new internal copy.
 * Due to this simple fact, we can iterate over the list in a safe way, even when concurrent modification is happening.
 * <a href="https://www.baeldung.com/java-copy-on-write-arraylist">Source</a>
 */
public class CopyOnWriteArrayListSample {

    public static void main(String[] args) {
        var list = new CopyOnWriteArrayList<String>();
        int count = 100_000;

        var append = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < count; i++) {
                list.add("a");
            }
        });

        var remove = CompletableFuture.runAsync(() -> {
            int i = 0;

            while (i < count) {
                if (!list.isEmpty()) {
                    list.removeFirst();
                    i++;
                }
            }
        });

        sleep(10);

        for (var ignored : list) ;// This might give a ConcurrentModificationException if we used an ArrayList instead.

        CompletableFuture.allOf(append, remove).join();

        System.out.println("Finished. List size = " + list.size());// Since the list is thread-safe, the size is 0.
        // If we used ArrayList instead, it might result in exceptions like ConcurrentModificationException and ArrayIndexOutOfBoundsException or the remove completable future might never end because "i" never reaches "count".
    }
}
