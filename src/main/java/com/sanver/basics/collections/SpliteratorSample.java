package com.sanver.basics.collections;

import java.util.HashSet;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * <div><p>In Java, a <strong>Spliterator</strong> is an object that provides a way to traverse and partition elements in a collection (such as <code>List</code>, <code>Set</code>, or <code>Stream</code>). It was introduced in Java 8 as part of the <code>java.util</code> package, primarily to support parallel processing and to improve efficiency when handling large datasets.</p><p>The term "Spliterator" is a combination of <strong>split</strong> and <strong>iterator</strong>. It combines two key functionalities:</p><ol><li><p><strong>Splitting</strong>: A Spliterator can split itself into multiple parts, which is useful for parallel processing. When working with large datasets, the ability to split the workload into smaller pieces can significantly improve performance by distributing the work across multiple threads.</p></li><li><p><strong>Iterating</strong>: Like a traditional iterator, a Spliterator allows sequential iteration over elements in a collection.</p></li></ol><h3>Key Features of Spliterator:</h3><ul><li><strong>Parallel Processing</strong>: Spliterators are used heavily in the parallel stream API. They can divide a dataset into smaller chunks, enabling parallel processing by multiple threads.</li><li><strong>Estimate Size</strong>: They can estimate the number of elements remaining for traversal, which is important for performance optimizations in parallel execution.</li><li><strong>Characteristics</strong>: A Spliterator can provide additional information about the data it is traversing through various characteristics (like <code>ORDERED</code>, <code>SORTED</code>, <code>SIZED</code>, etc.), enabling efficient processing strategies.</li></ul><h3>Common Methods:</h3><ol><li><p><strong>tryAdvance(Consumer&lt;? super T&gt; action)</strong>: This method processes one element at a time and returns <code>true</code> if more elements exist. It accepts a <code>Consumer</code> that defines how each element is processed.</p></li><li><p><strong>forEachRemaining(Consumer&lt;? super T&gt; action)</strong>: It processes all remaining elements sequentially in the current thread.</p></li><li><p><strong>trySplit()</strong>: Attempts to partition the elements in the current Spliterator into two Spliterators. This is used to facilitate parallel processing by dividing the workload.</p></li><li><p><strong>estimateSize()</strong>: Estimates the number of elements that remain to be processed.</p></li><li><p><strong>characteristics()</strong>: Returns a set of characteristics that describe the properties of the Spliterator (e.g., <code>ORDERED</code>, <code>DISTINCT</code>, <code>SIZED</code>, etc.).</p></li></ol>
 * <h3>Characteristics:</h3><p>A Spliterator can declare certain properties about the collection it is working on using <strong>characteristics</strong>. Some common characteristics include:</p><ul><li><strong>ORDERED</strong>: Elements have a defined encounter order.</li><li><strong>DISTINCT</strong>: Elements are distinct.</li><li><strong>SORTED</strong>: Elements are sorted according to a comparator or natural ordering.</li><li><strong>SIZED</strong>: The Spliterator can provide an accurate estimate of its size.</li><li><strong>SUBSIZED</strong>: All child Spliterators created by <code>trySplit</code> are also <code>SIZED</code>.</li></ul><p>Spliterator is a key component in Java Streams, especially for parallel processing, allowing efficient use of system resources for large datasets.</p></div>
 */
public class SpliteratorSample {

    public static void main(String[] args) {
        // An ideal trySplit method efficiently (without traversal) divides its elements exactly in half, allowing balanced parallel computation.
        Spliterator<Integer> spliterator = IntStream.range(0, 100).spliterator();
        Spliterator<Integer> subSplit1 = spliterator.trySplit(); // If spliterator cannot be split (because emptiness, inability to split after traversal has commenced, data structure constraints, and efficiency considerations) trySplit returns null.
        Spliterator<Integer> subSplit2 = subSplit1.trySplit();
        Spliterator<Integer> subSplit3 = spliterator.trySplit();
        print(subSplit1, "subSplit1");
        print(subSplit2, "subSplit2");
        print(subSplit3, "subSplit3");
        print(spliterator, "spliterator");

        // Spliterator can be used to convert any Iterable to List
        System.out.printf("Spliterator to convert Iterable to List%n%n");
        var set = new HashSet<>(List.of(3, 2, 1, 5, 4, 6));
        var list = StreamSupport.stream(set.spliterator(), false).toList();
        System.out.println(list);
        list = set.parallelStream().toList(); // Alternative for sets
        System.out.println(list);
    }

    private static void print(Spliterator<Integer> spliterator, String name) {
        if (spliterator == null) {
            System.out.printf("Cannot split. %s is null%n", name);
            return;
        }

        System.out.printf("%-26s: %d%n", name + ".estimateSize()", spliterator.estimateSize()); // Returns an estimate of the number of elements that would be encountered by a forEachRemaining traversal
        spliterator.forEachRemaining(x -> System.out.print(x + " "));
        System.out.printf("%n%n");
    }
}

