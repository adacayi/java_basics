package com.sanver.basics.streamapi;

import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * Demonstrates the behavior of ordered and unordered streams in Java.
 *
 * <p>
 * A stream is considered <b>ordered</b> when it preserves the encounter order
 * of its elements as they appear in the source. This means that stream
 * operations will process elements in the same sequence unless explicitly
 * modified (e.g., with {@code unordered()}).
 * </p>
 *
 * <h3>Key Characteristics of an Ordered Stream:</h3>
 * <ul>
 *   <li><b>Preserves Encounter Order:</b> The original order of elements from the source
 *       is maintained during processing. For instance, an ordered stream from a {@code List}
 *       will process elements in the same order as they are stored in the list.</li>
 *   <li><b>Source-Dependent:</b> The ordered nature of a stream depends on its source:
 *     <ul>
 *       <li><b>Ordered sources:</b> Examples include {@code List}, {@code LinkedHashSet},
 *           and {@code IntStream.range()}.</li>
 *       <li><b>Unordered sources:</b> Examples include {@code HashSet},
 *           {@code Stream.generate()}, and other unordered collections or generators.</li>
 *     </ul>
 *   </li>
 *   <li><b>Effect of Parallelism:</b> Even in a parallel stream, the encounter order
 *       is preserved for ordered streams unless explicitly modified.</li>
 *   <li><b>Guaranteed Behavior:</b> Ordered streams guarantee predictable results
 *       for terminal operations like {@code findFirst()}, {@code limit()}, and {@code skip()}.</li>
 * </ul>
 *
 * <h3>Examples</h3>
 *
 * <b>Example 1: Ordered Source</b>
 * <pre>{@code
 * List<Integer> numbers = List.of(1, 2, 3, 4, 5);
 * numbers.stream() // Ordered stream
 *        .forEach(System.out::println); // Prints: 1, 2, 3, 4, 5
 * }</pre>
 *
 * <b>Example 2: Parallel Stream with Order</b>
 * <pre>{@code
 * List<Integer> numbers = List.of(1, 2, 3, 4, 5);
 * numbers.parallelStream() // Parallel ordered stream
 *        .forEachOrdered(System.out::println); // Prints: 1, 2, 3, 4, 5
 * }</pre>
 *
 * <h3>Unordered Streams</h3>
 *
 * A stream is considered <b>unordered</b> under the following conditions:
 * <ul>
 *   <li>Its source is inherently unordered (e.g., {@code HashSet}, {@code Stream.generate()}).</li>
 *   <li>The {@code unordered()} method is explicitly called on an ordered stream.</li>
 * </ul>
 *
 * <b>Example: Declaring Unordered</b>
 * <pre>{@code
 * List<Integer> numbers = List.of(1, 2, 3, 4, 5);
 * numbers.stream()            // Ordered stream
 *        .unordered()         // Explicitly unordered
 *        .forEach(System.out::println); // Encounter order is no longer guaranteed
 * }</pre>
 *
 * <h3>Why Order Matters:</h3>
 * Encounter order is crucial for certain operations:
 * <ul>
 *   <li><b>findFirst()</b>: Returns the first element in encounter order for
 *       an ordered stream. For unordered streams, it may return any element.</li>
 *   <li><b>limit() and skip():</b> These operations rely on order to determine
 *       which elements to process.</li>
 * </ul>
 *
 * <h3>Performance Note:</h3>
 * Using unordered streams can sometimes improve performance, particularly for
 * parallel streams, as removing order constraints gives the stream more freedom
 * in processing elements concurrently.
 */
public class UnorderedSample {
    public static void main(String[] args) {
        Supplier<IntStream> getStream = () -> IntStream.range(0, 1000);
        System.out.println("findFirst result: " + getStream.get().parallel().findFirst().orElse(-1)); // If the stream has order, findFirst always returns the first element if the stream is not empty, even though the stream is a parallel.
        System.out.println("findFirst result after invoking unordered(): " + getStream.get().unordered().parallel().findFirst().orElse(-1)); // Unordered removes order guarantee
    }
}
