package com.sanver.basics.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * <div class="markdown prose w-full break-words dark:prose-invert dark"><p>In Java, <strong>iterators</strong> serve as a mechanism to traverse or iterate through elements in a collection (such as <code>ArrayList</code>, <code>HashSet</code>, <code>LinkedList</code>, etc.) without exposing the underlying structure of the collection. They provide a standard way to access elements one by one in a sequential manner, regardless of the specific type of collection.</p><h3>Key Purposes of Iterators:</h3><ol><li><p><strong>Uniform Traversal Across Collections</strong>:</p><ul><li>Iterators provide a consistent way to iterate over different types of collections (e.g., lists, sets, maps). Instead of writing different traversal logic for each collection type, you can use an iterator to handle all types uniformly.</li></ul></li><li><p><strong>Abstraction of Collection Implementation</strong>:</p><ul><li>Iterators abstract the internal structure of the collection. Whether the collection is an array, linked list, tree, or hash table, the iterator provides a simple interface (<code>hasNext()</code>, <code>next()</code>, <code>remove()</code>) to access elements without the need to understand the underlying structure.</li></ul></li><li><p><strong>Supports Safe Removal During Iteration</strong>:</p><ul><li>Iterators offer a <code>remove()</code> method to safely remove elements from the collection during iteration, which isn't directly possible with a for-each loop or basic <code>for</code> loops without risking <code>ConcurrentModificationException</code>.</li></ul></li><li><p><strong>Avoids Indexing</strong>:</p><ul><li>Some collections, like <code>Set</code> or <code>HashMap</code>, don't support indexing. Iterators allow you to traverse these collections without relying on an index, which is crucial for non-indexed collections.</li></ul></li><li><p><strong>Thread-Safe Iteration</strong>:</p><ul><li>Some iterators (like <code>ConcurrentIterator</code> for <code>ConcurrentHashMap</code>) are designed to be used safely in concurrent environments where multiple threads access a collection at the same time.</li></ul></li></ol><h3>Common Methods in Java's Iterator Interface:</h3><ul><li><code>hasNext()</code>:<ul><li>Checks if there are more elements to iterate over.</li></ul></li><li><code>next()</code>:<ul><li>Retrieves the next element in the collection.</li></ul></li><li><code>remove()</code>:<ul><li>Removes the current element from the collection (optional operation).</li></ul></li></ul><pre class="!overflow-visible"><div class="dark bg-gray-950 contain-inline-size rounded-md border-[0.5px] border-token-border-medium relative"><div class="sticky top-9 md:top-[5.75rem]"><div class="absolute bottom-0 right-2 flex h-9 items-center"><div class="flex items-center rounded bg-token-main-surface-secondary px-2 font-sans text-xs text-token-text-secondary"><span class="" data-state="closed"></span></div></div></div><div class="overflow-y-auto p-4" dir="ltr">
 *     </div></div></pre></div>
 */
public class IteratorSample {
    public static void main(String[] args) {
        var list = new ArrayList<>(List.of(1, 2, 3, 5, 6, 7));
        var iterator = list.iterator();

        System.out.printf("Printing the list with %n%n\twhile (iterator.hasNext()) {%n\t\tSystem.out.printf(\"%%d \", iterator.next());%n\t}%n%n\t");

        while (iterator.hasNext()) {
            System.out.printf("%d ", iterator.next());
        }

        System.out.printf("%n%n");

        iterator = list.iterator(); // If we did not renew this, then iterator.next() would throw NoSuchElementException below

        System.out.printf("Printing the list with %n%n\titerator.next();%n\titerator.forEachRemaining(x -> System.out.printf(\"%%d \", x));%n%n\t");
        iterator.next();
        iterator.forEachRemaining(x -> System.out.printf("%d ", x));

        System.out.printf("%n%n");

        iterator = list.iterator();

        while (iterator.hasNext()) {
            var item = iterator.next();

            if (item % 2 == 0) {
                iterator.remove(); // Unlike Enumeration, Iterator allows for element removal from a collection.
                // Removes the element in the lastRet (index of last element returned) and sets the cursor to lastRet.
            } else {
                System.out.printf("%d ", item);
            }
        }
        System.out.println();
        System.out.println(list);

        iterator = list.iterator();

        while (iterator.hasNext()) {
            list.add(3);
            System.out.printf("%d ", iterator.next()); // iterator.next() will throw a ConcurrentModificationException,
            // since the list is modified after the iterator has been created.
            // This is achieved by modCount field in the list and the expectedModCount field in the iterator.
            // When an iterator is generated, the expectedModCount is set to modCount and when the list is modified,
            // modCount is incremented. When iterator.next() is called, expectedModCount and modCount are compared,
            // if they are not equal, then the ConcurrentModificationException is thrown.
        }
    }
}
